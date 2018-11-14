package ru.tilacyn.md5;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleThreadMD5Test {
    @Test
    public void compare() throws Exception {
        ForkJoinMD5 forkJoinMD5 = new ForkJoinMD5(".");
        SingleThreadMD5 singleThreadMD5 = new SingleThreadMD5(".");

        assertEquals(singleThreadMD5.calculate(), forkJoinMD5.calculate());
    }

}