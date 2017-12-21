package ru.tilacyn.reflector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Reflector {

    private FileWriter fileWriter;

    private int tabsNumber = 0;


    //processing Strings

    private String makeSimpleReturnType(String s) {
        int openBrace = s.indexOf('(');
        int i = openBrace;

        String type = "";

        while (s.charAt(i) != ' ') {
            i--;
        }
        String end = s.substring(i, s.length());

        while (s.charAt(i) != ' ') {
            i--;
        }

        i--;

        while (i >= 0 && s.charAt(i) != ' ') {
            type = s.charAt(i) + type;
            i--;
        }

        i++;

        System.out.println("KEK" + s.substring(0, i) + afterDollar(type) + end);

        return s.substring(0, i) + afterDollar(type) + end;
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
                    result += afterDollar(currentType) + " arg" + ((Integer) argNumber).toString() + ", ";
                    currentType = "";
                    argNumber++;
                    continue;
                }

                if (s.charAt(i) == ')') {
                    result += afterDollar(currentType) + " arg" + ((Integer) argNumber).toString();
                    break;
                }

                currentType += s.charAt(i);
            }
        }

        System.out.println();

        System.out.println(result);


        return result + s.substring(s.indexOf(')'), s.length());

    }

    private String afterDollar(String s) {
        if (!s.contains("$")) {
            return s;
        }

        String afterDollar = "";

        for (int i = s.length() - 1; i >= 0; i--) {
            Character c = s.charAt(i);
            if (c == '$') {
                break;
            }
            afterDollar = c + afterDollar;
        }
        return afterDollar;
    }

    private String afterDot(String s) {
        String result = "";

        for (int i = s.length() - 1; i >= 0; i--) {
            Character c = s.charAt(i);
            if (c == '.') {
                break;
            }
            result = c + result;
        }

        return result;
    }

    private String makeSimpleName(String s) {
        int openBrace = s.indexOf('(');
        int i;

        for (i = openBrace; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                break;
            }
        }

        System.out.println("LOLOLOL " + afterDot(afterDollar(s.substring(i + 1, openBrace))));

        return s.substring(0, i) + " " + afterDot(afterDollar(s.substring(i + 1, openBrace))) + s.substring(openBrace, s.length());
    }

    private String tabs() {
        String tabs = "";
        for (int i = 0; i < tabsNumber; i++) {
            tabs += '\t';
        }
        return tabs;
    }

    //print methods

    private void printModifiers(int mods) throws IOException {
        if (Modifier.isPublic(mods)) {
            fileWriter.write("public ");
        }
        if (Modifier.isPrivate(mods)) {
            fileWriter.write("private ");
        }
        if (Modifier.isProtected(mods)) {
            fileWriter.write("protected ");
        }
        if (Modifier.isStatic(mods)) {
            fileWriter.write("static ");
        }
        if (Modifier.isAbstract(mods)) {
            fileWriter.write("abstract ");
        }
        if (Modifier.isFinal(mods)) {
            fileWriter.write("final ");
        }
    }

    private void printMethod(Method method) throws IOException {

        fileWriter.write(tabs());


        fileWriter.write(makeSimpleName(
                makeSimpleArgTypes(
                        makeSimpleReturnType(
                                method.toGenericString()))));

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

        printModifiers(field.getModifiers());

        fileWriter.write(field.getType().getSimpleName() + " " + afterDollar(field.getName()) + ";\n");
    }

    private void printConstructor(Constructor constructor, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        if (isGlobal) {
            String newSignature = makeSimpleName(
                    makeSimpleArgTypes(
                            constructor.toGenericString()));
            int i;
            for (i = newSignature.indexOf('('); i >= 0 && newSignature.charAt(i) != ' '; i--) {
            }
            fileWriter.write(newSignature.substring(0, i + 1) +
                    "ResultClass" +
                    newSignature.substring(newSignature.indexOf('('), newSignature.length()));
        } else {
            fileWriter.write(makeSimpleName(
                    makeSimpleArgTypes(
                            constructor.toGenericString())));
        }

        fileWriter.write(" {\n");

        fileWriter.write(tabs() + "}\n");
    }

    private void printClass(Class<?> someClass, boolean isGlobal) throws IOException {
        fileWriter.write(tabs());

        printModifiers(someClass.getModifiers());

        if (isGlobal) {
            fileWriter.write(
                    "class ResultClass extends " + someClass.getSuperclass().getName() + "{" +
                            "\n\n");
        } else {
            fileWriter.write(
                    "class " + someClass.getSimpleName() + " extends " + someClass.getSuperclass().getName() + "{" +
                            "\n\n");
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
