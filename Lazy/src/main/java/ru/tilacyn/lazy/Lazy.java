package ru.tilacyn.lazy;


/**
 * This interface is used for lazy evaluating
 * You should implement only one method - get with return type - T
 * <p>
 * Lazy objects are initialized with Supplier objects and get returns a result of Supplier::get
 *
 * @param <T> Lazy::get return type
 */
public interface Lazy<T> {
    /**
     * result is evaluated once so that applying get many times will give you the same answer
     * also it may return null(not forbidden)
     *
     * @return a result of type T
     */
    T get();
}
