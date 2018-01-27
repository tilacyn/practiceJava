package ru.tilacyn.moduleLoader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ModuleLoader extends ClassLoader {
    private byte b[] = fetchClassFromFS();
    private Class<?> clazz = new ClassLoader() {
        public Class<?> getClass(String s, byte[] b, int l, int r) {
            return super.defineClass(s, b, l, r);
        }
    }.getClass("SomeClass", b, 0, b.length);

    public ModuleLoader() throws IOException {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    private byte[] fetchClassFromFS() throws IOException {
        InputStream is = new FileInputStream(new File("SomeClass.class"));

        // Get the size of the file
        long length = new File("SomeClass.class").length();

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + "SomeClass.class");
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;

    }
}
