package ru.tilacyn.pool;

import java.util.function.Function;

/**
 * an interface for tasks in thread pool
 * each instance is connected with a supplier: () -> T
 * <p>
 * each task has methods
 * isReady - is evaluating over or not
 * get - result of evaluation
 * thenApply - create a new Task and add it to a Thread pool
 *
 * @param <T> get return type
 */
public interface LightFuture<T> {
    /**
     * checks whether a task result evaluation is over
     *
     * @return true if evaluation is over, false else
     */
    boolean isReady();

    /**
     * returns a result of supplier::get evaluation
     * that is calculated once
     *
     * @return returns a result of evaluation
     * @throws LightExecutionException in case supplier::get throws any exception
     */
    T get() throws LightExecutionException;

    /**
     * creates a new LightFuture, where result will be evaluated this way:
     * f.apply(this.result) and adds it to a thread pool
     * if !this.isReady() then evaluation of a new LightFuture obviously doesn't start yet
     *
     * @param f an object of type Function<T, T>
     * @return a new LightFuture<T> with evaluation instruction: () -> f.apply(this.get());
     * @throws LightExecutionException in case supplier::get throws any exception
     */
    LightFuture<T> thenApply(Function<T, T> f) throws LightExecutionException;

}
