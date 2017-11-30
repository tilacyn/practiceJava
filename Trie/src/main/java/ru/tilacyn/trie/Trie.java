package ru.tilacyn.trie;


import java.io.*;

import java.util.*;

/**
 * this class realises a data structure named 'Trie'
 * which is actually a set of strings on alphabet of english letters [a, b, ..., z]
 */


public class Trie implements java.io.Serializable {

    /**
     * private class which describes a vertex in our tree
     * each vertex has an array of its children so that we could pass with O(1) time
     * a number of children
     * and a boolean flag whether it is an end of any string
     */

    private class Node {

        /**
         * array of children
         */

        Node next[];

        /**
         * flag
         */

        boolean isTerminal;

        /**
         * number of children
         */

        int children;
        Node(){
            next = new Node[30];
            isTerminal = false;
            children = 0;
        }
    }

    /**
     * a head of our tree
     */

    Node head;



    Trie(){
        head = new Node();
    }

    /**
     * counts number of s.charAt(i) position in alphabet     *
     * @param s - string
     * @param i - index
     * @return char number
     */

    public int getNum(String s, int i){
        return s.charAt(i) - 'a';
    }

    /**
     * worktime : O(|s|)
     * @param s
     * @return whether trie contains the exact string
     */

    public boolean contains(String s){
        Node cur = head;
        int pos = 0;
        while(pos < s.length() && cur.next[getNum(s, pos)] != null){
            cur = cur.next[getNum(s, pos)];
            pos++;
        }
        if(pos == s.length() && cur.isTerminal) return true;
        else return false;
    }

    /**
     * adds a string to our trie
     * @param s - string
     * @return false - if trie already contains this string, true - if doesn't
     */

    public boolean add(String s) {
        Node cur = head;
        int pos = 0;
        boolean contains = contains(s);
        if(contains)
            return false;

        while(pos < s.length() && cur.next[getNum(s, pos)] != null){
            cur.children++;
            cur = cur.next[getNum(s, pos)];
            pos++;
        }

        while(pos < s.length()){
            cur.children++;
            Node next = new Node();
            cur.next[getNum(s, pos)] = next;
            cur = next;
            pos++;
        }

        cur.children++;

        cur.isTerminal = true;
        return true;
    }

    /**
     * removes the string from our trie
     * @param s - string
     * @return false - if trie doesn't contain s, true - if it does
     */

    public boolean remove(String s){
        if(!contains(s))
            return false;
        Node cur = head;
        int pos = 0;
        while(pos < s.length() && cur.next[getNum(s, pos)] != null) {
            cur.children--;
            if(cur.next[getNum(s, pos)].children <= 1) {
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

    public int size(){
        return head.children;
    }

    /**
     * counts a number of strings in our trie which start with the prefix given
     * @param s - prefix
     * @return a number strings in trie which start with the prefix
     */

    public int howManyStartWithPrefix(String s){
        Node cur = head;
        int pos = 0;
        while(pos < s.length() && cur.next[getNum(s, pos)] != null){
            cur = cur.next[getNum(s, pos)];
            pos++;
        }
        if(pos == s.length())
            return cur.children;
        return 0;
    }

    /**
     * a function inherited from a Serializable interface
     * it serializes object using output stream
     * @param out - output stream
     * @throws IOException
     */

    void serialize(OutputStream out) throws IOException{
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(this);
        objectOut.flush();
        objectOut.close();
    }

    /**
     * a function inherited from a Serializable interface
     * it deserializes object using input stream
     * @param in - input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */

    void deserialize(InputStream in) throws IOException, ClassNotFoundException{
        ObjectInputStream objectIn = new ObjectInputStream(in);
        Trie tmp = (Trie) objectIn.readObject();
        head = tmp.head;
        objectIn.close();
    }

}

