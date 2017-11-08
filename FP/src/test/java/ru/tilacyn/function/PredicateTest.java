package ru.tilacyn.function;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    @Test
    public void or() throws Exception {
        Predicate<Integer> is2 = x -> x == 2;
        Predicate<Integer> is3 = x -> x == 3;
        assert is3.or(is2).apply(2);
        assert is3.or(is2).apply(3);
        assert is2.or(is3).apply(2);
        assert is2.or(is3).apply(3);
        assert !is2.or(is3).apply(0);
        assert !is2.or(is3).apply(5);
        Predicate<String> isShorter5 = s -> s.length() < 5;
        Predicate<String> isLonger7 = s -> s.length() > 7;
        assert isShorter5.or(isLonger7).apply("a");
        assert isShorter5.or(isLonger7).apply("asssssss");
        assert !isShorter5.or(isLonger7).apply("assssss");
    }

    @Test
    public void and() throws Exception {
        Predicate<Integer> greater2 = x -> x > 2;
        Predicate<Integer> less5 = x -> x < 5;
        assert greater2.and(less5).apply(3);
        assert greater2.and(less5).apply(4);
        assert !greater2.and(less5).apply(2);
        assert !greater2.and(less5).apply(5);
        assert !greater2.and(less5).apply(6);

        Predicate<String> isLonger5 = s -> s.length() > 5;
        Predicate<String> isShorter10 = s -> s.length() < 10;
        assert isLonger5.and(isShorter10).apply("aaaaaa");
        assert isShorter10.and(isLonger5).apply("asssssss");
        assert !isLonger5.and(isShorter10).apply("ass");
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> greater2 = x -> x > 2;

        assert !greater2.not().apply(3);
        assert greater2.not().apply(1);

        Predicate<String> isLonger5 = s -> s.length() > 5;

        assert isLonger5.not().apply("aaaa");
        assert isLonger5.not().apply("aaaaa");
        assert !isLonger5.not().apply("aaaaaa");
    }

}