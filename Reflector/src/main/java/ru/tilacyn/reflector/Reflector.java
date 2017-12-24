package ru.tilacyn.reflector;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.lang.reflect.Type;
import java.util.*;

/**
 * class that has two public methods:
 * 1. printStructure(Class<?> someClass)
 * it prints all the someClass methods(without realization), fields and inside classes
 * into the file SomeClass.java where the new class SomeClass is written
 * file is int the project directory
 * after writing this file is compiled
 * <p>
 * 2. diffClasses(Class<?> first, Class<?> second)
 * it writes all the methods and fields from one class that do not appear in another to th file diff
 * in the project root directory
 * and returns whether all classes are the same or not
 */
public class Reflector {

    /**
     * FileWriter that is used to write
     * whether in file "SomeClass.java"
     * or in file "diff", both in root project dir
     */
    private FileWriter fileWriter;

    /**
     * number of tabs that we should write before method, field, class, etc
     * to decorate our output file in an appropriate way
     */
    private int tabsNumber = 0;

    //processing strings

    /**
     * changes all the dollars: '$' in string s to dots: '.'
     *
     * @param s source string
     * @return result string
     */
    private String dollarToDot(@NotNull final String s) {
        String result = "";
        for (Character c : s.toCharArray()) {
            if (c == '$') {
                result += '.';
            } else {
                result += c;
            }
        }
        return result;
    }

    /**
     * changes function name leaving only the part after last dot
     * eg java.util.ArrayList.add -> add
     * it demands that the source string is a method signature(at least name and braces should be)
     *
     * @param s source string: full method signature
     * @return result string
     */
    private String afterDot(@NotNull final String s) {
        int openBrace = s.indexOf('(');
        int i;
        String name = "";
        boolean hasDotOccured = false;
        for (i = openBrace - 1; i >= 0 && s.charAt(i) != ' '; i--) {
            if (s.charAt(i) == '.') {
                hasDotOccured = true;
            }
            if (!hasDotOccured) {
                name = s.charAt(i) + name;
            }
        }
        return s.substring(0, i + 1) + name + s.substring(openBrace, s.length());
    }

    /**
     * does the same as dollatToDot but for the field signature string
     *
     * @param s source string: field signature
     * @return
     */
    private String afterDotField(@NotNull final String s) {
        int openBrace = s.indexOf('(');
        int i;
        String name = "";
        boolean hasDotOccured = false;
        for (i = s.length() - 1; i >= 0 && s.charAt(i) != ' '; i--) {
            if (s.charAt(i) == '.') {
                hasDotOccured = true;
            }
            if (!hasDotOccured) {
                name = s.charAt(i) + name;
            }
        }
        return s.substring(0, i + 1) + name;
    }

    /**
     * changes method signature by adding argument names to the argument types
     * eg set(int,int) -> set(int arg0, int arg1)
     *
     * @param s source string: method signature
     * @return result string
     */
    private String makeSimpleArgTypes(@NotNull final String s) {
        int openBrace = s.indexOf('(');

        String result = s.substring(0, openBrace + 1);

        String currentType = "";

        int argNumber = 0;

        if (s.charAt(openBrace + 1) != ')') {
            for (int i = openBrace + 1; i < s.length() && s.charAt(i - 1) != ')'; i++) {
                if (s.charAt(i) == ',') {
                    result += currentType + " arg" + ((Integer) argNumber).toString() + ", ";
                    currentType = "";
                    argNumber++;
                    continue;
                }

                if (s.charAt(i) == ')') {
                    result += currentType + " arg" + ((Integer) argNumber).toString();
                    break;
                }

                currentType += s.charAt(i);
            }
        }
        return result + s.substring(s.indexOf(')'), s.length());
    }

    /**
     * makes "SomeClass" the name of the class
     *
     * @param s source String: class signature
     * @return result class signature
     */
    private String makeGlobalName(@NotNull final String s) {
        int nameStart = s.indexOf("class ") + 6;
        int i = nameStart;
        for (; i < s.length() && s.charAt(i) != ' '; i++) {
        }
        return s.substring(0, nameStart) + "SomeClass" + s.substring(i, s.length());
    }

