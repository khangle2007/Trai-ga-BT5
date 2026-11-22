package com.example.trai_ga_bt5;

public class Expression {

    private String expression = "";

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void append(String value) {
        expression += value;
    }

    public void deleteLast() {
        if (!expression.isEmpty()) {
            expression = expression.substring(0, expression.length() - 1);
        }
    }

    public void clear() {
        expression = "";
    }
}