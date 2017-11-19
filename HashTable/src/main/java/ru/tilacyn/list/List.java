package ru.tilacyn.list;


/**
 * doubly linked list for class HashTable
 * collisions are processed by chain method
 */
public class List {

    /**
     * class Node - additional class for List
     * stores pair {String key, String value}
     * also stores two references to the previous Node and to the next Node in the list
     * fields are public because Node is a private class
     * and there is no use of making them private because of four necessary setters and getters
     */
    private class Node {
        /**
         * stored pair {String key, String s}
         */
        public String s, key;

        /**
         * stored Node next
         * stored Node prev
         */
        public Node next, prev;

        /**
         * initializes next, prev by null
         * initializes key, s by arguments
         * @param key
         * @param s
         */
        Node(String key, String s) {
            next = prev = null;
            this.s = s;
            this.key = key;
        }
    }

    /**
     * head of the list
     */
    private Node head = null;

    /**
     * number of pairs {key, value} in the list
     */
    private int size = 0;

    /**
     * adds Node with {key, s} to the list
     * @param key
     * @param s
     */
    public void add(String key, String s) {
        Node node = new Node(key, s);
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    /**
     * @return size of the list
     */
    public int size() {
        return size;
    }


    /**
     * check whether this key appears in the list
     * @param key
     * @return if appears then true else false
     */
    public boolean contains(String key) {
        Node cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) return true;
            cur = cur.next;
        }
        return false;
    }

    /**
     * removes key and value stored by key in list
     * actually it removes the only first appearance of key in list
     * @param key
     * @return if key appears in table, then value stored by key, else null
     */
    public String remove(String key) {
        size--;
        Node cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (cur.equals(head)) {
                    if (cur.next == null) {
                        head = null;
                    } else {
                        head = cur.next;
                        head.prev = null;
                    }
                } else {
                    if (cur.next == null) {
                        cur.prev.next = null;
                    } else {
                        cur.prev.next = cur.next;
                        cur.next.prev = cur.prev;
                    }
                }
                return cur.s;
            }
            cur = cur.next;
        }
        size++;
        return null;
    }

    /**
     * @return whether list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return if head != null then list head key else null
     */
    public String getHeadKey() {
        if (head == null) {
            return null;
        }
        return head.key;
    }

    /**
     * doesn't change list
     * @param key
     * @return if key appears in table then value stored by key else null
     */
    public String get(String key) {
        Node cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.s;
            }
            cur = cur.next;
        }
        return null;
    }

}
