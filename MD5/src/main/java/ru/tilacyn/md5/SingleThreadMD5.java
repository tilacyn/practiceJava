package ru.tilacyn.md5;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single threaded version of MD5 counter
 * You are able to set a filename and if it is a directory then method calculate returns MD5(dir_name + f(child1) + ... + f(childn))
 * If it is not a directory then it returns an MD5 of a file content
 * Hash algorithm is SHA-256
 */
public class SingleThreadMD5 {
    private File root;

    private static final String algo = "SHA-256";

    /**
     * A constructor that takes the only parameter - directory name
     * @param dir a name of a directory we want to count MD5
     */
    public SingleThreadMD5(@NotNull String dir) {
        root = new File(dir);
    }


    /**
     * exactly calculates the MD5 function
     * if given file is a directory then method calculate returns MD5(dir_name + f(child1) + ... + f(childn))
     * if given file is not a directory then it returns an MD5 of a file content
     *
     * @return MD5 of a file
     * @throws IOException if problems with reading file occurred
     * @throws NoSuchAlgorithmException actually cannot be thrown
     *
     */
    public byte[] calculate() throws IOException, NoSuchAlgorithmException {
        return calculate(root);
    }

    private byte[] calculate(@NotNull File current) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algo);

        if (current.isDirectory()) {
            md.update(current.getName().getBytes());

            for (File child : current.listFiles()) {
                md.update(calculate(child));
            }
        } else {
            md.update(calculateFile(current));
        }

        return md.digest();
    }

    private byte[] calculateFile(@NotNull File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algo);

        DigestInputStream dis = new DigestInputStream(new FileInputStream(file), md);

        while (dis.read() != -1) {

        }
        return dis.getMessageDigest().digest();
    }

}
