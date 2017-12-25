package ru.tilacyn.Tree;

import org.junit.Test;
import ru.tilacyn.MyTreeSet.MyTreeSet;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

class Pair<K, V> {
    private K key;
    private V value;

    Pair(K k, V v) {
        key = k;
        value = v;
    }

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        return obj != null &&
                this.getClass().equals(obj.getClass()) &&
                key.equals(((Pair<K, V>) obj).getKey()) &&
                value.equals(((Pair<K, V>) obj).getValue());
    }
}

public class TreeSetTest {

    @Test
    public void descendingIterator() throws Exception {
        TreeSet<Double> bst = new TreeSet<>();
        bst.add(3.4);
        bst.add(4.3);
        bst.add(2.1);
        bst.add(-5.4);

        Iterator<Double> it = bst.descendingIterator();

        assertTrue(it.hasNext());
        assertEquals(4.3, it.next(), 0.1);

        assertTrue(it.hasNext());
        assertEquals(3.4, it.next(), 0.1);

        assertTrue(it.hasNext());
        assertEquals(2.1, it.next(), 0.1);

        assertTrue(it.hasNext());
        assertEquals(-5.4, it.next(), 0.1);


        assertTrue(!it.hasNext());
    }

    @Test
    public void descendingSet() throws Exception {
        TreeSet<Double> bst = new TreeSet<>();
        bst.add(0.1);
        bst.add(0.3);
        bst.add(0.2);
        bst.add(0.4);
        bst.add(0.5);

        MyTreeSet<Double> descendingBst = bst.descendingSet();

        descendingBst.add(1.0);

        assertTrue(descendingBst.contains(1.0));
        assertTrue(descendingBst.contains(0.1));
        assertTrue(descendingBst.contains(0.2));
        assertTrue(descendingBst.contains(0.3));
        assertTrue(descendingBst.contains(0.4));
        assertTrue(descendingBst.contains(0.5));

        Double prev = 2.0;

        for (Object o : descendingBst) {
            assertTrue((Double) o < prev);
            prev = (Double) o;
        }


    }

    @Test
    public void first() throws Exception {
        TreeSet<Double> bst = new TreeSet<>();
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
        TreeSet<Double> bst = new TreeSet<>();
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
        TreeSet<Pair<Integer, Integer>> bst = new TreeSet<>((o1, o2) -> {
            if (o1.getKey().equals(o2.getKey())) {
                return o1.getValue().compareTo(o2.getValue());
            }
            return o1.getKey().compareTo(o2.getKey());
        });
        bst.add(new Pair<>(3, 4));
        bst.add(new Pair<>(5, 2));
        bst.add(new Pair<>(1, 5));
        bst.add(new Pair<>(6, 4));
        bst.add(new Pair<>(6, 2));

        assertEquals(new Pair<>(5, 2), new Pair<>(5, 2));
        assertEquals(new Pair<>(5, 2), bst.lower(new Pair<>(6, 2)));

        System.out.println(bst.lower(new Pair<>(6, 3)).getKey() + " " + bst.lower(new Pair<>(6, 3)).getValue());

        assertEquals(new Pair<>(6, 2), bst.lower(new Pair<>(6, 3)));
        assertEquals(new Pair<>(1, 5), bst.lower(new Pair<>(3, 0)));
        assertEquals(new Pair<>(6, 2), bst.lower(new Pair<>(6, 4)));
        assertEquals(null, bst.lower(new Pair<>(0, 0)));
        assertEquals(null, bst.lower(new Pair<>(1, 5)));
    }

