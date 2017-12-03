package ru.tilacyn.set;

/**
 * unbalanced BST with operations add, contains, size
 *
 * @param <T> - type of values stored
 */
public class Set<T extends Comparable<? super T>> {

    /**
     * vertice of BST with value of type T and children of type Node stored
     *
     * @param <T> type of value stored
     */
    private class Node<T extends Comparable<? super T>> {
        private T value;
        private Node l = null;
        private Node r = null;

        Node(T value) {
            this.value = value;
        }
    }

    /** tree head */
    private Node tree = null;

    /** set size */
    private int size = 0;

    /**
     * compares first and second
     *
     * @param first
     * @param second
     * @return true if first <= second, false otherwise
     */
    private boolean lessOrEqual(T first, T second) {
        return (first.compareTo(second) <= 0);
    }

    /**
     * adds a value to our set
     *
     * @param value of type T is added to the set
     * @return false if set already contains the value, true otherwise
     */
    public boolean add(T value) {
        Node<T> cur = tree;
        Node<T> ancestor = null;
        int lastDirection = 0;
        if (contains(value)) {
            return false;
        }

        while (cur != null) {
            ancestor = cur;
            if (lessOrEqual(cur.value, value)) {
                cur = cur.r;
                lastDirection = 1;
            } else {
                cur = cur.l;
                lastDirection = 0;
            }
        }

        if (tree != null) {
            if (lastDirection == 1) {
                ancestor.r = new Node(value);
            } else {
                ancestor.l = new Node(value);
            }
        } else {
            tree = new Node(value);
        }
        size++;
        return true;
    }

    /**
     * checks whether there is such value in set
     *
     * @param value is looked for in the set
     * @return true if there is such value stored, false otherwise
     */
    public boolean contains(T value) {
        Node<T> cur = tree;
        while (cur != null) {
            if (cur.value.compareTo(value) == 0) {
                return true;
            }
            if (lessOrEqual(cur.value, value)) {
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
