package ru.tilacyn.function;

import com.sun.istack.internal.NotNull;

/**
 * an interface extends Function1 interface, describes one-argument predicates
 *
 * @param <T> - argument type
 */
public interface Predicate<T> extends Function1<T, Boolean> {

    /**
     * creates an "or" predicate from this and other, which application is constructed like this:
     * result(x) = this(x) || other(x)
     *
     * @param other Predicate
     * @return new result Predicate
     */
    default Predicate<T> or(@NotNull Predicate<? super T> other) {
        return x -> apply(x) || other.apply(x);
    }

    /**
     * creates an "and" predicate from this and other, which application is constructed like this:
     * result(x) = this(x) && other(x)
     *
     * @param other Predicate
     * @return new result Predicate
     */
    default Predicate<T> and(@NotNull Predicate<? super T> other) {
        return x -> apply(x) && other.apply(x);
    }

    /**
     * creates a new "not" predicate from this one
     *
     * @return new result predicate
     */
    default Predicate<T> not() {
        return x -> !apply(x);
    }

    /**
     * static constants
     */
    Predicate<?> ALWAYS_TRUE = x -> true;
    Predicate<?> ALWAYS_FALSE = x -> false;
}


