package ru.tilacyn.calculator;

import org.jetbrains.annotations.NotNull;
import ru.tilacyn.stack.Stack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class for reforming mathematical expressions in infix notation to reverse polish notation
 * and evaluating them in reverse polish notation
 * accepts expressions in infix notation inside the global braces with operation
 * +, -, *, /
 * supports internal braces in the expression
 * if expression given is incorrect nothing is guaranteed, runtime error or wrong answer are both possible
 */
public class Calculator {
    /**
     * expression in infix notation of type string which is always initialized with a constructor argument
     */
    private String infixSequence;

    /**
     * expression in reverese polish notation stored in integer array,
     * integers less than 0 in the array are codes for operations
     */
    private final ArrayList<Integer> polishSequence = new ArrayList<>();

    /**
     * hashmap, is used for creating polishSequence contains pairs {Character operation, Integer prioroty}
     */
    private HashMap<Character, Integer> priority = new HashMap<>();

    /**
     * hashmap, is used for coding operations in polishSequence
     * contains pairs {Character operation, Integer Code}
     */
    private HashMap<Character, Integer> code = new HashMap<>();

    /**
     * stack with character values, is used in creating polish sequence
     * contains operation characters
     */
    private Stack<Character> operations;

    /**
     * stack with integer values, is used in evaluating polish sequence
     */
    private Stack<Integer> numbers;

    /**
     * public constructor
     *
     * @param infixSequence is used to initialize this.infixSequence
     */
    public Calculator(@NotNull String infixSequence) {
        this.infixSequence = infixSequence;
        numbers = new Stack<>(infixSequence.length());
        operations = new Stack<>(infixSequence.length());
        setCodesAndPriorities();
    }

    /**
     * non-public constructor that allows you to initialize stacks with your own objects
     *
     * @param infixSequence is used to initialize this.infixSequence
     * @param operations    is used to initialize this.operations
     * @param numbers       is used to initialize this.numbers
     */
    Calculator(@NotNull String infixSequence, @NotNull Stack<Character> operations, @NotNull Stack<Integer> numbers) {
        this.infixSequence = infixSequence;
        this.numbers = numbers;
        this.operations = operations;
        setCodesAndPriorities();
    }


    /**
     * this method is called in constructors and is necessary for evaluating methods
     * it fills hashmaps priority and codes
     */
    private void setCodesAndPriorities() {
        priority.put('(', 1);
        priority.put(')', 1);
        priority.put('+', 2);
        priority.put('-', 2);
        priority.put('*', 3);
        priority.put('/', 3);
        code.put('+', -1);
        code.put('-', -2);
        code.put('*', -3);
        code.put('/', -4);
    }

    /**
     * reads integer from the point index in infixSequence until the operation character
     *
     * @param index a point we start reading from
     * @return result integer (always not negative)
     */
    private int readNumber(@NotNull Integer index) {
        int res = 0;
        while (index < infixSequence.length() && isDigit(infixSequence.charAt(index))) {
            res *= 10;
            res += (int) infixSequence.charAt(index) - (int) '0';
            index++;
        }
        return res;
    }

    /**
     * @param c character that is analized
     * @return true if it is digit, false otherwise
     */
    private boolean isDigit(char c) {
        return (int) c >= '0' && (int) c <= '9';
    }

    /**
     * @param n integer that is processed
     * @return length of n in digits
     */
    private int getNumberLength(int n) {
        if (n == 0) return 1;
        int result = 0;
        while (n > 0) {
            n /= 10;
            result++;
        }
        return result;
    }

    /**
     * creates polish sequence from infix sequence using hashmaps and Stack operations
     *
     * @throws Stack.EmptyStackException if pop is applied to an empty Stack
     *                                   this exception might be thrown when input is incorrect
     */
    void createPolishSequence() throws Stack.EmptyStackException {
        for (int i = 0; i < infixSequence.length(); i++) {
            if (isDigit(infixSequence.charAt(i))) {
                int number = readNumber(i);
                polishSequence.add(number);
                i += getNumberLength(number);
                i--;
                continue;
            }
            if (infixSequence.charAt(i) == '+') {

                while (!operations.isEmpty() && priority.get(operations.top()) >= 2) {
                    polishSequence.add(code.get(operations.pop()));
                }
                operations.push('+');
            }
            if (infixSequence.charAt(i) == '-') {

                while (!operations.isEmpty() && priority.get(operations.top()) >= 2) {
                    polishSequence.add(code.get(operations.pop()));
                }
                operations.push('-');
            }
            if (infixSequence.charAt(i) == '*') {

                while (!operations.isEmpty() && priority.get(operations.top()) >= 3) {
                    polishSequence.add(code.get(operations.pop()));
                }
                operations.push('*');
            }
            if (infixSequence.charAt(i) == '/') {

                while (!operations.isEmpty() && priority.get(operations.top()) >= 3) {
                    polishSequence.add(code.get(operations.pop()));
                }
                operations.push('/');
            }
            if (infixSequence.charAt(i) == '(') {
                operations.push('(');
            }
            if (infixSequence.charAt(i) == ')') {
                while (!operations.top().equals('(')) {
                    polishSequence.add(code.get(operations.pop()));
                }
                operations.pop();
            }
        }
    }

    /**
     * @return polishSequence of type ArrayList<Integer>
     */
    ArrayList<Integer> getPolishSequence() {
        return polishSequence;
    }

    /**
     * prints polish sequence with spaces between elements
     */
    void printPolishSequence() {
        for (Integer aPolishSequence : polishSequence) {
            if (aPolishSequence == -1) {
                System.out.print("+ ");
            }
            if (aPolishSequence == -2) {
                System.out.print("- ");
            }
            if (aPolishSequence == -3) {
                System.out.print("* ");
            }
            if (aPolishSequence == -4) {
                System.out.print("/ ");
            }
            if (aPolishSequence >= 0) {
                System.out.print(aPolishSequence + " ");
            }
        }
        System.out.println();
    }

    /**
     * evaluates polish sequence using simple algorithm using Stack numbers
     *
     * @return evaluate result
     * @throws Stack.EmptyStackException if pop is applied to an empty Stack
     *                                   this exception might be thrown when input is incorrect
     */
    int evaluatePolishSequence() throws Stack.EmptyStackException {
        for (Integer aPolishSequence : polishSequence) {
            int current = aPolishSequence;

            if (current >= 0) {
                numbers.push(current);
            }
            if (current == -1) {
                numbers.push(numbers.pop() + numbers.pop());
            }
            if (current == -2) {
                numbers.push(-numbers.pop() + numbers.pop());
            }
            if (current == -3) {
                numbers.push(numbers.pop() * numbers.pop());
            }
            if (current == -4) {
                int right = numbers.pop();
                numbers.push(numbers.pop() / right);
            }
        }
        return numbers.top();
    }

    /**
     * method that creates polishSequence and evaluates it both
     *
     * @return evaluate result
     * @throws Stack.EmptyStackException if pop is applied to an empty Stack
     *                                   this exception might be thrown when input is incorrect
     */
    public int evaluate() throws Stack.EmptyStackException {
        createPolishSequence();
        return evaluatePolishSequence();
    }

}
