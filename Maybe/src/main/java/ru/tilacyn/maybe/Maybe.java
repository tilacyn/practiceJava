package ru.tilacyn.maybe;

import com.sun.istack.internal.NotNull;

import java.io.*;

import java.lang.Exception;

import java.util.ArrayList;
import java.util.function.*;

/**
 * this class may contain a value with type T
 *
 * @param <T>
 */
public class Maybe<T> {

    public static class EmptyMaybeException extends java.lang.Exception {
    }

    private T t;

    private Maybe(T t) {
        this.t = t;
    }

    /**
     * creates Maybe<T> object with a value given as an argument
     *
     * @param t   - value
     * @param <T>
     * @return - new object
     */
    public static <T> Maybe<T> just(@NotNull T t) {
        return new Maybe<>(t);
    }

    /**
     * creates a new object with no value (value is null)
     *
     * @param <T>
     * @return a new object
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * returns value or throws an exception if there is no value stored
     *
     * @return value
     * @throws Exception if there this object does not contain anything
     */
    public T get() throws EmptyMaybeException {
        if (t != null) {
            return t;
        }
        throw new EmptyMaybeException();
    }

    /**
     * @return whether there is a value stored
     */
    public boolean isPresent() {
        return t != null;
    }

    /**
     * creates a new object with a value that a function mapper returns
     *
     * @param mapper - a functions which takes our current value as an argument
     * @param <U>    return value type
     * @return a new Maybe object with mapper(t) result
     */
    public <U> Maybe<U> map(@NotNull Function<? super T, ? extends U> mapper) {
        if (!isPresent()) {
            return nothing();
        }
        return new Maybe<U>((U) mapper.apply(t));
    }

    /**
     * reads information from input stream and tries to parse integers
     * if it successfully parses an integer from a line then it puts a square of this integer
     * in an output stream
     *
     * @param is - input stream
     * @param os - output stream
     * @throws Exception if problems with resources occurred
     */
    public static void read(@NotNull FileInputStream is, @NotNull FileOutputStream os) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            ArrayList<Maybe<Integer>> array = new ArrayList<Maybe<Integer>>();
            String s;
            while ((s = reader.readLine()) != null) {
                try {
                    int n = Integer.parseInt(s);
                    System.out.println(n);
                    array.add(new Maybe<Integer>(n).map(x -> x * x));
                } catch (Exception e) {
                    array.add(Maybe.nothing());
                }
            }

            for (Maybe maybe : array) {
                try {
                    writer.write(maybe.get().toString());
                    System.out.println(maybe.get().toString());
                    writer.newLine();
                } catch (Exception e) {
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
