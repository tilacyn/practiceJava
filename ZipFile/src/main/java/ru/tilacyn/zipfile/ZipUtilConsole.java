package ru.tilacyn.zipfile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.regex.*;

/**
 * a class for a console util, that takes two arguments
 */

public class ZipUtilConsole {

    /**
     * writes data from input stream to output stream
     * @param is - input stream
     * @param os - output stream
     * @throws IOException
     */

    private static void writeFile(InputStream is, OutputStream os) throws IOException{
        byte[] buf = new byte[1024];
        int l;
        while((l = is.read(buf)) >= 0) {
            os.write(buf, 0, l);
        }
    }

    /**
     * recursively searches for zip archives in the directory
     * @param dir - directory
     * @param regex
     * @throws IOException
     */

    private static void findZip(File dir, String regex) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findZip(file, regex);
            } else {
                if(checkWithRegex(".*\\.zip", file.getName())){
                    unZip(new ZipFile(file), regex);
                }
            }
        }
    }

    /**
     * checks whether a string does match regex
     * @param regex
     * @param s - string we want to check
     * @return true - it mathes, false - else
     */

    private static boolean checkWithRegex(String regex, String s){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * extracts each file from the archive which matches regex
     * @param zipFile - archive
     * @param regex
     * @throws IOException
     */

    private static void unZip(ZipFile zipFile, String regex) throws IOException {
        Enumeration entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (!zipEntry.isDirectory() && checkWithRegex(regex, zipEntry.getName())) {
                File file = new File("Extracted", zipEntry.getName());
                new File(file.getParent()).mkdirs();
                file.createNewFile();
                writeFile(zipFile.getInputStream(zipEntry), new BufferedOutputStream(new FileOutputStream(file)));
            }
        }
    }

    /**
     * * first is a directory name
     * second is a regexp
     * searches recursively the directory for zip archives and
     * extracts only those files which match the regex
     * @param args - a string array with two string arguments (dirname and regex)
     */

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Bad Input");
        } else {
            new File("Extracted").mkdir();
            String path = args[0];
            String regex = args[1];
            try {
                findZip(new File(path), regex);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
