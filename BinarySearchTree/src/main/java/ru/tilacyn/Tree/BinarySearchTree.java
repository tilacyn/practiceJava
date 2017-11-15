package ru.tilacyn.Tree;

import ru.tilacyn.MyTreeSet.MyTreeSet;

import java.util.*;

public class BinarySearchTree<E> implements MyTreeSet<E> {
    private Node root;
    private Comparator<E> comparator;
    private int size;


    private int compare(E x, E y) {
        if (comparator == null) {
            return ((Comparable<E>) x).compareTo(y);
        } else {
            return comparator.compare(x, y);
        }
    }


    BinarySearchTree() {
        root = null;
        size = 0;
    }

    BinarySearchTree(Comparator<E> comparator) {
        root = null;
        size = 0;
        this.comparator = comparator;
    }


    /**
     * {@link MyTreeSet#descendingIterator()}
     **/

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            E current = last();

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E now = current;
                current = lower(current);
                return now;
            }
        };
    }


    /**
     * {@link MyTreeSet#descendingSet()}
     **/

    @Override
    public MyTreeSet<E> descendingSet() {
        return null;
    }


    /**
     * {@link MyTreeSet#first()}
     **/

    @Override
    public E first() {
        return min(root);
    }

    /**
     * {@link MyTreeSet#last()}
     */

    @Override
    public E last() {
        return max(root);
    }


    /**
     * {@link MyTreeSet#lower(E)}
     */

    @Override
    public E lower(E e) {
        Node current = findNode(e);
        System.out.println(current.value);
        if (compare(e, current.value) < 0 || (compare(e, current.value) == 0 & current.l == null)) {
            if (current.value.equals(first())) {
                return null;
            }
            while (current.parent != null && compare((E) current.parent.value, (E) current.value) > 0) {
                current = current.parent;
            }
            if (current.parent != null) {
                return current.parent.value;
            } else {
                return first();
            }
        }

        if (compare(e, current.value) > 0) {
            return current.value;
        }

        return max(current.l);
    }

    /**
     * {@link MyTreeSet#floor(E)}     *
     */

    @Override
    public E floor(E e) {
        Node current = findNode(e);
        if (compare(e, current.value) < 0) {
            if (current.value.equals(first())) {
                return null;
            }
            while (current.parent != null && compare((E) current.parent.value, (E) current.value) > 0) {
                current = current.parent;
            }
            if (current.parent != null) {
                return current.parent.value;
            } else {
                return first();
            }
        }

        return current.value;
    }

    /**
     * {@link MyTreeSet#ceiling(E)}     *
     */

    @Override
    public E ceiling(E e) {
        Node current = findNode(e);
        if (compare(e, current.value) > 0) {
            if (current.value.equals(last())) {
                return null;
            }
            while (current.parent != null && compare((E) current.parent.value, (E) current.value) < 0) {
                current = current.parent;
            }
            if (current.parent != null) {
                return (E) current.parent.value;
            } else {
                return last();
            }
        }

        if (compare(e, current.value) < 0) {
            return current.value;
        }

        return e;
    }


    /**
     * {@link MyTreeSet#higher(E)}     *
     */

    @Override
    public E higher(E e) {
        Node current = findNode(e);

        if (compare(e, current.value) > 0 || (compare(e, current.value) == 0 & current.r == null)) {
            if (current.value.equals(last())) {
                return null;
            }
            while (current.parent != null && compare((E) current.parent.value, (E) current.value) < 0) {
                current = current.parent;
            }
            if (current.parent != null) {
                return (E) current.parent.value;
            } else {
                return last();
            }
        }

        if (compare(e, current.value) < 0) {
            return current.value;
        }

        return min(current.r);
    }

    private E min(Node current) {
        while (current.l != null) {
            current = current.l;
        }
        return current.value;
    }

    private E max(Node current) {
        while (current.r != null) {
            current = current.r;
        }
        return current.value;
    }

    private Node findNode(E e) {
        Node current = root;
        while (current != null) {
            if (compare(e, current.value) > 0) {
                if (current.r == null) {
                    break;
                }
                current = current.r;
            } else if (compare(e, current.value) < 0) {
                if (current.l == null) {
                    break;
                }
                current = current.l;
            } else {
                return current;
            }
        }
        return current;
    }


    /**
     * Time O(1)
     * {@link java.util.Set#size}     *
     */

    @Override
    public int size() {
        return size;
    }

    /**
     * Time O(1)
     * {@link Set#isEmpty()}      *
     */

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Time O(size)
     * {@link java.util.Set#contains(Object)}      *
     */

    @Override
    public boolean contains(Object o) {
        Node current = root;
        while (current != null) {
            if (compare((E) o, (E) current.value) < 0) {
                current = current.l;
            } else if (compare((E) o, (E) current.value) > 0) {
                current = current.r;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * {@link Set#iterator()}     *
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            E current = first();

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E now = current;
                current = higher(current);
                return now;
            }
        };
    }

    /**
     * Time O(size^2)
     * {@link Set#toArray()}     *
     */

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size()];
        int i = 0;
        for (E element : this) {
            result[i++] = element;
        }
        return result;
    }

    /**
     * Time O(size^2)
     * {@link Set#toArray(T[])}     *
     */

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size()) {
            a = (T[]) new Object[this.size()];
        }
        int i = 0;
        for (E element : this) {
            a[i++] = (T) element;
        }
        return a;
    }


    public void print() {
        print(root);
        System.out.println();
    }

    public void print(Node node) {
        if (node == null) return;
        System.out.print(node.value);
        System.out.print(" (");
        print(node.l);
        System.out.print(") ");
        System.out.print(" (");
        print(node.r);
        System.out.print(") ");
    }


    /**
     * Time O(size)
     * {@link java.util.Set#add(E)}     *
     */

    @Override
    public boolean add(E e) {
        Node current = root;
        if (root == null) {
            root = new Node(e, null, null, null);
            size++;
            return true;
        }
        Node parent = null;
        boolean leftSon = false;
        while (current != null) {
            if (compare(e, current.value) < 0) {
                parent = current;
                current = current.l;
                leftSon = true;
            } else if (compare(e, current.value) > 0) {
                parent = current;
                current = current.r;
                leftSon = false;
            } else {
                return false;
            }
        }

        if (leftSon) {
            parent.l = new Node(e, null, null, parent);
        } else {
            parent.r = new Node(e, null, null, parent);
        }

        size++;
        return true;
    }

    /**
     * Time O(size)
     * {@link java.util.Set#remove(Object)}
     */

    @Override
    public boolean remove(Object o) {
        Node current = findNode((E) o);

        if (current == null) {
            return false;
        }

        if (!current.value.equals(o)) {
            return false;
        }

        size--;

        if (current.l == null && current.r == null) {
            if (current.equals(root)) {
                root = null;
            } else if (compare(current.value, current.parent.value) > 0) {
                current.parent.r = null;
            } else {
                current.parent.l = null;
            }
            return true;
        }

        if (current.l == null) {
            System.out.println("L = null");
            if (current.equals(root)) {
                root = current.r;
                root.parent = null;
            } else if (compare(current.value, current.parent.value) > 0) {
                current.r.parent = current.parent;
                current.parent.r = current.r;
            } else {
                current.r.parent = current.parent;
                current.parent.l = current.r;
            }
            return true;
        }

        if (current.r == null) {
            System.out.println("R = null");
            if (current.equals(root)) {
                root = current.l;
                root.parent = null;
            } else if (compare(current.value, current.parent.value) > 0) {
                current.l.parent = current.parent;
                current.parent.r = current.l;
            } else {
                current.l.parent = current.parent;
                current.parent.l = current.l;
            }
            return true;
        }

        E higher = higher((E) o);
        Node currentHigher = findNode(higher);

        if (currentHigher.equals(root)) {
            root = currentHigher.r;
        } else if (compare(currentHigher.value, currentHigher.parent.value) > 0) {
            currentHigher.r.parent = currentHigher.parent;
            currentHigher.parent.r = currentHigher.r;
        } else {
            currentHigher.r.parent = currentHigher.parent;
            currentHigher.parent.l = currentHigher.r;
        }

        current.value = currentHigher.value;

        return true;
    }


    /**
     * Time O(collection.size * size)
     * {@link java.util.Set#containsAll(Collection)}
     */

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Time O((collection.size + size) * size)
     * {@link java.util.Set#addAll(Collection)}
     */

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            boolean contains = add(element);
            result |= contains;
        }
        return result;
    }


    /**
     * Time O(n^2)
     * {@link java.util.Set#retainAll(Collection)}
     */

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        E[] toBeRemoved = (E[]) new Object[this.size];
        int count = 0;
        for (E element : this) {
            //System.out.println(element);
            if (!c.contains(element)) {
                toBeRemoved[count] = element;
                count++;
                result = true;
            }
        }

        for (int i = 0; i < count; i++) {
            System.out.println(toBeRemoved[i]);
            remove(toBeRemoved[i]);
        }

        return result;
    }


    /**
     * Time O(collection.size * size)
     * {@link java.util.Set#removeAll(Collection)}
     */

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true;
        for (Object element : c) {
            result |= remove(element);
        }
        return result;
    }


    /**
     * Time O(1)
     * {@link Set#clear()}
     */

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private class Node {
        Node(E value, Node l, Node r, Node parent) {
            this.value = value;
            this.l = l;
            this.r = r;
            this.parent = parent;
        }

        private E value;
        private Node l;
        private Node r;
        private Node parent;
    }
}
