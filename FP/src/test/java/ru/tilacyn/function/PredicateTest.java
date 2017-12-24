package ru.tilacyn.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.tilacyn.function.Predicate.ALWAYS_FALSE;
import static ru.tilacyn.function.Predicate.ALWAYS_TRUE;

public class PredicateTest {
    @Test
    public void or() throws Exception {
        Predicate<Integer> is2 = x -> x == 2;
        Predicate<Integer> is3 = x -> x == 3;
        assertTrue(is3.or(is2).apply(2));
        assertTrue(is3.or(is2).apply(3));
        assertTrue(is2.or(is3).apply(2));
        assertTrue(is2.or(is3).apply(3));
        assertFalse(is2.or(is3).apply(0));
        assertFalse(is2.or(is3).apply(5));
        Predicate<String> isShorter5 = s -> s.length() < 5;
        Predicate<String> isLonger7 = s -> s.length() > 7;
        assertTrue(isShorter5.or(isLonger7).apply("a"));
        assertTrue(isShorter5.or(isLonger7).apply("asssssss"));
        assertFalse(isShorter5.or(isLonger7).apply("assssss"));
    }

    @Test
    public void and() throws Exception {
        Predicate<Integer> greater2 = x -> x > 2;
        Predicate<Integer> less5 = x -> x < 5;
        assertTrue(greater2.and(less5).apply(3));
        assertTrue(greater2.and(less5).apply(4));
        assertFalse(greater2.and(less5).apply(2));
        assertFalse(greater2.and(less5).apply(5));
        assertFalse(greater2.and(less5).apply(6));

        Predicate<String> isLonger5 = s -> s.length() > 5;
        Predicate<String> isShorter10 = s -> s.length() < 10;
        assertTrue(isLonger5.and(isShorter10).apply("aaaaaa"));
        assertTrue(isShorter10.and(isLonger5).apply("asssssss"));
        assertFalse(isLonger5.and(isShorter10).apply("ass"));
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> greater2 = x -> x > 2;

        assertFalse(greater2.not().apply(3));
        assertTrue(greater2.not().apply(1));

        Predicate<String> isLonger5 = s -> s.length() > 5;

        assertTrue(isLonger5.not().apply("aaaa"));
        assertTrue(isLonger5.not().apply("aaaaa"));
        assertFalse(isLonger5.not().apply("aaaaaa"));
    }

    @Test
    public void alwaysTrue() {
        assertTrue(ALWAYS_TRUE.apply(""));
        assertTrue(ALWAYS_TRUE.apply(1));
        assertTrue(ALWAYS_TRUE.and(ALWAYS_TRUE).apply(1));
        assertTrue(ALWAYS_TRUE.or(ALWAYS_FALSE).apply(1));
        assertFalse(ALWAYS_TRUE.not().apply(0.5));
    }

    @Test
    public void alwaysFalse() {
        assertFalse(ALWAYS_FALSE.apply(""));
        assertFalse(ALWAYS_FALSE.apply(1));
        assertFalse(ALWAYS_FALSE.and(ALWAYS_TRUE).apply(1));
        assertTrue(ALWAYS_FALSE.or(ALWAYS_TRUE).apply(1));
        assertTrue(ALWAYS_FALSE.not().apply(0.5));
    }
}