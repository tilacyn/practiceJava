package ru.tilacyn.calculator;

import org.junit.Test;
import ru.tilacyn.stack.Stack;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class CalculatorTest {
    @Test
    public void createPolishSequence() throws Exception {
        //Stack<Integer> operations = mock(Stack.class);
        Stack<Character> operations = new Stack<>(100000);
        Stack<Integer> numbers = new Stack<>(100000);
        Calculator calculator = new Calculator("(3*3+4*(90-3))", operations, numbers);
        calculator.createPolishSequence();
        calculator.printPolishSequence();
        Integer[] result1 = {3, 3, -3, 4, 90, 3, -2, -3, -1};
        assertArrayEquals(result1, calculator.getPolishSequence().toArray());

        calculator = new Calculator("(3+4-10)", operations, numbers);
        calculator.createPolishSequence();
        calculator.printPolishSequence();
        Integer[] result2 = {3, 4, -1, 10, -2};
        assertArrayEquals(result2, calculator.getPolishSequence().toArray());

        calculator = new Calculator("(3+4-10+1)", operations, numbers);
        calculator.createPolishSequence();
        calculator.printPolishSequence();
        Integer[] result3 = {3, 4, -1, 10, -2, 1, -1};
        assertArrayEquals(result3, calculator.getPolishSequence().toArray());
        assertEquals(calculator.evaluatePolishSequence(), -2);
    }


    @Test
    public void evaluatePolishSequence() throws Exception {
        Stack<Character> operations = new Stack<>(100000);
        Stack<Integer> numbers = new Stack<>(100000);
        Calculator calculator = new Calculator("(3*3+4*(90-3))", operations, numbers);
        calculator.createPolishSequence();
        assertEquals(357, calculator.evaluatePolishSequence());

        numbers = mock(Stack.class);

        calculator = new Calculator("((5 + 9) / 7)", new Stack<Character>(10000), numbers);

        when(numbers.pop()).thenReturn(9, 5, 14, 7);
        when(numbers.top()).thenReturn(14, 2);
        calculator.createPolishSequence();
        calculator.evaluatePolishSequence();


        verify(numbers, times(5)).push(anyInt());

        numbers = mock(Stack.class);

        calculator = new Calculator("(((3 / 3) / 1) * 1)", new Stack<Character>(10000), numbers);

        calculator.createPolishSequence();
        calculator.printPolishSequence();

        when(numbers.pop()).thenReturn(3, 3, 1, 1, 1, 1, 1);
        when(numbers.top()).thenReturn(1, 1, 1, 1);

        calculator.evaluatePolishSequence();

        verify(numbers, times(7)).push(anyInt());
    }

    @Test
    public void evaluate() throws Exception {
        Calculator calculator = new Calculator("((3+22)/5*(9/3))");
        assertEquals(15, calculator.evaluate());
        calculator = new Calculator("(3+(4+(5+(6+(7+8)))))");
        assertEquals(33, calculator.evaluate());
        calculator = new Calculator("(729/3/3/3/3/9)");
        assertEquals(1, calculator.evaluate());
        calculator = new Calculator("(729/3/3/3/(9/3))");
        assertEquals(9, calculator.evaluate());
        calculator = new Calculator("(1*(2*(3*(4*(5*(6*7))))))");
        assertEquals(5040, calculator.evaluate());
        calculator = new Calculator("((((((1*2)*3)*4)*5)*6)*7)");
        assertEquals(5040, calculator.evaluate());
    }

}