package ru.tilacyn.reflector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.*;
import java.util.stream.Collectors;

public class Reflector {


    private FileWriter fileWriter;

    private int tabsNumber = 0;


    //processing Strings

    private String dollarToDot(String s) {
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

    private String afterDot(String s) {
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

    private String afterDotField(String s) {
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

    private String makeSimpleArgTypes(String s) {
        int openBrace = s.indexOf('(');

        String result = s.substring(0, openBrace + 1);

        String currentType = "";

        int argNumber = 0;

        if (s.charAt(openBrace + 1) != ')') {
            for (int i = openBrace + 1; i < s.length() && s.charAt(i - 1) != ')'; i++) {
                System.out.print(s.charAt(i));
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

    private String makeGlobalName(String s) {
        int nameStart = s.indexOf("class ") + 6;
        int i = nameStart;
        for (; i < s.length() && s.charAt(i) != ' '; i++) {
        }
        return s.substring(0, nameStart) + "ResultClass" + s.substring(i, s.length());
    }


    private String tabs() {
        String tabs = "";
        for (int i = 0; i < tabsNumber; i++) {
            tabs += '\t';
        }
        return tabs;
    }

    //print methods

    private void printMethod(Method method) throws IOException {

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

    private void printField(Field field, Class someClass) throws IOException {
        if (field.getType() == someClass.getEnclosingClass()) {
            return;
        }

        fileWriter.write(tabs());

        fileWriter.write(afterDotField(dollarToDot(field.toGenericString())) + ";\n");
    }

    private void printConstructor(Constructor constructor, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        if (isGlobal) {
            String newSignature = dollarToDot(afterDot(constructor.toGenericString()));
            int i;
            for (i = newSignature.indexOf('('); i >= 0 && newSignature.charAt(i) != ' '; i--) {
            }
            fileWriter.write(newSignature.substring(0, i + 1) +
                    "ResultClass" +
                    newSignature.substring(newSignature.indexOf('('), newSignature.length()));
        } else {
            fileWriter.write(dollarToDot(afterDot(constructor.toGenericString())));
        }

        fileWriter.write(" {\n");

        fileWriter.write(tabs() + "}\n");
    }

    private void printClass(Class<?> someClass, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        if (isGlobal) {
            fileWriter.write(makeGlobalName(dollarToDot(afterDotField(someClass.toGenericString()))) + "{\n\n");
        } else {
            fileWriter.write(dollarToDot(afterDotField(someClass.toGenericString())) + "{\n\n");
        }


        tabsNumber++;

        for (Constructor constructor : someClass.getConstructors()) {
            printConstructor(constructor, isGlobal);
            System.out.println("cons\n");
        }

        for (Field field : someClass.getDeclaredFields()) {
            printField(field, someClass);
            System.out.println("field\n");
        }


        for (Method method : someClass.getDeclaredMethods()) {
            printMethod(method);
            System.out.println("method\n");
        }

        for (Class child : someClass.getDeclaredClasses()) {
            printClass(child, false);
            System.out.println("child\n");
        }

        tabsNumber--;

        fileWriter.write(tabs() + "}\n");

    }


    public void printStructure(Class<?> someClass) throws IOException, InterruptedException {

        Runtime.getRuntime().exec("find . -maxdepth 1 -name \"*class\" | xargs rm").waitFor();

        File file = new File("ResultClass.java");

        file.createNewFile();

        fileWriter = new FileWriter(file, false);

        //fileWriter.write(untilFirstComa(someClass.getPackage().toString()) + ";\n\n");

        fileWriter.write('\n');

        printClass(someClass, true);

        fileWriter.flush();

        fileWriter.close();

        System.out.println("WOW");

        Runtime.getRuntime().exec("javac ResultClass.java").waitFor();

    }


    private boolean processDiffFields(Class<?> first, Class<?> second) throws IOException {
        //HashSet

        for (Field field : first.getDeclaredFields()) {

        }
        return true;
    }


    private boolean processDiffClasses(Class<?> first, Class<?> second) {
        return true;
    }

    public boolean diffClasses(Class<?> first, Class<?> second) throws IOException {

        File file = new File("diff");

        file.createNewFile();

        fileWriter = new FileWriter(file, false);

        fileWriter.write("FIELDS:\n");
/*
        for (Field field1 : firstFields) {
            boolean has = false;
            for (Field field2 : secondFields) {
                if (fieldComparator.compare(field1, field2) == 0) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                printField(field1, first);
            }
        }

        fileWriter.write("SECOND CLASS FIELDS:\n");

        for (Field field : secondFields) {
            if (firstFields.contains(field)) {
                printField(field, second);
            }
        }
*/
        fileWriter.flush();

        fileWriter.close();

        return true;
    }

    Comparator<Field> fieldComparator = new Comparator<Field>() {
        @Override
        public int compare(Field o1, Field o2) {
            System.out.println(o1.getType().getName());
            System.out.println(o2.getType().getName());
            System.out.println();
            if (!o1.getType().equals(o2.getType())) {
                return 1;
            }
            if (o1.getModifiers() != o2.getModifiers()) {
                return 1;
            }
            if (o1.getName() != o2.getName()) {
                return 1;
            }

            return 0;
        }
    };

}
