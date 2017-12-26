package ru.tilacyn.zipfile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.regex.*;

import org.jetbrains.annotations.NotNull;

/**
 * a class for a console util
 * it(main) searches a given directory recursively for zip archives and extracts
 * only those that match some regular expression
 */
public class ZipUtilConsole {

    /**
     * writes data from input stream to output stream
     *
     * @param is input stream
     * @param os output stream
     * @throws IOException
     */
    private static void writeFile(@NotNull InputStream is, @NotNull OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int l;
        while ((l = is.read(buf)) >= 0) {
            os.write(buf, 0, l);
        }
        is.close();
        os.close();
    }

    /**
     * recursively searches for zip archives in the directory
     *
     * @param dir   directory
     * @param regex regular expression
     * @throws IOException
     */
    private static void findZip(@NotNull File dir, @NotNull String regex) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findZip(file, regex);
            } else {
                if (checkWithRegex(".*\\.zip", file.getName())) {
                    unZip(new ZipFile(file), regex);
                }
            }
        }
    }

    /**
     * checks whether a string does match regex
     *
     * @param regex regular expression
     * @param s     string we want to check
     * @return true if matches, false else
     */
    private static boolean checkWithRegex(@NotNull String regex, @NotNull String s) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * extracts each file from the archive which matches regex
     *
     * @param zipFile archive
     * @param regex
     * @throws IOException
     */
    private static void unZip(@NotNull ZipFile zipFile, @NotNull String regex) throws IOException {
        Enumeration entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (!zipEntry.isDirectory() && checkWithRegex(regex, zipEntry.getName())) {
                System.out.println(zipEntry.getName());
                File file = new File("Extracted", zipEntry.getName());
                new File(file.getParent()).mkdirs();
                file.createNewFile();
                writeFile(zipFile.getInputStream(zipEntry), new BufferedOutputStream(new FileOutputStream(file)));
            }
        }
    }

    /**
     * first argument is a directory name
     * second argument is a regexp
     * searches recursively the directory for zip archives and
     * extracts only those files which match the regex
     *
     * @param args a string array with two string arguments (dirname and regex)
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 2) {
            System.out.println("Bad Input");
        } else {
            new File("Extracted").mkdir();
            String path = args[0];
            String regex = args[1];
            try {
                findZip(new File(path), regex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