    /**
     * @return current number of tabs to print
     */
    private String tabs() {
        String tabs = "";
        for (int i = 0; i < tabsNumber; i++) {
            tabs += '\t';
        }
        return tabs;
    }

    //print methods

    /**
     * writes method to the output file
     * return value is default
     *
     * @param method should be printed
     * @throws IOException if problems with writing occurred
     */
    private void printMethod(@NotNull final Method method) throws IOException {
        fileWriter.write(tabs());
        fileWriter.write(makeSimpleArgTypes(afterDot(dollarToDot(method.toGenericString()))));

        fileWriter.write(" {\n");

        tabsNumber++;

        String returnValue = "";

        Class returnType = method.getReturnType();

        if (returnType.isPrimitive()) {
            if (method.getReturnType().getName() == "void") {

            } else if (method.getReturnType().getName() == "boolean") {
                returnValue = " false";
            } else {
                returnValue = " 0";
            }
        } else {
            returnValue = " null";
        }

        fileWriter.write(tabs() + "return" + returnValue + ";\n");

        tabsNumber--;

        fileWriter.write(tabs() + "}\n");
    }

    /**
     * writes field to the output file
     *
     * @param field     should be printed
     * @param someClass Class the field belongs to
     * @throws IOException if problems with writing occurred
     */
    private void printField(@NotNull final Field field, @NotNull final Class someClass) throws IOException {
        if (field.getType() == someClass.getEnclosingClass()) {
            return;
        }

        fileWriter.write(tabs());

        fileWriter.write(afterDotField(dollarToDot(field.toGenericString())) + ";\n");
    }

    /**
     * writes constructor to the output file
     *
     * @param constructor should be printed
     * @param isGlobal    boolean: does the constructor belong to the global class
     * @throws IOException if problems with writing occurred
     */
    private void printConstructor(@NotNull final Constructor constructor, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        if (isGlobal) {
            String newSignature = dollarToDot(afterDot(constructor.toGenericString()));
            int i;
            for (i = newSignature.indexOf('('); i >= 0 && newSignature.charAt(i) != ' '; i--) {
            }
            fileWriter.write(newSignature.substring(0, i + 1) +
                    "SomeClass" +
                    newSignature.substring(newSignature.indexOf('('), newSignature.length()));
        } else {
            fileWriter.write(dollarToDot(afterDot(constructor.toGenericString())));
        }

        fileWriter.write(" {\n");

        fileWriter.write(tabs() + "}\n");
    }

    /**
     * writes class to the output file
     *
     * @param someClass should be printed
     * @param isGlobal  boolean: is this class the global one
     * @throws IOException if problems with writing occurred
     */
    private void printClass(@NotNull final Class<?> someClass, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        if (isGlobal) {
            fileWriter.write(makeGlobalName(dollarToDot(afterDotField(someClass.toGenericString()))) + "{\n\n");
        } else {
            fileWriter.write(dollarToDot(afterDotField(someClass.toGenericString())) + "{\n\n");
        }


        tabsNumber++;

        for (Constructor constructor : someClass.getConstructors()) {
            printConstructor(constructor, isGlobal);
        }

        for (Field field : someClass.getDeclaredFields()) {
            printField(field, someClass);
        }


        for (Method method : someClass.getDeclaredMethods()) {
            printMethod(method);
        }

        for (Class child : someClass.getDeclaredClasses()) {
            printClass(child, false);
        }

        tabsNumber--;

        fileWriter.write(tabs() + "}\n");

    }


