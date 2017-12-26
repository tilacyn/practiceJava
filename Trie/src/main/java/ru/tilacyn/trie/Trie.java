package ru.tilacyn.trie;


import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * This class realises a data structure named 'Trie'
 * which is actually a set of strings on alphabet of english letters [a, b, ..., z]
 */
public class Trie implements Serializable {
    /**
     * Private class which describes a vertex in our tree
     * each vertex has an array of its childrenNumber so that we could pass with O(1) time
     * a number of childrenNumber
     * and a boolean flag whether it is an end of any string
     */
    private class Node implements Serializable {

        /**
         * array of childrenNumber
         */
        private Node next[] = new Node[30];

        /**
         * is true if this Node is the end of any word in Trie
         * else false
         */
        private boolean isTerminal = false;

        /**
         * number of children
         */
        private int childrenNumber = 0;
    }

    /**
     * A head of our tree
     */
    private Node head = new Node();

    /**
     * counts number of s.charAt(i) position in alphabet     *
     *
     * @param s string
     * @param i index
     * @return char number
     */
    public int getNum(@NotNull String s, int i) {
        return s.charAt(i) - 'a';
    }

    /**
     * Work time : O(|s|)
     *
     * @param s string
     * @return whether trie contains the exact string
     */
    public boolean contains(@NotNull String s) {
        Node cur = head;
        int pos = 0;
        while (pos < s.length() && cur.next[getNum(s, pos)] != null) {
            cur = cur.next[getNum(s, pos)];
            pos++;
        }
        return pos == s.length() && cur.isTerminal;
    }

    /**
     * Adds a string to our trie
     * work time: O(|s|)
     *
     * @param s string
     * @return false if trie already contains this string, true if doesn't
     */
    public boolean add(@NotNull String s) {
        Node cur = head;
        int pos = 0;
        boolean contains = contains(s);
        if (contains) {
            return false;
        }

        while (pos < s.length() && cur.next[getNum(s, pos)] != null) {
            cur.childrenNumber++;
            cur = cur.next[getNum(s, pos)];
            pos++;
        }

        while (pos < s.length()) {
            cur.childrenNumber++;
            Node next = new Node();
            cur.next[getNum(s, pos)] = next;
            cur = next;
            pos++;
        }

        cur.childrenNumber++;

        cur.isTerminal = true;
        return true;
    }

    /**
     * Removes the string from our trie
     * work time: O(|s|)
     *
     * @param s string
     * @return false if trie doesn't contain s, true if it does
     */
    public boolean remove(@NotNull String s) {
        if (!contains(s))
            return false;
        Node cur = head;
        int pos = 0;
        while (pos < s.length() && cur.next[getNum(s, pos)] != null) {
            cur.childrenNumber--;
            if (cur.next[getNum(s, pos)].childrenNumber <= 1) {
                cur.next[getNum(s, pos)] = null;
                return true;
            }
            cur = cur.next[getNum(s, pos)];
            pos++;
        }
        cur.isTerminal = false;
        return true;
    }

    /**
     * @return a number of strings in our trie
     */
    public int size() {
        return head.childrenNumber;
    }

    /**
     * Counts a number of strings in our trie which start with the prefix given
     *
     * @param s prefix
     * @return a number of strings in trie which start with the prefix
     */
    public int howManyStartWithPrefix(@NotNull String s) {
        Node cur = head;
        int pos = 0;
        while (pos < s.length() && cur.next[getNum(s, pos)] != null) {
            cur = cur.next[getNum(s, pos)];
            pos++;
        }
        if (pos == s.length())
            return cur.childrenNumber;
        return 0;
    }

    /**
     * A function inherited from a Serializable interface
     * it serializes object using output stream
     *
     * @param out output stream
     * @throws IOException if problems with writing occurred
     */
    public void serialize(@NotNull OutputStream out) throws IOException {
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(this);
        objectOut.flush();
        objectOut.close();
    }

    /**
     * A function inherited from a Serializable interface
     * it deserializes object using input stream
     *
     * @param in input stream
     * @throws IOException            if problems with reading occurred
     * @throws ClassNotFoundException if problems with reading occurred
     */
    public void deserialize(@NotNull InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(in);
        Trie tmp = (Trie) objectIn.readObject();
        head = tmp.head;
        objectIn.close();
    }
}

