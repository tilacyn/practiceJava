package ru.tilacyn.pool;

import org.junit.Test;


import static org.junit.Assert.*;

import java.util.ArrayList;

public class ThreadPoolImplTest {
    @Test
    public void test1() throws LightExecutionException {
        ThreadPoolmpl<Integer> pool = new ThreadPoolmpl<>(5);
        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        assertEquals(task.get(), (Integer) 4);
        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 3);
        LightFuture<Integer> task2 = task1.thenApply((i) -> i + 1);
        LightFuture<Integer> task3 = task1.thenApply((i) -> i + 2);
        assertEquals(task1.get(), (Integer) 6);
        assertEquals(task2.get(), (Integer) 7);
        assertEquals(task3.get(), (Integer) 8);
        pool.shutdown();
    }

    @Test
    public void manyTasks() throws LightExecutionException {
        ThreadPoolmpl<String> pool = new ThreadPoolmpl<>(5);
        LightFuture<String> task1 = pool.addTask(new Integer(234567)::toString);
        LightFuture<String> task2 = pool.addTask(() -> "hello");
        LightFuture<String> task3 = pool.addTask(this::toString);
        pool.addTask(() -> {
            StringBuilder res = new StringBuilder();
            for (Integer i = 0; i < 1000; i++) {
                res.append(i.toString());
            }
            return res.toString();
        });
        LightFuture<String> task5 = pool.addTask(() -> "5");
        LightFuture<String> task6 = pool.addTask(() -> "6");
        LightFuture<String> task7 = pool.addTask(() -> "7");

        assertEquals(task1.get(), "234567");
        assertEquals(task2.get(), "hello");
        assertEquals(task3.get(), this.toString());
        assertEquals(task5.get(), "5");
        assertEquals(task6.get(), "6");
        assertEquals(task7.get(), "7");
        pool.shutdown();
    }

    @Test
    public void thenApplyInLoop() throws LightExecutionException {
        ThreadPoolmpl<Integer> pool = new ThreadPoolmpl<>(10);

        ArrayList<Integer> results = new ArrayList<>();

        LightFuture<Integer> task = pool.addTask(() -> 1);
        for (int i = 0; i < 100; i++) {
            final int k = i;
            task = task.thenApply(x -> x + k);
            results.add(task.get());
        }

        Integer sum = 1;

        for (int i = 0; i < 100; i++) {
            sum += i;
            assertEquals(results.get(i), sum);
        }
        pool.shutdown();
    }

    @Test
    public void thenApplyToOneResult() throws LightExecutionException {
        ThreadPoolmpl<Integer> pool = new ThreadPoolmpl<>(10);
        LightFuture<Integer> task = pool.addTask(() -> 2);

        ArrayList<LightFuture<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final int k = i;
            tasks.add(task.thenApply(x -> x * k));
        }

        for (Integer i = 0; i < 10; i++) {
            assertEquals((int) tasks.get(i).get(), 2 * i);
        }
        pool.shutdown();
    }

    @Test(expected = LightExecutionException.class)
    public void exception() throws LightExecutionException {
        ThreadPoolmpl<Integer> pool = new ThreadPoolmpl<>(1);
        LightFuture<Integer> task = pool.addTask(() -> {
            throw new IllegalStateException();
        });

        task.get();
        pool.shutdown();
    }

}