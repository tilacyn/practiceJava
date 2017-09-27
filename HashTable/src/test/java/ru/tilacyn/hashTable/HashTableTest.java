package ru.tilacyn.hashTable;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * a test class for HashTable
 */

public class HashTableTest{
    @Test
    public void clear() {
        HashTable ht = new HashTable(4);
        ht.put("1", "2");
        ht.clear();
        assert !(ht.contains("1"));
        assertEquals(ht.size(), 0);
    }

    @Test
    public void makeHashTable() {
        HashTable ht = new HashTable(4);
    }


    @Test
    public void getHash() {
        HashTable ht = new HashTable(100);
        assertEquals(ht.getHash("1"), ht.getHash("1"));
        assert ht.getHash("2") < 100 && ht.getHash("2") >= 0;
    }

    @Test
    public void put() {
        HashTable ht = new HashTable(100);
        assertEquals(ht.put("1", "2"), null);
        assertEquals(ht.put("3", "4"), null);
        assertEquals(ht.put("5", "6"), null);
        assertEquals(ht.get("1"), "2");
        assertEquals(ht.get("5"), "6");
        assertEquals(ht.put("3", "10"), "4");
        assertEquals(ht.get("3"), "10");
    }

    @Test
    public void remove() {
        HashTable ht = new HashTable(100);
        ht.put("1", "2");
        assertEquals(ht.remove("1"), "2");
        ht.put("3", "4");
        ht.put("5", "6");
        assertEquals(ht.remove("3"), "4");
        assertEquals(ht.remove("3"), null);
    }

    @Test
    public void contains() {
        HashTable ht = new HashTable(10);
        assert !(ht.contains("1"));
        ht.put("1", "2");
        ht.put("3", "4");
        assert (ht.contains("1"));
        ht.remove("1");
        assert !(ht.contains("1"));
    }

    @Test
    public void get() {
        HashTable ht = new HashTable(100);
        ht.put("1", "2");
        assertEquals(ht.get("1"), "2");
        ht.put("3", "4");
        assertEquals(ht.get("3"), "4");
        ht.remove("1");
        assertEquals(ht.get("1"), null);
    }

    @Test
    public void size() {
        HashTable ht = new HashTable(10);
        ht.put("1", "2");
        assertEquals(ht.size(), 1);
        ht.put("3", "4");
        assertEquals(ht.size(), 2);
        ht.put("5", "6");
        assertEquals(ht.size(), 3);
        ht.remove("3");
        assertEquals(ht.size(), 2);
    }
}