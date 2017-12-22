package ru.tilacyn.trie;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void getNum() {
        Trie trie = new Trie();
        assertEquals(0, trie.getNum("a", 0));
        assertEquals(1, trie.getNum("aab", 2));
    }

    @Test
    public void contains() {
        Trie trie = new Trie();
        trie.add("kek");
        assertTrue(trie.contains("kek"));
        trie.add("keks");
        assertTrue(trie.contains("kek"));
        assertTrue(trie.contains("keks"));
        trie.add("lol");
        assertTrue(!trie.contains("lo"));
    }

    @Test
    public void add() {
        Trie trie = new Trie();
        trie.add("johnlennon");
        assertTrue(trie.add("john"));
        assertTrue(trie.add("johnny"));
        assertTrue(trie.add("johnnycash"));
        assertTrue(!trie.add("johnny"));
    }

    @Test
    public void removeEmpty() {
        Trie trie = new Trie();
        assertFalse(trie.remove("s"));
    }

    @Test
    public void removeAll() {
        Trie trie = new Trie();
        trie.add("luntik");
        trie.add("peppapig");
        trie.add("orehus");
        trie.add("strawberry");
        assertTrue(trie.remove("luntik"));
        assertTrue(trie.remove("peppapig"));
        assertTrue(trie.remove("orehus"));
        assertTrue(trie.remove("strawberry"));
        assertEquals(trie.size(), 0);
    }

    @Test
    public void removeFromLinearTrie() {
        Trie trie = new Trie();
        trie.add("l");
        trie.add("luntik");
        trie.add("luntikikuzya");
        trie.add("luntikikuzyaimila");
        assertEquals(4, trie.size());
        assertTrue(trie.remove("luntik"));
        assertEquals(3, trie.size());
        assertTrue(trie.remove("luntikikuzya"));
        assertEquals(2, trie.size());
    }


    @Test
    public void removeAndAdd() {
        Trie trie = new Trie();
        trie.add("l");
        assertTrue(trie.remove("l"));
        assertFalse(trie.remove("l"));
        trie.add("l");
        assertTrue(trie.remove("l"));
    }


    @Test
    public void size() {
        Trie trie = new Trie();
        trie.add("mikjagger");
        assertEquals(1, trie.size());
        trie.add("jagger");
        assertEquals(2, trie.size());
        trie.add("robbykrieger");
        assertEquals(3, trie.size());
        trie.remove("jagger");
        assertEquals(2, trie.size());
        trie.add("superlol");
        assertEquals(3, trie.size());
        trie.add("super");
        assertEquals(4, trie.size());
        trie.remove("super");
        assertEquals(3, trie.size());
        trie.remove("superlol");
        assertEquals(2, trie.size());
    }

    @Test
    public void howManyStartWithPrefix() {
        Trie trie = new Trie();
        trie.add("egorletov");
        trie.add("igorletov");
        trie.add("letov");
        trie.add("egor");
        trie.add("egorushka");
        trie.add("egorchik");
        trie.add("egorishe");
        trie.add("igor");
        trie.add("igorek");
        trie.add("igorechek");
        trie.add("igorechechek");
        assertEquals(5, trie.howManyStartWithPrefix("e"));
        assertEquals(5, trie.howManyStartWithPrefix("egor"));
        assertEquals(5, trie.howManyStartWithPrefix("igor"));
        assertEquals(1, trie.howManyStartWithPrefix("letov"));
        assertEquals(1, trie.howManyStartWithPrefix("l"));
        assertEquals(0, trie.howManyStartWithPrefix("letova"));
        assertEquals(0, trie.howManyStartWithPrefix("w"));
        assertEquals(3, trie.howManyStartWithPrefix("igore"));
    }

    @Test
    public void serializeDeserializeEmpty() throws Exception {
        File file = new File("serialized");
        file.createNewFile();

        Trie trie = new Trie();

        FileOutputStream fos = new FileOutputStream(file);
        trie.serialize(fos);
        fos.close();

        FileInputStream fis = new FileInputStream(file);
        trie.deserialize(fis);
        fis.close();

        file.delete();
    }


    @Test
    public void serializeDeserializeNotEmpty() throws Exception {
        File file = new File("serialized");

        file.createNewFile();

        Trie trie = new Trie();
        trie.add("a");
        trie.add("b");
        trie.add("c");
        trie.add("abc");
        trie.add("bbbb");
        trie.remove("b");

        FileOutputStream fos = new FileOutputStream(file);
        trie.serialize(fos);
        fos.close();

        FileInputStream fis = new FileInputStream(file);
        trie.deserialize(fis);
        fis.close();

        file.delete();
    }
}