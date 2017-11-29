package ru.tilacyn.calculator;

import ru.tilacyn.stack.Stack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class for reforming mathematical expressions in infix notation to reverse polish notation
 * and evaluationg them in reverse polish notation
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
    public Calculator(String infixSequence) {
        this.infixSequence = infixSequence;
        numbers = new Stack<>(infixSequence.length());
        operations = new Stack<>(infixSequence.length());
        setCodesAndPriorities();
    }

    /**
     * non-public constructor that allows you to initialize stacks with your own objects
     *
     * @param infixSequence
     * @param operations
     * @param numbers
     */
    Calculator(String infixSequence, Stack operations, Stack numbers) {
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
    private int readNumber(Integer index) {
        int res = 0;
        while (index < infixSequence.length() && isDigit(infixSequence.charAt(index))) {
            res *= 10;
            res += (int) infixSequence.charAt(index) - (int) '0';
            index++;
        }
        index--;
        return res;
    }

    /**
     * @param c character that is analized
     * @return true if it is digit, false otherwise
     */
    private boolean isDigit(char c) {
        if ((int) c >= (int) '0' && (int) c <= '9') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param n
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
     */
    void createPolishSequence() {
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
    public void printPolishSequence() {
        for (int i = 0; i < polishSequence.size(); i++) {
            if (polishSequence.get(i) == -1) {
                System.out.print("+ ");
            }
            if (polishSequence.get(i) == -2) {
                System.out.print("- ");
            }
            if (polishSequence.get(i) == -3) {
                System.out.print("* ");
            }
            if (polishSequence.get(i) == -4) {
                System.out.print("/ ");
            }
            if (polishSequence.get(i) >= 0) {
                System.out.print(polishSequence.get(i) + " ");
            }
        }
        System.out.println();
    }

    /**
     * evaluates polish sequence using simple algorithm using Stack numbers
     *
     * @return evaluate result
     */
    int evaluatePolishSequence() {
        for (int i = 0; i < polishSequence.size(); i++) {
            int current = polishSequence.get(i);
            if (current >= 0) {
                numbers.push(current);
            }

            if (current == -1) {
                int rigthOperand = numbers.pop();
                int leftOperand = numbers.pop();
                int operationResult = leftOperand + rigthOperand;
                numbers.push(operationResult);
            }

            if (current == -2) {
                int rigthOperand = numbers.pop();
                int leftOperand = numbers.pop();
                int operationResult = leftOperand - rigthOperand;
                numbers.push(operationResult);
            }

            if (current == -3) {
                int rigthOperand = numbers.pop();
                int leftOperand = numbers.pop();
                int operationResult = leftOperand * rigthOperand;
                numbers.push(operationResult);
            }

            if (current == -4) {
                int rigthOperand = numbers.pop();
                int leftOperand = numbers.pop();
                int operationResult = leftOperand / rigthOperand;
                numbers.push(operationResult);
            }
        }
        return numbers.top();
    }

    /**
     * method that creates polishSequence and evaluates it both
     *
     * @return evaluate result
     */
    public int evaluate() {
        createPolishSequence();
        printPolishSequence();
        return evaluatePolishSequence();
    }

}
