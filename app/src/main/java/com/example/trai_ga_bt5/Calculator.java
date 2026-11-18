
package com.example.trai_ga_bt5;

import java.util.Stack;

public class Calculator {

    private static int precedence(char operator) {

        if (operator == '*' || operator == '/') {
            return 2;
        }

        if (operator == '+' || operator == '-') {
            return 1;
        }

        return 0;
    }

    private static double applyOperation(double a, double b, char operator) {

        switch (operator) {

            case '+':
                return a + b;

            case '-':
                return a - b;

            case '*':
                return a * b;

            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
        }

        throw new IllegalArgumentException("Invalid operator");
    }


    public static double calculate(String expression) {

        Stack<Double> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        int i = 0;

        while (i < expression.length()) {

            char c = expression.charAt(i);

            // Kiểm tra số âm hoặc số dương
            if (Character.isDigit(c) || c == '.'
                    || (c == '-' && (i == 0
                    || expression.charAt(i - 1) == '+'
                    || expression.charAt(i - 1) == '-'
                    || expression.charAt(i - 1) == '*'
                    || expression.charAt(i - 1) == '/'))) {

                StringBuilder number = new StringBuilder();

                if (c == '-') {
                    number.append(c);
                    i++;
                }

                while (i < expression.length()
                        && (Character.isDigit(expression.charAt(i))
                        || expression.charAt(i) == '.')) {

                    number.append(expression.charAt(i));
                    i++;
                }

                numberStack.push(Double.parseDouble(number.toString()));
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/') {

                while (!operatorStack.isEmpty()
                        && precedence(operatorStack.peek()) >= precedence(c)) {

                    double b = numberStack.pop();
                    double a = numberStack.pop();

                    char op = operatorStack.pop();

                    numberStack.push(applyOperation(a, b, op));
                }

                operatorStack.push(c);
            }

            i++;
        }

        while (!operatorStack.isEmpty()) {

            double b = numberStack.pop();
            double a = numberStack.pop();

            char op = operatorStack.pop();

            numberStack.push(applyOperation(a, b, op));
        }

        return numberStack.pop();
    }
}
