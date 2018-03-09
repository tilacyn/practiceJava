package ru.tilacyn.lazy;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LazyFactoryTest {

    @Test
    public void simpleTest1() {
        Lazy<String> lazy = LazyFactory.createSimpleLazy(() -> "");
        assertEquals(lazy.get(), "");

        Lazy<Integer> lazy1 = LazyFactory.createSimpleLazy(() -> 33);
        assertEquals((int) lazy1.get(), 33);

        Lazy<ArrayList<Integer>> lazy2 = LazyFactory.createSimpleLazy(ArrayList::new);
        assertEquals(lazy2.get(), new ArrayList<>());
    }

    @Test
    public void simpleTest2() {
        for (int i = 0; i < 100; i++) {
            final int k = i;
            Lazy<Integer> lazy = LazyFactory.createSimpleLazy(() -> k * k);
            assertEquals((int) lazy.get(), i * i);
        }
    }

    @Test
    public void returnNull() {
        Lazy<String> lazy = LazyFactory.createSimpleLazy(() -> null);
        assertEquals(lazy.get(), null);
        Lazy<IllegalStateException> lazyEx = LazyFactory.createSimpleLazy(() -> null);
        assertEquals(lazyEx.get(), null);
    }


    @Test
    public void isGetAppliedOnce() {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(multiplier2);
        assertEquals((int) lazy.get(), 2);
        assertEquals((int) lazy.get(), 2);
        assertEquals((int) lazy.get(), 2);
        assertEquals((int) lazy.get(), 2);
        assertEquals((int) lazy.get(), 2);
    }


    @Test
    public void threadTest() throws InterruptedException {
        Lazy<Integer> multiplier = LazyFactory.createComplexLazy(multiplier2);

        Thread a = new Thread(() -> multiplier.get());
        Thread b = new Thread(() -> multiplier.get());

        a.start();
        b.start();
        a.join();
        b.join();

        assertEquals(2, (int) multiplier.get());
    }


    private Supplier<Integer> multiplier2 = new Supplier<Integer>() {
        Integer n = 1;

        @Override
        public Integer get() {
            n *= 2;
            return n;
        }
    };

}