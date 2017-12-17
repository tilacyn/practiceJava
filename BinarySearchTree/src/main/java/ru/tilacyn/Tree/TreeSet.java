package ru.tilacyn.Tree;

import com.sun.istack.internal.NotNull;
import ru.tilacyn.MyTreeSet.MyTreeSet;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * a class with an implementation of MyTreeSet interface
 * elements of type E are stored in unbalanced binary search tree in an iterable ordered way
 *
 * @param <E> elements type
 */
public class TreeSet<E> implements MyTreeSet<E> {
    BinarySearchTree<E> bst = new BinarySearchTree<>();


    TreeSet() {
    }

    /**
     * a constructor that allows setting a comparator with your own
     * that influences the way elements will be iterated and stored
     * but doesn't influence methods like add, remove, contains
     *
     * @param comparator your own comparator
     */
    TreeSet(@NotNull Comparator<E> comparator) {
        bst = new BinarySearchTree<>(comparator);
    }

    /**
     * constructor that allows you to create another reference on an inner object bst
     *
     * @param other source TreeSet
     */
    TreeSet(@NotNull TreeSet other) {
        bst = other.bst;
    }

    /**
     * {@link MyTreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return bst.descendingIterator();
    }

    /**
     * {@link MyTreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return new TreeSet<E>(this) {
            @Override
            public Iterator<E> iterator() {
                return super.descendingIterator();
            }

            @Override
            public Iterator<E> descendingIterator() {
                return super.iterator();
            }
        };
    }

    /**
     * {@link MyTreeSet#first()}
     **/
    @Override
    public E first() {
        return bst.first();
    }

    /**
     * {@link MyTreeSet#last()}
     */
    @Override
    public E last() {
        return bst.last();
    }

    /**
     * {@link MyTreeSet#lower(E)}
     */
    @Override
    public E lower(E e) {
        return bst.lower(e);
    }

    /**
     * {@link MyTreeSet#floor(E)}     *
     */
    @Override
    public E floor(E e) {
        return bst.floor(e);
    }

    /**
     * {@link MyTreeSet#ceiling(E)}     *
     */
    @Override
    public E ceiling(E e) {
        return bst.ceiling(e);
    }

    /**
     * {@link MyTreeSet#higher(E)}     *
     */
    @Override
    public E higher(E e) {
        return bst.higher(e);
    }

    /**
     * Time O(1)
     * {@link java.util.Set#size}     *
     */
    @Override
    public int size() {
        return bst.size();
    }

    /**
     * Time O(1)
     * {@link Set#isEmpty()}      *
     */
    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    /**
     * Time O(size)
     * {@link java.util.Set#contains(Object)}      *
     */
    @Override
    public boolean contains(Object o) {
        return bst.contains(o);
    }

    /**
     * {@link Set#iterator()}     *
     */
    @Override
    public Iterator<E> iterator() {
        return bst.iterator();
    }

    /**
     * Time O(size^2)
     * {@link Set#toArray()}     *
     */
    @Override
    public Object[] toArray() {
        return bst.toArray();
    }

    /**
     * Time O(size^2)
     * {@link Set#toArray(T[])}     *
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return bst.toArray(a);
    }

    /**
     * Time O(size)
     * {@link java.util.Set#add(E)}     *
     */
    @Override
    public boolean add(E e) {
        return bst.add(e);
    }

    /**
     * Time O(size)
     * {@link java.util.Set#remove(Object)}
     */
    @Override
    public boolean remove(Object o) {
        return bst.remove(o);
    }

    /**
     * Time O(collection.size * size)
     * {@link java.util.Set#containsAll(Collection)}
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return bst.containsAll(c);
    }

    /**
     * Time O((collection.size + size) * size)
     * {@link java.util.Set#addAll(Collection)}
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return bst.addAll(c);
    }

    /**
     * Time O(n^2)
     * {@link java.util.Set#retainAll(Collection)}
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return bst.retainAll(c);
    }

    /**
     * Time O(collection.size * size)
     * {@link java.util.Set#removeAll(Collection)}
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return bst.removeAll(c);
    }

    /**
     * Time O(1)
     * {@link Set#clear()}
     */
    @Override
    public void clear() {
        bst.clear();
    }

    public void print() {
        bst.print();
    }

    /**
     * class that stores elements in an unbalanced binary search tree
     * has private access
     *
     * @param <E> elements type
     */
    private class BinarySearchTree<E> implements Iterable<E> {
        private Node root = null;
        private Comparator<E> comparator;
        private int size = 0;

        BinarySearchTree() {
            comparator = (o1, o2) -> ((Comparable<E>) o1).compareTo(o2);
        }

        private BinarySearchTree(@NotNull Comparator<E> comparator) {
            root = null;
            size = 0;
            this.comparator = comparator;
        }

