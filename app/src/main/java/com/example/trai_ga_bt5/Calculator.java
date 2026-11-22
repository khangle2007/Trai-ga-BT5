package com.example.trai_ga_bt5;

public class Calculator {

    private static String expression;
    private static int pos;

    public static double calculate(String input) {
        return calculate(input, false);
    }

    public static double calculate(String input, boolean degreeMode) {
        expression = input;
        pos = 0;

        double result = parseExpression(degreeMode);

        if (pos != expression.length()) {
            throw new IllegalArgumentException("Expression invalid");
        }

        return result;
    }

    private static double parseExpression(boolean degreeMode) {
        double value = parseTerm(degreeMode);

        while (pos < expression.length()) {
            char op = expression.charAt(pos);

            if (op == '+') {
                pos++;
                value += parseTerm(degreeMode);
            }
            else if (op == '-') {
                pos++;
                value -= parseTerm(degreeMode);
            }
            else {
                break;
            }
        }

        return value;
    }

    private static double parseTerm(boolean degreeMode) {
        double value = parseUnary(degreeMode);

        while (pos < expression.length()) {

            char op = expression.charAt(pos);

            if (op == '*') {
                pos++;
                value *= parseUnary(degreeMode);
            }
            else if (op == '/') {
                pos++;

                double divisor = parseUnary(degreeMode);

                if (divisor == 0) {
                    throw new ArithmeticException("divide by zero");
                }

                value /= divisor;
            }
            else if (startsOperand()) {
                value *= parseUnary(degreeMode);
            }
            else {
                break;
            }
        }

        return value;
    }

    private static double parseUnary(boolean degreeMode) {

        if (pos < expression.length()) {

            char c = expression.charAt(pos);

            if (c == '-') {
                pos++;
                return -parseUnary(degreeMode);
            }

            if (c == '+') {
                pos++;
                return parseUnary(degreeMode);
            }
        }

        return parsePrimary(degreeMode);
    }

    private static double parsePrimary(boolean degreeMode) {

        if (pos >= expression.length()) {
            throw new IllegalArgumentException("missing operand");
        }

        if (expression.charAt(pos) == 'e') {
            pos++;
            return Math.E;
        }

        if (expression.charAt(pos) == 'μ') {
            pos++;
            return Math.PI;
        }

        if (expression.startsWith("sin", pos)) {
            pos += 3;

            double angle = parseUnary(degreeMode);

            if (degreeMode) {
                angle = Math.toRadians(angle);
            }

            return Math.sin(angle);
        }

        return parseNumber();
    }

    private static double parseNumber() {

        int start = pos;
        boolean hasDecimal = false;

        while (pos < expression.length()) {

            char c = expression.charAt(pos);

            if (Character.isDigit(c)) {
                pos++;
            }
            else if (c == '.' && !hasDecimal) {
                hasDecimal = true;
                pos++;
            }
            else {
                break;
            }
        }

        if (start == pos || expression.substring(start, pos).equals(".")) {
            throw new NumberFormatException("invalid operand");
        }

        return Double.parseDouble(expression.substring(start, pos));
    }

    private static boolean startsOperand() {

        if (pos >= expression.length()) {
            return false;
        }

        char c = expression.charAt(pos);

        return c == 'e'
                || c == 'μ'
                || expression.startsWith("sin", pos);
    }
}