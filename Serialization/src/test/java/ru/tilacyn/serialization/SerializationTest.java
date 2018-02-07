package ru.tilacyn.serialization;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SerializationTest {

    @Test
    public void strings() throws Exception {
        File lol = new File("lol");
        lol.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(lol)) {
            Serialization.serialize(new Str("1", "2"), fos);
        }
        try (FileInputStream fis = new FileInputStream(lol)) {
            Str t = Serialization.deserialize(fis, Str.class);
            assertEquals(t.s1, "1");
            assertEquals(t.s2, "2");
        }
    }

    @Test
    public void ints() throws Exception {
        File lol = new File("lol");
        lol.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(lol)) {
            Serialization.serialize(new Int(20, 30), fos);
        }
        try (FileInputStream fis = new FileInputStream(lol)) {
            Int t = Serialization.deserialize(fis, Int.class);
            assertEquals(t.n1, 20);
            assertEquals(t.n2, 30);
        }
    }

    @Test
    public void complexEasy() throws Exception {
        File lol = new File("lol");
        lol.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(lol)) {
            Serialization.serialize(new CmpE(1, 1.0, 1.0f, (short) 3, (byte) 0, 5, false, "s", 'c'), fos);
        }
        try (FileInputStream fis = new FileInputStream(lol)) {
            CmpE t = Serialization.deserialize(fis, CmpE.class);
            assertEquals(t.i, 1);
            assertEquals(t.d, 1.0, 0.1);

            assertEquals(t.f, 1.0f, 0.1);
            assertEquals(t.sh, (short) 3);
            assertEquals(t.bt, (byte) 0);
            assertEquals(t.l, 5);
            assertEquals(t.bool, false);
            assertEquals(t.s, "s");
            assertEquals(t.c, 'c');

        }
    }


}

class Str {
    public String s1;
    public String s2;

    public Str(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Str() {
    }
}

class Int {
    public int n1;
    public int n2;

    public Int(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Int() {
    }
}

class CmpE {
    public int i;
    public double d;

    public float f;
    public short sh;
    public byte bt;
    public long l;
    public boolean bool;
    public String s;
    public char c;


    public CmpE(int i, double d, float f, short sh, byte bt, long l, boolean bool, String s, char c) {
        this.i = i;
        this.d = d;
        this.f = f;
        this.sh = sh;
        this.bt = bt;
        this.l = l;
        this.bool = bool;
        this.c = c;
        this.s = s;
    }

    public CmpE() {

    }

}