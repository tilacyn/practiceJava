package ru.tilacyn.md5;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Bad input, try again!");
            return;
        }

        String dir = args[0];

        SingleThreadMD5 singleThreadMD5 = new SingleThreadMD5(dir);
        ForkJoinMD5 forkJoinMD5 = new ForkJoinMD5(dir);



        try {
            long start = System.currentTimeMillis();

            singleThreadMD5.calculate();

            long end = System.currentTimeMillis();

            System.out.println("SingleThread version works at: " + (end - start) + " ms");

        } catch (IOException e) {
            System.out.println("File reading failed");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            long start = System.currentTimeMillis();

            forkJoinMD5.calculate();

            long end = System.currentTimeMillis();

            System.out.println("ForkJoin version works at: " + (end - start) + " ms");

        } catch (IOException e) {
            System.out.println("File reading failed");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
