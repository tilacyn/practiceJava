package ru.tilacyn.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;


/**
 * A version of MD5 counter which separates big tasks into the small ones and uses Fork-Join Pool
 * You are able to set a filename and if it is a directory then method calculate returns MD5(dir_name + f(child1) + ... + f(childn))
 * If it is not a directory then it returns an MD5 of a file content
 * Hash algorithm is SHA-256
 */
public class ForkJoinMD5 {
    private File root;

    private static final String algo = "SHA-256";

    /**
     * a constructor with the only parameter dir - name of a directory we want to calculate MD5
     * @param dir name of a directory we want to calculate MD5
     */
    public ForkJoinMD5(@NotNull String dir) {
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
     */
    public byte[] calculate() throws IOException, NoSuchAlgorithmException {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new MD5RecursiveTask(root));
    }

    private class MD5RecursiveTask extends RecursiveTask<byte[]> {
        private File file;

        MD5RecursiveTask(@NotNull File f) {
            file = f;
        }

        @Override
        protected byte[] compute() {
            if (!file.isDirectory()) {
                try {
                    return calculateFile(file);
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance(algo);
                    md.update(file.getName().getBytes());

                    ArrayList<MD5RecursiveTask> tasks = new ArrayList<>();

                    for (File child : file.listFiles()) {
                        if (child != null) {
                            tasks.add(new MD5RecursiveTask(child));
                        }
                    }

                    for (MD5RecursiveTask task : tasks) {
                        task.fork();
                    }

                    for (MD5RecursiveTask task : tasks) {
                        md.update(task.join());
                    }

                    return md.digest();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        private byte[] calculateFile(@NotNull File file) throws IOException, NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance(algo);

            DigestInputStream dis = new DigestInputStream(new FileInputStream(file), md);

            while (dis.read() != -1) {

            }
            return dis.getMessageDigest().digest();
        }
    }


}
