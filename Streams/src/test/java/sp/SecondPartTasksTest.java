package sp;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.*;
import static sp.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        char s = File.separatorChar;
        assertEquals(Arrays.asList("to the endless", "delight"),
                findQuotes(Arrays.asList("src" + s + "test" + s + "resources" + s + "1", "src" + s + "test" + s + "resources" + s + "2"), "d"));

    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, piDividedBy4(), 0.01);
    }

    @Test
    public void testFindPrinter() {
        assertEquals(
                "Arthur Conan Doyle",
                findPrinter(ImmutableMap.of(
                        "Arthur Conan Doyle", Arrays.asList("hello", "my name", "is", "Sir Arthur Conan Doyle", "I", "like", "deduction"),
                        "John Ronald Reuel Tolkien", Arrays.asList("hello", "my name", "is", "John", "I", "like", "elves", "and", "hobbits"),
                        "Jack London", Arrays.asList("hello", "my name", "is", "Jack", "I", "like", "wolves", "and", "lust for life"))));
    }

    @Test
    public void testCalculateGlobalOrder() {
        assertEquals(
                ImmutableMap.of(
                        "potatoes", 6,
                        "carrots", 4),
                calculateGlobalOrder(Arrays.asList(ImmutableMap.of(
                        "potatoes", 2,
                        "carrots", 3),
                        ImmutableMap.of(
                                "potatoes", 4,
                                "carrots", 1)
                )));

    }
}