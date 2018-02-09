package ru.tilacyn.myTemporaryFolder;

import org.jetbrains.annotations.Nullable;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.IOException;

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


    /**
     * creates a new Temporary Folder in the destination folder
     */
    public void create() throws IOException {
        tmpFolder = File.createTempFile("mtf", null, destinationFolder);
        tmpFolder.delete();
        tmpFolder.mkdir();
    }

    /**
     * @param fileName specified file name
     * @return a new file with the specified name under the temporary folder
     */
    public File newFile(@NotNull String fileName) throws IOException {
        File newFile = new File(getRoot(), fileName);
        newFile.mkdirs();
        newFile.delete();
        newFile.createNewFile();
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
     * @return a File of the temporary folder
     */
    public File getRoot() {
        if (tmpFolder != null) {
            return tmpFolder;
        }
        throw new IllegalStateException();
    }


    /**
     * @param path folder name(path)
     * @return a new folder under the temporary folder with the specified folder name(path)
     */
    public File newFolder(@NotNull String path) {
        File newFolder = new File(tmpFolder, path);
        newFolder.mkdirs();
        return newFolder;
    }

    /**
     * @param paths path to folder
     * @return a new folder under the temporary folder with the specified path
     */
    public File newFolder(@NotNull String... paths) {
        File current = tmpFolder;
        for (String path : paths) {
            current = new File(current, path);
            current.mkdirs();
        }
        return current;
    }

    /**
     * @return a new folder with random name under the temporary folder
     * @throws IOException if problems with creating new file occurred
     */
    public File newFolder() throws IOException {
        File newFolder = File.createTempFile("mtf", null, tmpFolder);
        newFolder.delete();
        newFolder.mkdirs();
        return newFolder;
    }


    /**
     * deletes recursively the temporary folder
     */
    public void delete() {
        if (tmpFolder == null) {
            return;
        }
        deleteRecursively(tmpFolder);
    }

    private void deleteRecursively(@NotNull File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }

}