    /**
     * writes the whole structure
     *
     * @param someClass class that should be printed
     * @throws IOException          if problems with writing occurred
     * @throws InterruptedException if problems with executing commands in the runtime occurred
     */
    public void printStructure(@NotNull final Class<?> someClass) throws IOException, InterruptedException {

        Runtime.getRuntime().exec("find . -maxdepth 1 -name \"*class\" | xargs rm").waitFor();

        File file = new File("SomeClass.java");

        file.createNewFile();

        fileWriter = new FileWriter(file, false);

        //fileWriter.write(untilFirstComa(someClass.getPackage().toString()) + ";\n\n");

        fileWriter.write('\n');

        printClass(someClass, true);

        fileWriter.flush();

        fileWriter.close();

        Runtime.getRuntime().exec("javac SomeClass.java").waitFor();

    }

    //diff

    /**
     * hash function depends on the
     * field modifiers
     * field name
     * field type
     *
     * @param field is to be hashed
     * @return hashed field of type String
     */
    private String hashField(@NotNull final Field field) {
        return field.getName() + field.getModifiers() + field.getType();
    }

    /**
     * hash function depends on the
     * method name
     * method return type
     * method argument types
     *
     * @param method is to be hashed
     * @return hashed method of type String
     */
    private String hashMethod(@NotNull final Method method) {
        System.out.println(method.getName() + " " + method.getReturnType() + " " + method.getGenericParameterTypes());

        String result = method.getName() + " " + method.getReturnType() + " ";

        for (Type type : method.getParameterTypes()) {
            result += type.toString() + " ";
        }

        return result;
    }

    /**
     * checks if all the fields in the first class also appear int the second class
     * and prints those which do not appear
     *
     * @param first  class
     * @param second class
     * @return true if all the first fields also are in the second class
     * @throws IOException if problems with writing occurred
     */
    private boolean processDiffFields(@NotNull final Class<?> first, @NotNull final Class<?> second) throws IOException {
        HashSet<String> secondFields = new HashSet<>();
        boolean result = true;

        for (Field field : second.getDeclaredFields()) {
            secondFields.add(hashField(field));
        }

        fileWriter.write("FIELDS:\n\n");

        for (Field field : first.getDeclaredFields()) {
            if (!secondFields.contains(hashField(field))) {
                printField(field, first);
                result = false;
            }
        }

        return result;
    }

    /**
     * checks if all the methods in the first class also appear int the second class
     * and prints those which do not appear
     *
     * @param first  class
     * @param second class
     * @return true if all the first methods also are in the second class
     * @throws IOException if problems with writing occurred
     */
    private boolean processDiffMethods(@NotNull final Class<?> first, @NotNull final Class<?> second) throws IOException {
        HashSet<String> secondMethods = new HashSet();
        boolean result = true;

        for (Method method : second.getDeclaredMethods()) {
            secondMethods.add(hashMethod(method));
        }

        fileWriter.write("METHODS:\n");


        for (Method method : first.getDeclaredMethods()) {
            if (!secondMethods.contains(hashMethod(method))) {
                printMethod(method);
                result = false;
            }
        }

        return result;
    }

    /**
     * checks whether all the fields and methods of the first appear also in the second
     * and prints all the fields and methods from tre first that do not appear in the second by the way
     *
     * @param first
     * @param second
     * @return true if all the fields and methods of the first class are in the second
     * @throws IOException
     */
    private boolean processDiffClasses(@NotNull final Class<?> first, @NotNull final Class<?> second) throws IOException {
        return processDiffFields(first, second) &
                processDiffMethods(first, second);
    }

    /**
     * checks if the first and the second classes have all theirs methods and fields in common
     * prints those fields | methods that do not appear in the another class
     *
     * @param first  class
     * @param second class
     * @return true if they have the same methods and fields
     * @throws IOException if problems with writing to the output file occurred
     */
    public boolean diffClasses(@NotNull final Class<?> first, @NotNull final Class<?> second) throws IOException {
        boolean result = true;
        File file = new File("diff");

        file.createNewFile();
        fileWriter = new FileWriter(file, false);

        result &= processDiffClasses(first, second);
        result &= processDiffClasses(second, first);

        fileWriter.flush();
        fileWriter.close();

        return result;
    }
}