    @Test
    public void floor() throws Exception {
        TreeSet<Pair<Integer, Integer>> bst = new TreeSet<>((o1, o2) -> {
            if (o1.getKey().equals(o2.getKey())) {
                return o1.getValue().compareTo(o2.getValue());
            }
            return o1.getKey().compareTo(o2.getKey());
        });
        bst.add(new Pair<>(-1, -1));
        bst.add(new Pair<>(3, 3));
        bst.add(new Pair<>(2, 2));
        bst.add(new Pair<>(0, 0));
        bst.add(new Pair<>(1, 1));
        bst.add(new Pair<>(1, 2));
        bst.add(new Pair<Integer, Integer>(1, 3));
        bst.add(new Pair<>(1, 0));
        bst.add(new Pair<>(0, -1));

        assertEquals(new Pair<>(3, 3), bst.floor(new Pair<>(5, 2)));
        assertEquals(new Pair<>(-1, -1), bst.floor(new Pair<>(-1, -1)));
        assertEquals(null, bst.floor(new Pair<>(-2, 0)));
        assertEquals(new Pair<>(1, 3), bst.floor(new Pair<>(1, 10)));

    }

    @Test
    public void ceiling() throws Exception {

        TreeSet<Integer> bst = new TreeSet<>();
        bst.add(1);
        bst.print();
        bst.add(4);
        bst.add(-3);
        bst.add(9);
        bst.add(2);
        bst.print();
        bst.add(6);
        bst.add(0);

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
        TreeSet<Integer> bst = new TreeSet<>();
        assertTrue(bst.add(1));
        bst.print();
        assertFalse(bst.add(1));
        assertTrue(bst.add(4));
        assertTrue(bst.add(-3));
        assertTrue(bst.add(9));
        assertTrue(bst.add(2));
        bst.print();
        assertTrue(!bst.add(2));
        assertTrue(bst.add(0));
        assertTrue(bst.add(6));
        assertTrue(!bst.add(0));

        assertEquals(bst.higher(-1), (Integer) 0);
        assertEquals(bst.higher(-4), (Integer) (-3));
        assertEquals(bst.higher(8), (Integer) 9);
        assertEquals(bst.higher(7), (Integer) 9);
        assertEquals(bst.higher(6), (Integer) 9);
        assertEquals(bst.higher(5), (Integer) 6);
        assertEquals(bst.higher(2), (Integer) 4);
        assertEquals(bst.higher(1), (Integer) 2);

        bst = new TreeSet<>();
        for (int i = 0; i < 35; i++) {
            bst.add(i);
        }
        for (int i = 0; i < 34; i++) {
            assertEquals(i + 1, (int) bst.higher(i));
        }

    }

    @Test
    public void size() throws Exception {
        TreeSet<String> bst = new TreeSet<>((o1, o2) -> ((Integer) o1.hashCode()).compareTo(o2.hashCode()));
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
        TreeSet<String> bst = new TreeSet<>((o1, o2) -> ((Integer) o1.hashCode()).compareTo(o2.hashCode()));
        bst.add("Hello");
        assertTrue(!bst.isEmpty());
        bst.add("I love you");
        bst.remove("I love you");
        assertTrue(!bst.isEmpty());
        bst.remove("Hello");
        assertTrue(bst.isEmpty());
    }

    @Test
    public void contains() throws Exception {
        TreeSet<String> bst = new TreeSet<>((o1, o2) -> ((Integer) o1.hashCode()).compareTo(o2.hashCode()));
        TreeSet<Integer> bstWeird = new TreeSet<>((o1, o2) -> {
            Integer a = o1 * o1;
            int b = o2 * o2;
            return a.compareTo(b);
        });

        bst.add("Hello");
        assertTrue(bst.add("I love you"));
        bst.add("Can you");
        bst.add("Tell me");
        bst.add("Your name");

        bst.print();

        assertTrue(bst.contains("Hello"));
        assertTrue(!bst.contains("Hello I love you"));
        assertTrue(bst.contains("I love you"));
        assertTrue(bst.contains("Can you"));
        assertTrue(bst.contains("Tell me"));
        assertTrue(bst.contains("Your name"));

        bstWeird.add(3);
        bstWeird.add(4);
        assertTrue(!bstWeird.add(-4));


    }


    @Test
    public void iterator() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        bst.add(0);
        bst.add(20);
        bst.add(10);
        bst.add(3);
        bst.add(-5);
        bst.add(-1);

