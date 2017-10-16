package ru.tilacyn.hashTable;

import org.junit.Test;

import java.util.Random;

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

    public String makeRandomString() {
        int n;
        do {
            n = Math.abs(new Random().nextInt()) % 8;
        } while(n == 0);
        char[] arr = new char[n];

        for(int i = 0; i < n; i++) {
            int number = Math.abs(new Random().nextInt());
            if(number % 4 == 0) arr[i] = 'a';
            else if(number % 4 == 1) arr[i] = 'b';
            else if(number % 4 == 2) arr[i] = 'c';
            else if(number % 4 == 3) arr[i] = 'd';
        }
        return arr.toString();
    }

    /**
     * additional test function on processing collisions
     * it makes sure that there ARE collisions and checks whether everything's alright
     */

    public void processCollisions() {
        HashTable ht = new HashTable(10);
        for(int i = 0; i < 20; i++) {
            String key1 = makeRandomString();
            String key2 = makeRandomString();
            if(key1.hashCode() % 10 == key2.hashCode() % 10) {
                assertEquals(ht.put(key1, "1"), null);
                assertEquals(ht.put(key2, "2"), null);
                assert ht.contains(key1);
                assert ht.contains(key2);
                assertEquals(ht.get(key1), "1");
                assertEquals(ht.get(key2), "2");
            }
        }
    }

    /**
     * processing collisions added
     */

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

        processCollisions();
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

    /**
     * new test function added consequently
     */


    @Test
    public void rebuild() {
        HashTable ht = new HashTable(4);
        ht.put("1", "1");
        ht.put("2", "2");
        ht.put("3", "3");
        ht.put("4", "4");
        ht.put("5", "5");
        ht.put("6", "6");
        ht.put("7", "7");
        ht.rebuild();

        assertEquals(ht.get("1"), "1");
        assertEquals(ht.get("2"), "2");
        assertEquals(ht.get("3"), "3");
        assertEquals(ht.get("4"), "4");
        assertEquals(ht.get("5"), "5");
        assertEquals(ht.get("6"), "6");
        assertEquals(ht.get("7"), "7");
        assertEquals(ht.get("8"), null);
        assertEquals(ht.get("9"), null);
    }

}