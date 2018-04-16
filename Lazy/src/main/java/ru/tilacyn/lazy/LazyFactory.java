package ru.tilacyn.lazy;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

public class LazyFactory<T> {

    /**
     * creates a simple instance of Lazy
     *
     * @param supplier a Supplier<T> object that provides get evaluating
     * @param <T>      Supplier::get return type
     * @return new instance of Lazy initialized by a given supplier
     */
    public static <T> Lazy<T> createSimpleLazy(@NotNull Supplier<T> supplier) {
        return new Lazy<T>() {
            private T result = null;
            private boolean evaluated = false;

            @Override
            public T get() {
                if (!evaluated) {
                    result = supplier.get();
                    evaluated = true;
                }
                return result;
            }
        };
    }


    /**
     * creates a new Lazy<T> Object that affords working with threads
     * because method get is made synchronized
     *
     * @param supplier a Supplier<T> object that is used for evaluating get result
     * @param <T>      supplier::get return type
     * @return a new instance of Lazy with synchronization initialized by a supplier
     */
    public static <T> Lazy<T> createComplexLazy(@NotNull Supplier<T> supplier) {
        return new Lazy<T>() {
            private T result = null;
            private boolean evaluated = false;


            @Override
            public T get() {
                synchronized (this) {
                    if (!evaluated) {
                        result = supplier.get();
                        evaluated = true;
                    }
                }
                return result;
            }
        };
    }
}
