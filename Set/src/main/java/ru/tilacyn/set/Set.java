package ru.tilacyn.set;

/**
 * unbalanced BST with operations add, contains, size
 *
 * @param <T>
 */

public class Set<T extends Comparable<? super T>> {
    private class Node<T extends Comparable<? super T>> {
        public T value;
        public Node l = null;
        public Node r = null;

        Node(T value) {
            this.value = value;
        }

    }

    /**
     * tree head
     */

    private Node tree = null;

    private int size = 0;

    /**
     * compares first and second
     *
     * @param first
     * @param second
     * @return true if first <= second, false otherwise
     */

    private boolean leq(T first, T second) {
        return (first.compareTo(second) <= 0);
    }

    /**
     * adds a value to our set
     *
     * @param value
     * @return false if set already contains the value, true otherwise
     */

    public boolean add(T value) {
        Node<T> cur = tree;
        Node<T> ancestor = null;
        boolean lastDir = false;
        if (contains(value)) {
            return false;
        }

        while (cur != null) {
            ancestor = cur;
            if (leq(cur.value, value)) {
                cur = cur.r;
                lastDir = true;
            } else {
                cur = cur.l;
                lastDir = false;
            }
        }

        if (tree != null) {
            if (lastDir) {
                ancestor.r = new Node<T>(value);
            } else {
                ancestor.l = new Node<T>(value);
            }
        } else {
            tree = new Node<T>(value);
        }
        size++;
        return true;
    }

    /**
     * checks whether there is such value in set
     *
     * @param value
     * @return true if there is such value stored, false otherwise
     */


    public boolean contains(T value) {
        Node<T> cur = tree;
        while (cur != null) {
            if (cur.value.compareTo(value) == 0) {
                return true;
            }
            if (leq(cur.value, value)) {
                cur = cur.r;
            } else {
                cur = cur.l;
            }
        }

        return false;

    }

    /**
     * @return size of our set
     */

    public int size() {
        return size;
    }
}
