package ru.tilacyn.Tree;

import javafx.util.Pair;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class BinarySearchTreeTest {
    @Test
    public void descendingIterator() throws Exception {
        BinarySearchTree<Double> bst = new BinarySearchTree<>();
        bst.add(3.4);
        bst.add(4.3);
        bst.add(2.1);
        bst.add(-5.4);

        Iterator<Double> it = bst.descendingIterator();

        assert it.hasNext();
        assertEquals(4.3, (double) it.next(), 0.1);

        assert it.hasNext();
        assertEquals(3.4, (double) it.next(), 0.1);

        assert it.hasNext();
        assertEquals(2.1, (double) it.next(), 0.1);

        assert it.hasNext();
        assertEquals(-5.4, (double) it.next(), 0.1);


        assert !it.hasNext();
    }

    @Test
    public void descendingSet() throws Exception {
    }

    @Test
    public void first() throws Exception {
        BinarySearchTree<Double> bst = new BinarySearchTree<>();
        bst.add(2.3);
        assertEquals((Double) 2.3, bst.first());
        bst.add(4.5);
        assertEquals((Double) 2.3, bst.first());
        bst.add(1.0);
        assertEquals((Double) 1.0, bst.first());
        bst.add(-5.6);
        bst.add(100.3);
        bst.add(3.141592);
        bst.add(3.333333);
        bst.add(0.0000002);
    }

    @Test
    public void last() throws Exception {
        BinarySearchTree<Double> bst = new BinarySearchTree<>();
        bst.add(2.3);
        assertEquals((Double) 2.3, bst.last());
        bst.add(4.5);
        assertEquals((Double) 4.5, bst.last());
        bst.add(1.0);
        assertEquals((Double) 4.5, bst.last());
        bst.add(-5.6);
        assertEquals((Double) 4.5, bst.last());
        bst.add(100.3);
        assertEquals((Double) 100.3, bst.last());
        bst.add(3.141592);
        assertEquals((Double) 100.3, bst.last());
        bst.add(3.333333);
        assertEquals((Double) 100.3, bst.last());
        bst.add(0.0000002);
        assertEquals((Double) 100.3, bst.last());
    }

    @Test
    public void lower() throws Exception {
        BinarySearchTree<Pair<Integer, Integer>> bst = new BinarySearchTree<>(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                if (o1.getKey().equals(o2.getKey())) {
                    return o1.getValue().compareTo(o2.getValue());
                }
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        bst.add(new Pair<Integer, Integer>(3, 4));
        bst.add(new Pair<Integer, Integer>(5, 2));
        bst.add(new Pair<Integer, Integer>(1, 5));
        bst.add(new Pair<Integer, Integer>(6, 4));
        bst.add(new Pair<Integer, Integer>(6, 2));

        assertEquals(new Pair<Integer, Integer>(5, 2), bst.lower(new Pair<Integer, Integer>(6, 2)));
        assertEquals(new Pair<Integer, Integer>(6, 2), bst.lower(new Pair<Integer, Integer>(6, 3)));
        assertEquals(new Pair<Integer, Integer>(1, 5), bst.lower(new Pair<Integer, Integer>(3, 0)));
        assertEquals(new Pair<Integer, Integer>(6, 2), bst.lower(new Pair<Integer, Integer>(6, 4)));
        assertEquals(null, bst.lower(new Pair<Integer, Integer>(0, 0)));
        assertEquals(null, bst.lower(new Pair<Integer, Integer>(1, 5)));
    }

    @Test
    public void floor() throws Exception {
        BinarySearchTree<Pair<Integer, Integer>> bst = new BinarySearchTree<>(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                if (o1.getKey().equals(o2.getKey())) {
                    return o1.getValue().compareTo(o2.getValue());
                }
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        bst.add(new Pair<Integer, Integer>(-1, -1));
        bst.add(new Pair<Integer, Integer>(3, 3));
        bst.add(new Pair<Integer, Integer>(2, 2));
        bst.add(new Pair<Integer, Integer>(0, 0));
        bst.add(new Pair<Integer, Integer>(1, 1));
        bst.add(new Pair<Integer, Integer>(1, 2));
        bst.add(new Pair<Integer, Integer>(1, 3));
        bst.add(new Pair<Integer, Integer>(1, 0));
        bst.add(new Pair<Integer, Integer>(0, -1));

        assertEquals(new Pair<Integer, Integer>(3, 3), bst.floor(new Pair<Integer, Integer>(5, 2)));
        assertEquals(new Pair<Integer, Integer>(-1, -1), bst.floor(new Pair<Integer, Integer>(-1, -1)));
        assertEquals(null, bst.floor(new Pair<Integer, Integer>(-2, 0)));
        assertEquals(new Pair<Integer, Integer>(1, 3), bst.floor(new Pair<Integer, Integer>(1, 10)));

    }

    @Test
    public void ceiling() throws Exception {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add((Integer) 1);
        bst.print();
        bst.add((Integer) 4);
        bst.add((Integer) (-3));
        bst.add((Integer) 9);
        bst.add((Integer) 2);
        bst.print();
        bst.add((Integer) 6);
        bst.add((Integer) 0);

        assertEquals(bst.ceiling(-1), (Integer) 0);
        assertEquals(bst.ceiling(-4), (Integer) (-3));
        assertEquals(bst.ceiling(8), (Integer) 9);
        assertEquals(bst.ceiling(7), (Integer) 9);
        assertEquals(bst.ceiling(6), (Integer) 6);
        assertEquals(bst.ceiling(5), (Integer) 6);
        assertEquals(bst.ceiling(2), (Integer) 2);
        assertEquals(bst.ceiling(1), (Integer) 1);
    }

    @Test
    public void higher() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assert bst.add((Integer) 1);
        bst.print();
        assertFalse(bst.add((Integer) 1));
        assert bst.add((Integer) 4);
        assert bst.add((Integer) (-3));
        assert bst.add((Integer) 9);
        assert bst.add((Integer) 2);
        bst.print();
        assert !bst.add((Integer) 2);
        assert bst.add((Integer) 0);
        assert bst.add((Integer) 6);
        assert !bst.add((Integer) 0);

        assertEquals(bst.higher(-1), (Integer) 0);
        assertEquals(bst.higher(-4), (Integer) (-3));
        assertEquals(bst.higher(8), (Integer) 9);
        assertEquals(bst.higher(7), (Integer) 9);
        assertEquals(bst.higher(6), (Integer) 9);
        assertEquals(bst.higher(5), (Integer) 6);
        assertEquals(bst.higher(2), (Integer) 4);
        assertEquals(bst.higher(1), (Integer) 2);

        bst = new BinarySearchTree<>();
        for (int i = 0; i < 35; i++) {
            bst.add(i);
        }
        for (int i = 0; i < 34; i++) {
            assertEquals(i + 1, (int) bst.higher(i));
        }

    }

    @Test
    public void size() throws Exception {
        BinarySearchTree<String> bst = new BinarySearchTree<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return ((Integer) o1.hashCode()).compareTo(o2.hashCode());
            }
        });
        bst.add("Hello");
        assertEquals(1, bst.size());
        bst.add("I love you");
        assertEquals(2, bst.size());
        bst.add("Won't you");
        assertEquals(3, bst.size());
        bst.add("Tell me");
        assertEquals(4, bst.size());
        bst.add("Your name");
        assertEquals(5, bst.size());
        bst.remove("Your name");
        assertEquals(4, bst.size());

    }

    @Test
    public void isEmpty() throws Exception {
        BinarySearchTree<String> bst = new BinarySearchTree<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return ((Integer) o1.hashCode()).compareTo(o2.hashCode());
            }
        });
        bst.add("Hello");
        assert !bst.isEmpty();
        bst.add("I love you");
        bst.remove("I love you");
        assert !bst.isEmpty();
        bst.remove("Hello");
        assert bst.isEmpty();
    }

    @Test
    public void contains() throws Exception {
        BinarySearchTree<String> bst = new BinarySearchTree<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return ((Integer) o1.hashCode()).compareTo(o2.hashCode());
            }
        });
        BinarySearchTree<Integer> bstWeird = new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                Integer a = o1 * o1;
                int b = o2 * o2;
                return a.compareTo(b);
            }
        });

        bst.add("Hello");
        assert bst.add("I love you");
        bst.add("Can you");
        bst.add("Tell me");
        bst.add("Your name");

        bst.print();

        assert bst.contains("Hello");
        assert !bst.contains("Hello I love you");
        assert bst.contains("I love you");
        assert bst.contains("Can you");
        assert bst.contains("Tell me");
        assert bst.contains("Your name");

        bstWeird.add(3);
        bstWeird.add(4);
        assert !bstWeird.add(-4);


    }


    @Test
    public void iterator() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(0);
        bst.add(20);
        bst.add(10);
        bst.add(3);
        bst.add(-5);
        bst.add(-1);

        Iterator<Integer> it = bst.iterator();
        assert it.hasNext();
        assertEquals(-5, (int) it.next());
        assertEquals(-1, (int) it.next());
        assertEquals(0, (int) it.next());
        assertEquals(3, (int) it.next());
        assertEquals(10, (int) it.next());
        assertEquals(20, (int) it.next());
        assert !it.hasNext();

        for (Integer e : bst) {
            System.out.println(e);
        }

    }

    @Test
    public void toArray() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            bst.add(i);
            vector.add(i);
        }
        assertArrayEquals(vector.toArray(), bst.toArray());

    }

    @Test
    public void toArray1() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>();
        Integer[] res1 = new Integer[35];
        Integer[] res2 = new Integer[0];

        for (int i = 0; i < 35; i++) {
            bst.add(i);
            vector.add(i);
        }
        assertArrayEquals(vector.toArray(), bst.toArray(res1));
        assertArrayEquals(vector.toArray(), bst.toArray(res2));

    }

    @Test
    public void add() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assert bst.add((Integer) 1);
        bst.print();
        assertFalse(bst.add((Integer) 1));
        assert bst.add((Integer) 4);
        assert bst.add((Integer) (-3));
        assert bst.add((Integer) 9);
        assert bst.add((Integer) 2);
        bst.print();
        assert !bst.add((Integer) 2);
        assert bst.add((Integer) 0);
        assert bst.add((Integer) 6);
        assert !bst.add((Integer) 0);

        bst.print();
    }

    @Test
    public void remove() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertFalse(bst.remove(4));
        bst.add((Integer) 1);
        bst.add((Integer) 4);
        bst.add((Integer) (-3));
        bst.add((Integer) 9);
        bst.print();
        bst.remove(-3);
        assert !bst.contains((Integer) (-3));
        bst.add(-3);
        bst.add((Integer) 2);
        bst.remove(2);

        bst.print();

        assert !bst.contains((Integer) (2));
        bst.add((Integer) 6);
        bst.add((Integer) 0);
        bst.remove(1);
        assert !bst.remove(1);
        assert !bst.contains((Integer) (1));

        bst = new BinarySearchTree<>();
        for (int i = 0; i < 9; i++) {
            bst.add(i);
        }

        for (int i = 5; i < 9; i++) {
            assert bst.contains(i);
            System.out.println("KEK");
            bst.print();
            bst.remove(i);
            bst.print();
            assert !bst.contains(i);
        }


    }

    @Test
    public void containsAll() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            bst.add(i);
            vector.add(i);
        }

        assert bst.containsAll(vector);

        vector.clear();

        for (int i = 0; i < 29; i++) {
            vector.add(i);
        }

        assert bst.containsAll(vector);

        vector.add(-1);

        assert !bst.containsAll(vector);


    }

    @Test
    public void addAll() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            vector.add(i);
        }

        bst.addAll(vector);
        assert bst.containsAll(vector);

        for (int i = 0; i < 29; i++) {
            vector.add(i + 10);
        }

        bst.addAll(vector);
        assert bst.containsAll(vector);

        for (int i = 0; i < 39; i++) {
            assert bst.contains(i);
        }
    }

    @Test
    public void retainAll() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>(29);
        for (int i = 0; i < 29; i++) {
            vector.add(i);
        }
        for (int i = 0; i < 35; i++) {
            bst.add(i);
        }

        bst.retainAll(vector);

        bst.print();

        for (int i = 29; i < 35; i++) {
            assertFalse(bst.contains(i));
        }
        for (int i = 0; i < 29; i++) {
            assert bst.contains(i);
        }
    }


    @Test
    public void removeAll() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        ArrayList<Integer> vector = new ArrayList<>(29);

        for (int i = 0; i < 29; i++) {
            vector.add(i);
            System.out.println(vector.get(i));
        }


        for (int i = 0; i < 35; i++) {
            bst.add(i);
        }

        assert bst.contains(29);

        bst.removeAll(vector);

        bst.print();

        for (int i = 29; i < 35; i++) {
            assert bst.contains(i);
        }

        for (int i = 0; i < 29; i++) {
            assert !bst.contains(i);
        }
    }

    @Test
    public void clear() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 10; i++) {
            bst.add(i);
        }

        bst.clear();

        assert bst.isEmpty();

        for (int i = -5; i < -3; i++) {
            bst.add(i);
        }

        bst.clear();

        assert bst.isEmpty();
    }

}