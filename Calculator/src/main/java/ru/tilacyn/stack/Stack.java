package ru.tilacyn.stack;

import org.jetbrains.annotations.Nullable;

/**
 * realisation of a simple generic stack based on array
 *
 * @param <T> type of elements in stack
 */


public class Stack<T> {
    /**
     * Exception for cases of pop applied to an empty stack
     */
    public static class EmptyStackException extends Exception {
    }

    /**
     * size of the stack
     */
    private int size = 0;

    /**
     * storage of type T[]
     */
    private T[] array;

    /**
     * public constructor for stack
     *
     * @param n size of the stack
     */
    public Stack(int n) {
        array = (T[]) new Object[n];
    }

    /**
     * pushes elem in stack
     *
     * @param elem of type T we push in stack
     */
    public void push(@Nullable T elem) {
        if (size == array.length) {
            T[] newArray = (T[]) new Object[2 * size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = elem;
        size++;
    }

    /**
     * removes the top of the stack
     *
     * @return the top of the stack or null if stack is empty
     * @throws Stack.EmptyStackException if pop is applied to an empty Stack
     */
    public T pop() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        }
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
