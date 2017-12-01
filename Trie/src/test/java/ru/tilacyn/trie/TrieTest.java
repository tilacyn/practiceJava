package ru.tilacyn.trie;

import org.junit.Test;

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
        assert trie.contains("kek");
        trie.add("keks");
        assert trie.contains("kek");
        assert trie.contains("keks");
        trie.add("lol");
        assert !trie.contains("lo");
    }

    @Test
    public void add() {
        Trie trie = new Trie();
        trie.add("johnlennon");
        assert trie.add("john");
        assert trie.add("johnny");
        assert trie.add("johnnycash");
        assert !trie.add("johnny");
    }

    @Test
    public void remove() {
        Trie trie = new Trie();
        trie.add("luntik");
        trie.add("peppapig");
        trie.add("orehus");
        trie.add("strawberry");
        assert trie.contains("strawberry");
        assert trie.remove("strawberry");
        assert !trie.contains("strawberry");
        trie.add("pineapple");
        assert trie.contains("pineapple");
        trie.add("pine");
        assert trie.remove("pine");
        assert !trie.contains("pine");
        assert trie.contains("pineapple");
        assert trie.remove("pineapple");
        assert !trie.contains("pineapple");
        assert !trie.remove("kek");
        assert !trie.remove("pineapple");
        assert trie.add("lizardking");
        assert trie.add("lizard");
        assert trie.remove("lizardking");
        assert trie.remove("lizard");

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
}