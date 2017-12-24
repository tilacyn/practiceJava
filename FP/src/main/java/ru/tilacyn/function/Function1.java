package ru.tilacyn.function;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.io.*;
import java.lang.String;

/**
 * an interface for one-argument functions
 * with operations: application, composition
 *
 * @param <U> argument type
 * @param <V> return type
 */
public interface Function1<U, V> {

    /**
     * calls function
     *
     * @param x argument
     * @return function result
     */
    public V apply(U x);

    /**
     * a function composition
     * it actually works like g(f(x)), f = this
     *
     * @param g   function g
     * @param <T> return value type of function g
     * @return one-argument function, which is a result of composition
     */
    default <T> Function1<U, T> compose(@NotNull Function1<? super V, ? extends T> g) {
        return x -> g.apply(apply(x));
    }
}
