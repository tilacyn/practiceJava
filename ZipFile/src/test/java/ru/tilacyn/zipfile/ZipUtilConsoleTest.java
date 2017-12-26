package ru.tilacyn.zipfile;

import java.io.*;
import java.util.regex.*;


import org.junit.Test;

import static org.junit.Assert.*;

public class ZipUtilConsoleTest {
    private char sep = File.separatorChar;


    private static boolean checkWithRegex(String regex, String s) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    private void checkDirectory(File file, String regex) {
        for (File f : file.listFiles()) {
            System.out.println(f.getName());
            if (f.isFile()) {
                assertTrue(checkWithRegex(regex, f.getName()));
            } else {
                checkDirectory(f, regex);
            }
        }
    }

    private void deleteDir(File dir) {
        for (File f : dir.listFiles()) {
            System.out.println(f.getName());
            if (f.isFile()) {
                f.delete();
            } else {
                deleteDir(f);
            }
        }
        dir.delete();
    }

    @Test
    public void main() throws Exception {
        new File("Extracted").mkdirs();

        String[] arr = new String[2];
        arr[0] = "Zipped";
        arr[1] = ".*\\.txt";

        ZipUtilConsole.main(arr);

        checkDirectory(new File("Extracted"), ".*\\.txt");

        for (Integer i = 1; i <= 4; i++) {
            assertTrue(new File("Extracted" + sep + "TestZip" + sep + i.toString() + sep + i.toString() + ".txt").exists());
        }

        deleteDir(new File("Extracted"));
    }

    @Test
    public void mainEmptyArchive() throws Exception {
        new File("Extracted").mkdirs();

        String[] arr = new String[2];
        arr[0] = "ZippedEmpty";
        arr[1] = ".*";

        ZipUtilConsole.main(arr);

        assertEquals(new File("Extracted").listFiles().length, 0);

        deleteDir(new File("Extracted"));
    }

}