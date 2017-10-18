package ru.tilacyn.maybe;

import java.io.*;

import java.util.ArrayList;
import java.util.function.*;

/**
 * this class may contain a value with type T
 *
 * @param <T>
 */

public class Maybe<T> {

    private T t;

    Maybe(T t) {
        this.t = t;
    }

    /**
     * creates Maybe<T> object with a value given as an argument
     *
     * @param t   - value
     * @param <T>
     * @return - new object
     */

    public static <T> Maybe<T> just(T t) {
        Maybe<T> maybe = new Maybe<T>(t);
        return maybe;
    }

    /**
     * creates a new object with no value (value is null)
     *
     * @param <T>
     * @return a new object
     */

    public static <T> Maybe<T> nothing() {
        Maybe<T> maybe = new Maybe<T>(null);
        return maybe;
    }

    /**
     * returns value or throws an exception if there is no value stored
     *
     * @return value
     * @throws Exception if there this object does not contain anything
     */

    public T get() throws Exception {
        if (t != null) {
            return t;
        }
        throw new Exception("There's nothing in this Maybe");
    }

    /**
     * @return whether there is a value stored
     */

    public boolean isPresent() {
        if (t == null) {
            return false;
        }
        return true;
    }

    /**
     * creates a new object with a value that a function mapper returns
     *
     * @param mapper - a functions which takes our current value as an argument
     * @param <U>    return value type
     * @return a new Maybe object with mapper(t) result
     */

    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        if (!isPresent()) {
            return new Maybe<U>(null).nothing();
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
     * @throws Exception
     */


    public static void read(FileInputStream is, FileOutputStream os) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        ArrayList<Maybe<Integer>> arr = new ArrayList<Maybe<Integer>>();
        String s;
        while ((s = reader.readLine()) != null) {
            try {
                int n = Integer.parseInt(s);
                arr.add(new Maybe<Integer>(n).map(x -> x * x));
            } catch (Exception e) {
                arr.add(Maybe.nothing());
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            try {
                writer.write(arr.get(i).get().toString());
                writer.newLine();
            } catch (Exception e) {
                writer.newLine();
            }
        }
    }

}
