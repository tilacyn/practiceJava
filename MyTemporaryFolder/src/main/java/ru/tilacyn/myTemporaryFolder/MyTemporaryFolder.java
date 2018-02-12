package ru.tilacyn.myTemporaryFolder;

import org.jetbrains.annotations.Nullable;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jetbrains.annotations.NotNull;

/**
 * MyTemporaryFolder Rule allows creation of files and folders for temporary usage that should be
 * deleted when the test method finishes
 * So this class is rather helpful for testing
 */
public class MyTemporaryFolder extends ExternalResource {

    /* destination folder */
    private File destinationFolder;

    /* temporary folder */
    private File tmpFolder;

    /* initializes destination folder as a default directory (actually null) */
    public MyTemporaryFolder() {
    }


    /**
     * initializes destination folder as a specified directory
     *
     * @param parentFolder specified directory
     */
    public MyTemporaryFolder(@Nullable File parentFolder) {
        destinationFolder = parentFolder;
    }

    @Override
    public void before() throws IOException {
        if (destinationFolder != null) {
            destinationFolder.delete();
            destinationFolder.mkdirs();
        }
    }

    @Override
    public void after() {
        if (destinationFolder != null) {
            destinationFolder.delete();
        }
    }


    /**
     * creates a new Temporary Folder in the destination folder
     */
    public void create() throws IOException {
        if (destinationFolder == null) {
            tmpFolder = Files.createTempDirectory("mtf").toFile();
        } else {
            tmpFolder = Files.createTempDirectory(destinationFolder.toPath(), "mtf").toFile();
        }
    }

    /**
     * creates a new file with the specified file name
     *
     * @param fileName specified file name
     * @return a new file with the specified name under the temporary folder
     */
    public File newFile(@NotNull String fileName) throws IOException {
        File newFile = new File(getRoot(), fileName);
        if (!newFile.mkdirs()) {
            throw new IOException("Invalid path");
        }
        if (!newFile.delete()) {
            throw new IOException("Deletion failed");
        }
        if (!newFile.createNewFile()) {
            throw new IOException("File creation failed");
        }
        return newFile;
    }

    /**
     * creates a new file with a random name under the temporary folder
     *
     * @return a new File
     */
    public File newFile() throws IOException {
        return File.createTempFile("mtf", null, getRoot());
    }


    /**
     * returns temporary folder directory
     *
     * @return a File of the temporary folder
     */
    public File getRoot() {
        if (tmpFolder != null) {
            return tmpFolder;
        }
        throw new IllegalStateException("Temporary folder has not been created");
    }


    /**
     * creates a new folder under the temporary folder with the specified path
     *
     * @param path folder name(path)
     * @return a new folder under the temporary folder with the specified folder name(path)
     */
    public File newFolder(@NotNull String path) throws IOException {
        File newFolder = new File(tmpFolder, path);
        if (!newFolder.mkdirs()) {
            throw new IOException("Invalid path(file might alredy exist)");
        }
        return newFolder;
    }

    /**
     * creates a new folder with the specified path
     *
     * @param paths array of strings, each string is considered to be a directory under the previous one
     * @return a new folder under the temporary folder with the specified path
     */
    public File newFolder(@NotNull String... paths) throws Exception {
        File current = tmpFolder;
        for (String path : paths) {
            current = new File(current, path);
            current.mkdirs();
        }
        return current;
    }

    /**
     * creates a new folder with a random name under the temporary folder
     *
     * @return a new folder with random name under the temporary folder
     * @throws IOException if problems with creating new file occurred
     */
    public File newFolder() throws IOException {
        File newFolder = File.createTempFile("mtf", null, tmpFolder);
        // if problems occur exception will be thrown by the createTempFile call
        newFolder.delete();
        if (!newFolder.mkdirs()) {
            throw new IOException("Invalid path");
        }
        return newFolder;
    }


    /**
     * deletes recursively the temporary folder
     */
    public void delete() throws IOException {
        if (tmpFolder == null) {
            return;
        }
        deleteRecursively(tmpFolder);
    }

    private void deleteRecursively(@NotNull File file) throws IOException {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        if (!file.delete()) {
            throw new IOException("Deletion failed");
        }
    }

}
