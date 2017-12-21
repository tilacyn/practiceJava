package ru.tilacyn.reflector;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.*;


public class ReflectorTest {

    @Test
    public void printStructure() throws Exception {
        Reflector reflector = new Reflector();

        reflector.printStructure(Reflector.class);
    }

    @Test
    public void diffClasses() throws Exception {
        Reflector reflector = new Reflector();
        assertFalse(reflector.diffClasses(Sword.class, Axe.class));
        assertFalse(reflector.diffClasses(Sword.class, Elve.class));
        assertFalse(reflector.diffClasses(Axe.class, Sword.class));
    }

    @Test
    public void diffFields1() throws Exception {
        Reflector reflector = new Reflector();
        assertTrue(reflector.diffClasses(AF.class, BF.class));
        assertFalse(reflector.diffClasses(AF.class, BDiff.class));
        // E and E extends Object test
        assertTrue(reflector.diffClasses(AO.class, BO.class));
    }


    @Test
    public void diffEmptyClasses() throws Exception {
        Reflector reflector = new Reflector();
        assertTrue(reflector.diffClasses(A.class, B.class));
    }

    @Test
    public void diffClassToItself() throws Exception {
        Reflector reflector = new Reflector();
        assertTrue(reflector.diffClasses(AF.class, AF.class));
        assertTrue(reflector.diffClasses(Elve.class, Elve.class));
        assertTrue(reflector.diffClasses(Array.class, Array.class));
    }

    @Test
    public void diffMethods() throws Exception {
        Reflector reflector = new Reflector();
        assertTrue(reflector.diffClasses(AM.class, BM.class));
        assertTrue(reflector.diffClasses(BM.class, AM.class));
        assertFalse(reflector.diffClasses(AM.class, BMDiff.class));
        assertFalse(reflector.diffClasses(BMDiff.class, AM.class));
    }

    @Test
    public void complexTest() throws Exception {
        Reflector reflector = new Reflector();

        reflector.printStructure(AM.class);
        assertTrue(reflector.diffClasses(new ModuleLoader().clazz, AM.class));

        reflector.printStructure(Reflector.class);
        assertTrue(reflector.diffClasses(new ModuleLoader().clazz, Reflector.class));

        reflector.printStructure(ReflectorTest.class);
        assertTrue(reflector.diffClasses(new ModuleLoader().clazz, ReflectorTest.class));
    }
}


class ModuleLoader extends ClassLoader {
    byte b[] = fetchClassFromFS("SomeClass.class");
    Class<?> clazz = new ClassLoader() {
        public Class<?> getClass(String s, byte[] b, int l, int r) {
            return super.defineClass(s, b, l, r);
        }
    }.getClass("SomeClass", b, 0, b.length);

    ModuleLoader() throws IOException {
    }

    private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File(path));

        // Get the size of the file
        long length = new File(path).length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;

    }
}


class Elve {

    private Folk folk;

    private Arch arch;

    private int age;

    private double weight;

    private class Arch extends java.util.ArrayList<Integer> {
        int bows;

        int getBows() {
            return bows;
        }

        <T> T lol(T val) {
            return val;
        }

        void incBows() {
            bows++;
        }

        void decBows() {
            bows--;
        }
    }

    private class Folk {
        String folk;

        private Folk(String folk) {
            this.folk = folk;
        }

        Folk makeNewFolk(Folk t) {
            t.setSindarin();
            return t;
        }

        String getFolk(ArrayList<? extends HashMap> lol, String kek) {
            return folk;
        }

        void setSindarin() {
            folk = "Sindar";
        }

        void setNoldoli() {
            folk = "Noldoli";
        }
    }

}

class Sword<E extends Elve> {
    E kek;
    int length;
    int width;
    int material;
    String name;
    Axe teamMate;

    Axe getTeamMate() {
        return teamMate;
    }

    int getMaterials(int lol) {
        return material + lol;
    }

    int getWidth() {
        return material;
    }

}

class A {
}

class AF<T extends Elve> {
    Field field;
    T fieldGeneric;
}

class BF<E extends Elve> {
    Field field;
    E fieldGeneric;
}

class AO<E extends Object> {
    E f;
}

class BO<E> {
    E f;
}

class B {
}

class BDiff<E extends Elve> {
    Field field;
    E fieldGeneric;
    int b;
}

class Axe<T extends Elve> {
    T kek;
    int length;
    int width;
    int material;
    String name;
    Sword teamMate;

    int getMaterials() {
        return material;
    }

    Sword getTeamMate() {
        return teamMate;
    }

    int getWidth() {
        return material;
    }

}

class AM {
    int get(int arg) {
        return arg + 20;
    }

    <T> T set(Collection<? extends T> collection) {
        return collection.iterator().next();
    }

}

class BM {
    int get(int arg0) {
        return arg0 + 20;
    }

    <E> E set(Collection<? extends E> collection) {
        return collection.iterator().next();
    }
}

class BMDiff {
    int get(int arg0) {
        return arg0 + 20;
    }

    void set() {
    }

    <T> T set(Collection<? extends T> collection) {
        return collection.iterator().next();
    }
}

class AC {
    class Node {
        int f1;
        int f2;

        void set() {
        }
    }

    static class SNode {
        int f1;
        int f2;

        void set() {
        }
    }
}

class BC {
    class Node {
        int f1;
        int f2;

        void set() {
        }
    }

    static class SNode {
        int f1;
        int f2;

        void set() {
        }
    }
}

class BCDiff {
    private class Node {
        int f1 = 1;
        int f2;
        int f3;

        void set() {
        }
    }

    static class SNode {
        int f1 = 1;
        int f2;

        void set() {
        }
    }
}