        Iterator<Integer> it = bst.iterator();
        assertTrue(it.hasNext());
        assertEquals(-5, (int) it.next());
        assertEquals(-1, (int) it.next());
        assertEquals(0, (int) it.next());
        assertEquals(3, (int) it.next());
        assertEquals(10, (int) it.next());
        assertEquals(20, (int) it.next());
        assertTrue(!it.hasNext());

        for (Integer e : bst) {
            System.out.println(e);
        }

    }

    @Test
    public void toArray() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            bst.add(i);
            vector.add(i);
        }
        assertArrayEquals(vector.toArray(), bst.toArray());

    }

    @Test
    public void toArray1() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
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
        TreeSet<Integer> bst = new TreeSet<>();
        assertTrue(bst.add(1));
        bst.print();
        assertFalse(bst.add(1));
        assertTrue(bst.add(4));
        assertTrue(bst.add(-3));
        assertTrue(bst.add(9));
        assertTrue(bst.add(2));
        bst.print();
        assertTrue(!bst.add(2));
        assertTrue(bst.add(0));
        assertTrue(bst.add(6));
        assertTrue(!bst.add(0));

        bst.print();
    }

    @Test
    public void remove() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        assertFalse(bst.remove(4));
        bst.add(1);
        bst.add(4);
        bst.add(-3);
        bst.add(9);
        bst.print();
        bst.remove(-3);
        assertTrue(!bst.contains(-3));
        bst.add(-3);
        bst.add(2);
        bst.remove(2);

        bst.print();

        assertTrue(!bst.contains(2));
        bst.add(6);
        bst.add(0);
        bst.remove(1);
        assertTrue(!bst.remove(1));
        assertTrue(!bst.contains(1));

        bst = new TreeSet<>();
        for (int i = 0; i < 9; i++) {
            bst.add(i);
        }

        for (int i = 5; i < 9; i++) {
            assertTrue(bst.contains(i));
            System.out.println("KEK");
            bst.print();
            bst.remove(i);
            bst.print();
            assertTrue(!bst.contains(i));
        }
    }

    @Test
    public void containsAll() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            bst.add(i);
            vector.add(i);
        }

        assertTrue(bst.containsAll(vector));

        vector.clear();

        for (int i = 0; i < 29; i++) {
            vector.add(i);
        }

        assertTrue(bst.containsAll(vector));

        vector.add(-1);

        assertTrue(!bst.containsAll(vector));
    }

    @Test
    public void addAll() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        ArrayList<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            vector.add(i);
        }

        bst.addAll(vector);
        assertTrue(bst.containsAll(vector));

        for (int i = 0; i < 29; i++) {
            vector.add(i + 10);
        }

        bst.addAll(vector);
        assertTrue(bst.containsAll(vector));

        for (int i = 0; i < 39; i++) {
            assertTrue(bst.contains(i));
        }
    }

    @Test
    public void retainAll() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
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
            assertTrue(bst.contains(i));
        }
    }


    @Test
    public void removeAll() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        ArrayList<Integer> vector = new ArrayList<>(29);

        for (int i = 0; i < 29; i++) {
            vector.add(i);
            System.out.println(vector.get(i));
        }


        for (int i = 0; i < 35; i++) {
            bst.add(i);
        }

        assertTrue(bst.contains(29));

        bst.removeAll(vector);

        bst.print();

        for (int i = 29; i < 35; i++) {
            assertTrue(bst.contains(i));
        }

        for (int i = 0; i < 29; i++) {
            assertTrue(!bst.contains(i));
        }
    }

    @Test
    public void clear() throws Exception {
        TreeSet<Integer> bst = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            bst.add(i);
        }

        bst.clear();

        assertTrue(bst.isEmpty());

        for (int i = -5; i < -3; i++) {
            bst.add(i);
        }

        bst.clear();

        assertTrue(bst.isEmpty());
    }

}