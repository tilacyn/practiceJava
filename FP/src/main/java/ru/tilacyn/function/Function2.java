package ru.tilacyn.function;

import org.jetbrains.annotations.NotNull;

/**
 * an interface for two-argument functions
 * with operations application, composition, binding first argument,
 * binding second argument, curry
 *
 * @param <U> first argument type
 * @param <V> second argument type
 * @param <T> return value type
 */
public interface Function2<U, V, T> {
    public T apply(U x, V y);

    /**
     * a two-argument function composition
     * it actually works like g(f(x)), f = this
     *
     * @param g   one-argument function g
     * @param <K> return value type of function g
     * @return two-argument function, which is a result of composition
     */
    default <K> Function2<U, V, K> compose(@NotNull Function1<? super T, ? extends K> g) {
        return (x, y) -> g.apply(apply(x, y));
    }

    /**
     * it binds second argument by y
     *
     * @param y - second argument parameter
     * @return a one-argument function
     */
    default Function1<U, T> bind2(V y) {
        return x -> apply(x, y);
    }

    /**
     * it binds first argument by x
     *
     * @param x - first argument parameter
     * @return
     */
    default Function1<V, T> bind1(U x) {
        return y -> apply(x, y);
    }

    /**
     * it works the same way as bind2
     *
     * @param y
     * @return
     */
    default Function1<U, T> curry(V y) {
        return bind2(y);
    }
}
