package ru.tilacyn.zipfile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.regex.*;


import org.junit.Test;

import static org.junit.Assert.*;

public class ZipUtilConsoleTest {
    /*

    private static void writeFile(InputStream is, OutputStream os) throws IOException{
        byte[] buf = new byte[1024];
        int l;
        while((l = is.read(buf)) >= 0) {
            os.write(buf, 0, l);
        }
    }

    private static void zip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                zip(f, out);
            else {
                out.putNextEntry(new ZipEntry(f.getPath()));
                writeFile(new FileInputStream(f), out);
            }
        }
    }

    */

    private static boolean checkWithRegex(String regex, String s){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    @Test
    public void main() {
        /*
        new File("TestZip/1").mkdirs();
        new File("TestZip/2").mkdirs();
        new File("TestZip/3").mkdirs();
        new File("TestZip/4").mkdirs();
        new File("TestZip/5").mkdirs();

        new File("Test").mkdirs();

        try {
            new File("TestZip/4/4.txt").createNewFile();
            new File("TestZip/1/1.txt").createNewFile();
            new File("TestZip/2/2.txt").createNewFile();
            new File("TestZip/3/3.txt").createNewFile();

            zip(new File("TestZip"), new ZipOutputStream(new FileOutputStream("Test/archive.zip")));

        } catch (Exception e){
            e.printStackTrace();
        }
        */
        String[] arr = new String[2];
        arr[0] = "Test";
        arr[1] = ".*\\.txt";

        ZipUtilConsole.main(arr);

        for(File f : new File("Extracted").listFiles()){
                //System.out.println(f.getName());
                assert checkWithRegex(f.getName(), ".*\\.txt");
        }

        assert new File("Extracted/1.txt").exists();
        assert new File("Extracted/2.txt").exists();
        assert new File("Extracted/3.txt").exists();
        assert new File("Extracted/4.txt").exists();
        assert new File("Extracted/5.txt").exists();


    }

}