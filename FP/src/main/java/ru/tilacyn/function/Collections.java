package ru.tilacyn.function;

import com.sun.istack.internal.NotNull;

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
     * @return new AbstractList<T> of new elements that are results of f application to vector elements
     */
    public <U, T> AbstractList<T> map(@NotNull final Function1<? super U, ? extends T> f,
                                      @NotNull final Iterable<? extends U> vector) {
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
     * @return new AbstractList<U> of vector elements that satisfy predicate
     */
    public <U> AbstractList<U> filter(@NotNull final Predicate<? super U> f,
                                      @NotNull final Iterable<? extends U> vector) {
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
     * @return new AbstractList<U> of the first vector elements that satisfy the predicate
     */
    public <U> AbstractList<U> takeWhile(@NotNull final Predicate<? super U> f,
                                         @NotNull final Iterable<? extends U> vector) {
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
     * @return new AbstractList<U> of the first elements that do not satisfy predicate
     */
    public <U> AbstractList<U> takeUnless(@NotNull final Predicate<? super U> f,
                                          @NotNull final Iterable<? extends U> vector) {
        return takeWhile(f.not(), vector);
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
    public <U, T> T foldl(@NotNull final Function2<? super U, ? super T, ? extends T> f,
                          T start,
                          @NotNull final Collection<? extends U> vector) {
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
     * @param collection that is going to be folded
     * @param <U>        collection elements type
     * @param <T>        f return value type
     * @return last application result
     */
    public <U, T> T foldr(@NotNull final Function2<? super U, ? super T, ? extends T> f,
                          T start,
                          @NotNull final Collection<? extends U> collection) {
        ArrayList<U> vector = new ArrayList<U>();
        vector.addAll(collection);
        for (int i = 0; i < vector.size(); i++) {
            start = f.apply(vector.get(vector.size() - i - 1), start);
        }
        return start;
    }

}
