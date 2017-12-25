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
        stack.push(6);
        assertEquals(6, (int) stack.pop());
        assertEquals("5", stringStack.pop());
        assertEquals("4", stringStack.pop());
        assertEquals("3", stringStack.pop());
        assertEquals("2", stringStack.pop());
        assertEquals("1", stringStack.pop());
        stringStack.push("hello");
        assertEquals("hello", stringStack.pop());
    }

    @Test(expected = Stack.EmptyStackException.class)
    public void popFromEmpty() throws Stack.EmptyStackException {
        new Stack<String>(2).pop();
    }

    @Test
    public void size() throws Exception {
        Stack<Integer> stack = new Stack<>(50);
        Stack<String> stringStack = new Stack<>(10);
        fillTwoStacks(stack, stringStack);
        assertEquals(5, stack.size());
        stack.pop();
        assertEquals(4, stack.size());
        stack.pop();
        assertEquals(3, stack.size());
        stack.pop();
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
        stack.pop();
        assertEquals(0, stack.size());
        stack.push(3);
        assertEquals(1, stack.size());
        stack.push(3);
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
        stack.push(3);
        assertEquals(2, stack.size());
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
        assertEquals("5", stringStack.top());
        stringStack.push("wow");
        assertEquals("wow", stringStack.top());
    }

    private void fillTwoStacksAsserts(Stack<Integer> stack, Stack<String> stringStack) {
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
        assertEquals("1", stringStack.top());
        stringStack.push("2");
        assertEquals("2", stringStack.top());
        stringStack.push("3");
        assertEquals("3", stringStack.top());
        stringStack.push("4");
        assertEquals("4", stringStack.top());
        stringStack.push("5");
        assertEquals("5", stringStack.top());
    }

    @Test
    public void capacityIncrease() {
        Stack<Integer> stack = new Stack<>(1);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        stack = new Stack<>(2);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
    }

    private void fillTwoStacks(Stack<Integer> stack, Stack<String> stringStack) {
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