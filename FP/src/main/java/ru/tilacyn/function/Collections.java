package ru.tilacyn.function;

import java.util.*;

/**
 * A class with some methods on iterable structures
 */

public class Collections {

    /**
     * works like usual map - creates a new list of function results by applying on
     * the iterable structure elements
     *
     * @param f      one-argument function
     * @param vector iterable structure
     * @param <U>    source structure element type
     * @param <T>    result structure element type
     * @return new ArrayList<T>
     */


    public <U, T> ArrayList<T> map(Function1<U, T> f, Iterable<U> vector) {
        ArrayList<T> resVector = new ArrayList<T>();
        for (U element : vector) {
            resVector.add(f.apply(element));
        }
        return resVector;
    }

    /**
     * filters elements in our structure: if predicate application returns true - then element is left
     * otherwise - no
     *
     * @param f      one-argument predicate
     * @param vector iterable structure
     * @param <U>    source structure element type
     * @return new ArrayList<U>
     */

    public <U> ArrayList<U> filter(Predicate<U> f, Iterable<U> vector) {
        ArrayList<U> resVector = new ArrayList<U>();
        for (U element : vector) {
            if (f.apply(element)) {
                resVector.add(element);
            }
        }
        return resVector;
    }


    /**
     * takes elements while(predicate(elements) == true), then breaks
     *
     * @param f      one-argument predicate
     * @param vector iterable structure
     * @param <U>    source structure element type
     * @return new ArrayList<U>
     */

    public <U> ArrayList<U> takeWhile(Predicate<U> f, Iterable<U> vector) {
        ArrayList<U> resVector = new ArrayList<U>();
        for (U element : vector) {
            if (f.apply(element)) {
                resVector.add(element);
            } else {
                break;
            }
        }
        return resVector;
    }


    /**
     * takes elements while(predicate(elements) == false), then breaks
     *
     * @param f      one-argument predicate
     * @param vector iterable structure
     * @param <U>    source structure element type
     * @return new ArrayList<U>
     */

    public <U> ArrayList<U> takeUnless(Predicate<U> f, Iterable<U> vector) {
        ArrayList<U> resVector = new ArrayList<U>();
        for (U element : vector) {
            if (!f.apply(element)) {
                resVector.add(element);
            } else {
                break;
            }
        }
        return resVector;
    }

    /**
     * works like a usual foldl (from the left edge)
     *
     * @param f      two-arguments function
     * @param start  start value
     * @param vector collection
     * @param <U>    collection elements type
     * @param <T>    f return value type
     * @return last application result
     */

    public <U, T> T foldl(Function2<U, T, T> f, T start, Collection<U> vector) {
        ArrayList<U> resVector = new ArrayList<U>();
        for (U element : vector) {
            start = f.apply(element, start);
        }
        return start;
    }

    /**
     * works like a usual foldr (from the right edge)
     *
     * @param f          two-arguments function
     * @param start      start value
     * @param collection
     * @param <U>        collection elements type
     * @param <T>        f return value type
     * @return last application result
     */

    public <U, T> T foldr(Function2<U, T, T> f, T start, Collection<U> collection) {
        ArrayList<U> vector = new ArrayList<U>();
        vector.addAll(collection);
        for (int i = 0; i < vector.size(); i++) {
            start = f.apply(vector.get(vector.size() - i - 1), start);
        }
        return start;
    }

}