        private Iterator<E> descendingIterator() {
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

        private E first() {
            if (isEmpty()) return null;
            return min(root);
        }

        private E last() {
            if (isEmpty()) return null;
            return max(root);
        }

        private E lower(E e) {
            Node current = findNode(e);
            System.out.println(current.value);
            if (comparator.compare(e, current.value) < 0 || (comparator.compare(e, current.value) == 0 & current.l == null)) {
                if (current.value.equals(first())) {
                    return null;
                }
                while (current.parent != null && comparator.compare((E) current.parent.value, (E) current.value) > 0) {
                    current = current.parent;
                }
                if (current.parent != null) {
                    return current.parent.value;
                } else {
                    return first();
                }
            }

            if (comparator.compare(e, current.value) > 0) {
                return current.value;
            }

            return max(current.l);
        }

        private E floor(E e) {
            Node current = findNode(e);
            if (comparator.compare(e, current.value) < 0) {
                if (current.value.equals(first())) {
                    return null;
                }
                while (current.parent != null && comparator.compare((E) current.parent.value, (E) current.value) > 0) {
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

        private E ceiling(E e) {
            Node current = findNode(e);
            if (comparator.compare(e, current.value) > 0) {
                if (current.value.equals(last())) {
                    return null;
                }
                while (current.parent != null && comparator.compare((E) current.parent.value, (E) current.value) < 0) {
                    current = current.parent;
                }
                if (current.parent != null) {
                    return (E) current.parent.value;
                } else {
                    return last();
                }
            }

            if (comparator.compare(e, current.value) < 0) {
                return current.value;
            }

            return e;
        }

        private E higher(E e) {
            Node current = findNode(e);

            if (comparator.compare(e, current.value) > 0 || (comparator.compare(e, current.value) == 0 & current.r == null)) {
                if (current.value.equals(last())) {
                    return null;
                }
                while (current.parent != null && comparator.compare((E) current.parent.value, (E) current.value) < 0) {
                    current = current.parent;
                }
                if (current.parent != null) {
                    return (E) current.parent.value;
                } else {
                    return last();
                }
            }

            if (comparator.compare(e, current.value) < 0) {
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
                if (comparator.compare(e, current.value) > 0) {
                    if (current.r == null) {
                        break;
                    }
                    current = current.r;
                } else if (comparator.compare(e, current.value) < 0) {
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

        private int size() {
            return size;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private boolean contains(Object o) {
            Node current = root;
            while (current != null) {
                if (comparator.compare((E) o, (E) current.value) < 0) {
                    current = current.l;
                } else if (comparator.compare((E) o, (E) current.value) > 0) {
                    current = current.r;
                } else {
                    return true;
                }
            }
            return false;
        }

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

        private Object[] toArray() {
            Object[] result = new Object[this.size()];
            int i = 0;
            for (E element : this) {
                result[i++] = element;
            }
            return result;
        }

        private <T> T[] toArray(T[] a) {
            if (a.length < this.size()) {
                a = (T[]) new Object[this.size()];
            }
            int i = 0;
            for (E element : this) {
                a[i++] = (T) element;
            }
            return a;
        }

        private void print() {
            print(root);
            System.out.println();
        }

        private void print(Node node) {
            if (node == null) return;
            System.out.print(node.value);
            System.out.print(" (");
            print(node.l);
            System.out.print(") ");
            System.out.print(" (");
            print(node.r);
            System.out.print(") ");
        }

        private boolean add(E e) {
            Node current = root;
            if (root == null) {
                root = new Node(e, null, null, null);
                size++;
                return true;
            }
            Node parent = null;
            boolean leftSon = false;
            while (current != null) {
                if (comparator.compare(e, current.value) < 0) {
                    parent = current;
                    current = current.l;
                    leftSon = true;
                } else if (comparator.compare(e, current.value) > 0) {
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

        private boolean remove(Object o) {
            Node current = findNode((E) o);

            if (current == null) {
                return false;
            }

            if (!current.value.equals(o)) {
                return false;
            }

            size--;

            if (processRemoveCases(current)) {
                return true;
            }

            E higher = higher((E) o);
            Node currentHigher = findNode(higher);

            processHigherNode(currentHigher);

            current.value = currentHigher.value;

            return true;
        }

        private boolean processRemoveCases(Node current) {
            if (current.l == null && current.r == null) {
                if (current.equals(root)) {
                    root = null;
                } else if (comparator.compare(current.value, current.parent.value) > 0) {
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
                } else if (comparator.compare(current.value, current.parent.value) > 0) {
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
                } else if (comparator.compare(current.value, current.parent.value) > 0) {
                    current.l.parent = current.parent;
                    current.parent.r = current.l;
                } else {
                    current.l.parent = current.parent;
                    current.parent.l = current.l;
                }
                return true;
            }
            return false;
        }

        private void processHigherNode(Node currentHigher) {
            if (currentHigher.equals(root)) {
                root = currentHigher.r;
            } else if (comparator.compare(currentHigher.value, currentHigher.parent.value) > 0) {
                currentHigher.r.parent = currentHigher.parent;
                currentHigher.parent.r = currentHigher.r;
            } else {
                currentHigher.r.parent = currentHigher.parent;
                currentHigher.parent.l = currentHigher.r;
            }
        }

        private boolean containsAll(Collection<?> c) {
            for (Object element : c) {
                if (!contains(element)) {
                    return false;
                }
            }
            return true;
        }

        private boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c) {
                boolean contains = add(element);
                result |= contains;
            }
            return result;
        }

        private boolean retainAll(Collection<?> c) {
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

        private boolean removeAll(Collection<?> c) {
            boolean result = true;
            for (Object element : c) {
                result |= remove(element);
            }
            return result;
        }

        private void clear() {
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
}