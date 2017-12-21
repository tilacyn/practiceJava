package ru.tilacyn.reflector;

import com.sun.javafx.collections.ArrayListenerHelper;
import org.junit.Test;
import ru.tilacyn.reflector.Reflector;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

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


class Sword<E> {
    E kek;
    int length;
    int width;
    int material;
    String name;
    Axe teamMate;
}

class Axe<T> {
    T kek;
    int length;
    int width;
    int material;
    String name;
    Sword teamMate;
}


public class ReflectorTest {
    @Test
    public void diffClasses() throws Exception {
        Reflector reflector = new Reflector();
        reflector.diffClasses(Sword.class, Axe.class);
    }

    @Test
    public void printStructure() throws Exception {
        Reflector reflector = new Reflector();

        reflector.printStructure(Reflector.class);
        //reflector.printStructure(Axe.class);
        //reflector.printStructure(Elve.class);
    }

}