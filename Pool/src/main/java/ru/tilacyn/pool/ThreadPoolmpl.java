package ru.tilacyn.pool;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.ArrayList;

/**
 * A simple Pool of tasks
 * It has the only constructor with parameter - number of threads
 * that will work simultaneously
 * Each thread is whether working on a task or waiting for it
 * Each task accepted is stored as an instance of a class that implements LightFuture interface
 * method get returns a result of a task(return value of supplier)
 *
 * @param <T> task return type
 */
public class ThreadPoolmpl<T> {

    private LinkedList<Task> queue = new LinkedList<>();

    private ArrayList<TaskExecutor> threads;

    /**
     * creates a new instance of ThreadPoolmpl
     *
     * @param n - number of threads that may work simultaneously
     */
    public ThreadPoolmpl(int n) {
        threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            TaskExecutor executor = new TaskExecutor();
            executor.start();
            threads.add(executor);
        }
    }


    /**
     * adds a new Task to the thread pool queue
     * @param supplier an object of type Supplier<T> that specifies the way of task evaluation
     * @return this task shortened to the LightFuture interface
     */
    public synchronized LightFuture<T> addTask(@NotNull Supplier<T> supplier) {
        Task task = new Task(supplier);
        queue.push(task);
        notify();
        System.out.println("TASK ADDED");
        return task;
    }

    /**
     * interrupts all the threads
     */
    public void shutdown() {
        for (TaskExecutor t : threads) {
            t.interrupt();
        }
    }

    /**
     * a class for tasks that implements LightFuture
     */
    private class Task implements LightFuture<T> {
        private Supplier<T> supplier;
        private T result = null;
        private boolean exceptionOccurred = false;
        private boolean over = false;


        private Task(@NotNull Supplier<T> supplier) {
            this.supplier = supplier;
        }

        private synchronized void execute() {
            try {
                result = supplier.get();
            } catch (Exception e) {
                System.out.println("EXCEPTION CAUGHT");
                exceptionOccurred = true;
                e.printStackTrace();
            }
            over = true;
            notifyAll();
        }

        @Override
        public boolean isReady() {
            return over;
        }

        @Override
        public synchronized T get() throws LightExecutionException {
            System.out.println("HELLO");
            try {
                while (!over) {
                    System.out.println("NOT OVER");
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (exceptionOccurred) {
                throw new LightExecutionException();
            }

            return result;
        }

        @Override
        public synchronized LightFuture<T> thenApply(@NotNull Function<T, T> f) {
            return addTask(() -> {
                T functionResult = null;
                try {
                    while (!isReady()) {
                        System.out.println("NOT READY");
                        wait();
                    }
                    functionResult = f.apply(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return functionResult;
            });
        }
    }

    private class TaskExecutor extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (ThreadPoolmpl.this) {
                        while (queue.isEmpty()) {
                            ThreadPoolmpl.this.wait();
                        }
                        Task currentTask = queue.pop();
                        currentTask.execute();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

