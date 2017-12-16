package ru.tilacyn.stack;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void push() {
        Stack<Integer> stack = new Stack<>(50);
        Stack<String> stringStack = new Stack<>(5);
        fillTwoStacksAsserts(stack, stringStack);
    }

    @Test
    public void pop() throws Exception {
        Stack<Integer> stack = new Stack<>(50);
        Stack<String> stringStack = new Stack<>(10);
        fillTwoStacks(stack, stringStack);
        assertEquals(5, (int) stack.pop());
        assertEquals(4, (int) stack.pop());
        assertEquals(3, (int) stack.pop());
        assertEquals(2, (int) stack.pop());
        assertEquals(1, (int) stack.pop());
        assertEquals(null, stack.pop());
        stack.push(6);
        assertEquals(6, (int) stack.pop());
        assertEquals("5", (String) stringStack.pop());
        assertEquals("4", (String) stringStack.pop());
        assertEquals("3", (String) stringStack.pop());
        assertEquals("2", (String) stringStack.pop());
        assertEquals("1", (String) stringStack.pop());
        stringStack.push("hello");
        assertEquals("hello", (String) stringStack.pop());
    }

    @Test
    public void size() throws Exception {
        Stack<Integer> stack = new Stack<>(50);
        Stack<String> stringStack = new Stack<>(10);
        fillTwoStacks(stack, stringStack);
        assertEquals(5, (int) stack.size());
        stack.pop();
        assertEquals(4, (int) stack.size());
        stack.pop();
        assertEquals(3, (int) stack.size());
        stack.pop();
        assertEquals(2, (int) stack.size());
        stack.pop();
        assertEquals(1, (int) stack.size());
        stack.pop();
        assertEquals(0, (int) stack.size());
        stack.push(3);
        assertEquals(1, (int) stack.size());
        stack.push(3);
        assertEquals(2, (int) stack.size());
        stack.pop();
        assertEquals(1, (int) stack.size());
        stack.push(3);
        assertEquals(2, (int) stack.size());
        assertEquals(5, stringStack.size());
    }

    @Test
    public void top() throws Exception {
        Stack<Integer> stack = new Stack<>(50);
        Stack<String> stringStack = new Stack<>(10);
        fillTwoStacks(stack, stringStack);
        assertEquals(5, (int) stack.top());
        stack.pop();
        assertEquals(4, (int) stack.top());
        stack.pop();
        assertEquals(3, (int) stack.top());
        stack.pop();
        assertEquals(2, (int) stack.top());
        stack.pop();
        assertEquals(1, (int) stack.top());
        stack.pop();
        stack.push(6);
        assertEquals(6, (int) stack.top());
        assertEquals("5", (String) stringStack.top());
        stringStack.push("wow");
        assertEquals("wow", (String) stringStack.top());
    }

    void fillTwoStacksAsserts(Stack<Integer> stack, Stack<String> stringStack) {
        stack.push(1);
        assertEquals(1, (int) stack.top());
        stack.push(2);
        assertEquals(2, (int) stack.top());
        stack.push(3);
        assertEquals(3, (int) stack.top());
        stack.push(4);
        assertEquals(4, (int) stack.top());
        stack.push(5);
        assertEquals(5, (int) stack.top());


        stringStack.push("1");
        assertEquals("1", (String) stringStack.top());
        stringStack.push("2");
        assertEquals("2", (String) stringStack.top());
        stringStack.push("3");
        assertEquals("3", (String) stringStack.top());
        stringStack.push("4");
        assertEquals("4", (String) stringStack.top());
        stringStack.push("5");
        assertEquals("5", (String) stringStack.top());
    }

    void fillTwoStacks(Stack<Integer> stack, Stack<String> stringStack) {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        stringStack.push("1");
        stringStack.push("2");
        stringStack.push("3");
        stringStack.push("4");
        stringStack.push("5");
    }

}