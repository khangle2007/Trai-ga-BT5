package com.example.trai_ga_bt5;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ButtonHandler implements View.OnClickListener {

    private boolean hasError = false;

    private Expression expression;
    private Context context;
    private Process process;
    private boolean isDegree = false;

    public ButtonHandler(Expression expression, Context context, Process process) {
        this.expression = expression;
        this.context = context;
        this.process = process;
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        if (viewId == R.id.number0) handleNumber("0");
        else if (viewId == R.id.number1) handleNumber("1");
        else if (viewId == R.id.number2) handleNumber("2");
        else if (viewId == R.id.number3) handleNumber("3");
        else if (viewId == R.id.number4) handleNumber("4");
        else if (viewId == R.id.number5) handleNumber("5");
        else if (viewId == R.id.number6) handleNumber("6");
        else if (viewId == R.id.number7) handleNumber("7");
        else if (viewId == R.id.number8) handleNumber("8");
        else if (viewId == R.id.number9) handleNumber("9");
        else if (viewId == R.id.e) handleNumber("e");
        else if (viewId == R.id.mu) handleNumber("μ");
        else handleOperator(viewId);
    }

    public void handleNumber(String number) {
        String current = expression.getExpression();

        if (current.startsWith("=") || hasError) {
            expression.clear();
            expression.append(number);
            hasError = false;
            process.start();
            return;
        }

        if (current.equals("0")) {
            expression.clear();
        }

        expression.append(number);
        process.start();
    }

    public void handleOperator(int viewId) {

        String current = expression.getExpression();

        if (viewId == R.id.sin) {

            if (current.startsWith("=") || hasError) {
                expression.clear();
                current = "";
                hasError = false;
            }

            if (current.equals("0")) {
                expression.clear();
            }

            expression.append("sin");
            process.start();
        }

        else if (viewId == R.id.deg) {
            isDegree = !isDegree;
            if(isDegree) {
                Toast.makeText(context, "tính theo độ", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "tính theo radian", Toast.LENGTH_SHORT).show();
            }
        }

        else if (viewId == R.id.Ac) {
            expression.clear();
            hasError = false;
            process.reset();
        }

        else if (viewId == R.id.delete) {

            if (current.length() <= 1 || hasError) {
                expression.clear();
                expression.append("0");
                hasError = false;
            } else {
                expression.deleteLast();
                process.start();
            }
        }

        else if (viewId == R.id.devide) {
            appendOperator("/");
        }

        else if (viewId == R.id.plus) {
            appendOperator("+");
        }

        else if (viewId == R.id.minus) {
            appendOperator("-");
        }

        else if (viewId == R.id.multi) {
            appendOperator("*");
        }

        else if (viewId == R.id.decimal) {

            if (current.startsWith("=") || hasError || current.equals("0")) {
                expression.clear();
                expression.append("0.");
                hasError = false;
                process.start();
            }
            else {
                expression.append(".");
                process.start();
            }
        }

        else if (viewId == R.id.submit) {
            process.stop();
            if(current.startsWith("=") || hasError || current.length()==1 && current.charAt(0)=='0')
            {
                expression.setExpression("0");
                return;
            }
            calculate();
        }
    }

    private void appendOperator(String operator) {

        String current = expression.getExpression();

        if (hasError || current.startsWith("=")) {
            return;
        }

        expression.append(operator);
        process.start();
    }

    private void calculate() {

        String current = expression.getExpression();

        if (current.isEmpty()) {
            return;
        }

        try {

            double answer = Calculator.calculate(current, isDegree);

            DecimalFormat df = new DecimalFormat("0.##########");

            df.setDecimalFormatSymbols(
                    DecimalFormatSymbols.getInstance(Locale.US)
            );

            expression.setExpression("=" + df.format(answer));
            hasError = false;

        }
        catch (ArithmeticException e) {
            expression.setExpression("Divide by zero");
            hasError = true;
        }
        catch (NumberFormatException e) {
            expression.setExpression("Invalid operand");
            hasError = true;
        }
        catch (IllegalArgumentException e) {
            expression.setExpression("Expression invalid");
            hasError = true;
        }
        catch (Exception e) {
            expression.setExpression("Error");
            hasError = true;
        }
    }

    public boolean isDegree() {
        return isDegree;
    }

    public void setDegree(boolean degree) {
        isDegree = degree;
    }
}