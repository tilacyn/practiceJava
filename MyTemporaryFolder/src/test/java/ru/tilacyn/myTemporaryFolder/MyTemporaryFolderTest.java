package ru.tilacyn.myTemporaryFolder;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MyTemporaryFolderTest {
    private final Character sep = File.separatorChar;
    private final String testDir = "src" + sep + "test" + sep + "resources" + sep;

    private File testDirFile = new File(testDir);

    @Rule
    public MyTemporaryFolder mtf = new MyTemporaryFolder(testDirFile);

    @Test
    public void create() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        assertEquals(testDirFile.listFiles().length, 1);
        File mtfFile = testDirFile.listFiles()[0];
        assertTrue(mtfFile.isDirectory());
        assertTrue(mtfFile.getName().substring(0, 3).equals("mtf"));
        mtf.delete();
    }

    @Test
    public void noParamsConstructor() throws Exception {
        mtf = new MyTemporaryFolder();
        mtf.create();
        assertEquals(mtf.getRoot().getParent(),
                File.createTempFile("prefix", null, null).getParent());

        mtf.delete();
    }


    @Test
    public void newFileRandomName() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        File newFile = mtf.newFile();
        assertTrue(newFile.exists());
        assertTrue(newFile.isFile());
        assertEquals(newFile.getParent(), mtf.getRoot().getPath());
        mtf.delete();
    }

    @Test
    public void newFile() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        File newFile = mtf.newFile("folder/file.txt");
        assertTrue(newFile.exists());
        assertTrue(newFile.isFile());
        assertEquals(newFile.getParent(), mtf.getRoot().getPath() + sep + "folder");
        mtf.delete();
    }

    @Test
    public void getRoot() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        assertEquals(mtf.getRoot().getParent() + sep, testDir);
        mtf.newFile();
        mtf.newFile("1");
        mtf.newFolder("f");
        mtf.newFolder("f", "t", "p");
        assertEquals(mtf.getRoot().getParent() + sep, testDir);
        mtf.delete();
    }

    @Test
    public void newFolder() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        assertTrue(mtf.newFolder("f").exists());
        assertTrue(mtf.newFolder("f" + sep + "t" + sep + "p").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f" + sep + "t").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f" + sep + "t" + sep + "p").exists());
        mtf.delete();
    }

    @Test
    public void newFolderManyArgs() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        assertTrue(mtf.newFolder("f").exists());
        assertTrue(mtf.newFolder("f", "t", "p").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f" + sep + "t").exists());
        assertTrue(new File(mtf.getRoot().getPath() + sep + "f" + sep + "t" + sep + "p").exists());
        mtf.delete();
    }

    @Test
    public void newFolderNoArgs() throws Exception {
        mtf = new MyTemporaryFolder(testDirFile);
        mtf.create();
        File f = mtf.newFolder();
        assertTrue(f.exists());
        assertEquals(f.getParent(), mtf.getRoot().getPath());
        mtf.delete();
    }
}