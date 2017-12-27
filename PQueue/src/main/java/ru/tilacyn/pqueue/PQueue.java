package ru.tilacyn.pqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PQueue<K> implements Iterable<K> {
    private Comparator<? super K> comparator = (o1, o2) -> ((Comparable<? super K>) o2).compareTo(o2);

    private K[] array = (K[]) new Object[2];

    private int size = 0;

    public PQueue(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public PQueue() {
    }

    void print() {
        for (K k : array) {
            System.out.print(k + " ");
        }
        System.out.print("\n");
    }


    private void swap(int i, int j) {
        K oldFirst = array[i];
        array[i] = array[j];
        array[j] = oldFirst;
    }

    private boolean less(int i, int j) {
        return comparator.compare(array[i], array[j]) > 0;
    }

    private boolean greaterEqual(int i, int j) {
        return !less(i, j);
    }

    private void siftDown(int i) {
        print();
        while (2 * i <= size) {
            int child = 2 * i;
            System.out.println(array[i] + " " + array[child]);
            if (child < size && less(child, child + 1)) {
                child++;
            }
            if (greaterEqual(i, child)) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }

    private void siftUp(int i) {
        while (i > 1) {
            int parent = i / 2;
            System.out.println(i + " wow " + parent);
            if (less(parent, i)) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
        System.out.println();
    }

    public void add(K k) {
        if (size + 1 == array.length) {
            K[] newArray = (K[]) new Object[3 * size];
            System.arraycopy(array, 0, newArray, 0, size + 1);
            array = newArray;
        }
        array[++size] = k;
        siftUp(size);
    }

    public K poll() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        K result = array[1];
        swap(1, size);
        size--;
        siftDown(1);
        return result;
    }


    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        K[] array = (K[]) new Object[2];
    }


    @Override
    public Iterator<K> iterator() {
        return new PQueueIterator();
    }

    private class PQueueIterator implements Iterator<K> {

        private PQueue<K> copy;

        public PQueueIterator() {
            copy = new PQueue<>(comparator);
            for (int i = 1; i <= size; i++)
                copy.add(array[i]);
        }

        public boolean hasNext() {
            return copy.size != 0;
        }

        public K next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.poll();
        }
    }


}
