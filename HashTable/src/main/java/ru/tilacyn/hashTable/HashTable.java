package ru.tilacyn.hashTable;

import ru.tilacyn.list.*;

/**
 * HashTable using lists with closed access
 */

public class HashTable {

    /**
     * list array where data is stored
     * exact list top put data is chosen from array by counting hash function
     * data[hash(key)]
     */
    private List[] data;

    /**
     * number of different keys in table
     */
    private int size = 0;

    /**
     * Constructor
     *
     * @param n - list array size,
     */
    public HashTable(int n) {
        data = new List[n];
        for (int i = 0; i < n; i++) {
            data[i] = new List();
        }
    }

    /**
     * returns a result of hash function applied to s
     * @param s string
     * @return integer result
     */
    public int getHash(String s) {
        return s.hashCode() % data.length;
    }

    /**
     * @return size of our table - number of different keys stored in table
     */
    public int size() {
        return size;
    }

    /**
     * Removes key and value from the table
     *
     * @param key
     * @return value or null if key doesn't appear in table
     */
    public String remove(String key) {
        int i = getHash(key);
        size--;
        return data[i].remove(key);
    }

    /**
     * checks if key appears in table
     *
     * @param key
     * @return if appears true else false
     */
    public boolean contains(String key) {
        int i = getHash(key);
        return data[i].contains(key);
    }

    /**
     * @param key
     * @return value or null if key doesn't appear in table
     */
    public String get(String key) {
        int i = getHash(key);
        return data[i].get(key);
    }

    /**
     * puts a pair <key, value> in table
     * if table.size is twice as big as data.size then rebuild method is called
     *
     * @param key - key
     * @param s   - value
     * @return value if this key appears in table else null иначе
     */
    public String put(String key, String s) {
        if (size > 2 * data.length) {
            rebuild();
        }
        int i = getHash(key);
        String res = null;
        if (contains(key)) {
            res = remove(key);
        }
        data[i].add(key, s);
        size++;
        return res;
    }

    /**
     * clears our table, list array size remains
     */
    public void clear() {
        size = 0;
        int n = data.length;
        data = new List[n];
        for (int i = 0; i < n; i++) {
            data[i] = new List();
        }
    }

    /**
     * rebuilds our HashTable
     * it increases size of data array twice
     * and copies all data to a new List array
     * rehashing is being done since now our hash-function has changed
     */
    public void rebuild() {
        int n = data.length;
        List[] oldData = data.clone();
        data = new List[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            data[i] = new List();
        }

        for (int i = 0; i < n; i++) {
            while (!oldData[i].isEmpty()) {
                String headKey = oldData[i].getHeadKey();
                String headValue = oldData[i].get(headKey);
                put(headKey, headValue);
                oldData[i].remove(headKey);
            }
        }
    }
}

