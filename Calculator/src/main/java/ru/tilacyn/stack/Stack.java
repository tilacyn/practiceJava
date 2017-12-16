package ru.tilacyn.stack;

import com.sun.istack.internal.Nullable;

/**
 * realisation of a simple generic stack based on array
 *
 * @param <T> type of elements in stack
 */
public class Stack<T> {

    /**
     * size of the stack
     */
    private int size = 0;

    /**
     * storage of type T[]
     */
    private T[] array;

    /**
     * public constuctor for stack
     *
     * @param n size of the stack
     */
    public Stack(int n) {
        array = (T[]) new Object[n];
    }

    /**
     * pushs elem in stack
     *
     * @param elem of type T we push in stack
     */
    public void push(@Nullable T elem) {
        array[size] = elem;
        size++;
    }

    /**
     * removes the top of the stack
     *
     * @return the top of the stack or null if stack is empty
     */
    public T pop() {
        size--;
        return array[size];
    }

    /**
     * @return the top of the stack or null if stack is empty
     */
    public T top() {
        return array[size - 1];
    }

    /**
     * @return size of stack
     */
    public int size() {
        return size;
    }

    /**
     * @return whether stack isEmpty
     */
    public boolean isEmpty() {
        return size == 0;
    }

}
