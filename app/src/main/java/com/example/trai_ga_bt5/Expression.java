
package com.example.trai_ga_bt5;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private TextView result;
    private TextView subtend;
    private Process process;

    private boolean isDegree = false;


    public Expression(TextView result, TextView subtend,
                      Process process) {

        this.result = result;
        this.subtend = subtend;
        this.process = process;
    }

    public String prepareExpression(String expression) {

        Pattern pattern = Pattern.compile("sin(\\d+(\\.\\d*)?|\\.\\d+)");
        Matcher matcher = pattern.matcher(expression);

        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {

            double angle = Double.parseDouble(matcher.group(1));
            double value;

            if (isDegree) {
                value = Math.sin(Math.toRadians(angle));
            }
            else {
                value = Math.sin(angle);
            }

            matcher.appendReplacement(
                    buffer,
                    Double.toString(value)
            );
        }

        matcher.appendTail(buffer);

        return buffer.toString()
                .replace("e", Double.toString(Math.E))
                .replace("μ", Double.toString(Math.PI));
    }

    public void colorExpression(TextView textView, String expression) {

        SpannableString spannable = new SpannableString(expression);

        int operatorColor = Color.parseColor("#5689F5");

        for (int i = 0; i < expression.length(); i++) {

            char c = expression.charAt(i);

            if (c == '+' || c == '-' || c == '*'
                    || c == '/' || c == 'e'
                    || c == 's' || c == 'i' || c == 'n' || c == 'μ') {

                spannable.setSpan(
                        new ForegroundColorSpan(operatorColor),
                        i,
                        i + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        textView.setText(spannable);
    }

    public void updateExpression(TextView subtend) {

        String expression = subtend.getText().toString();

        colorExpression(subtend, expression);
    }

    public void handleNumber(String number) {

        if (result.getText().charAt(0) == '=') {

            process.reset();

            result.setText(number);

            process.start();
            return;
        }

        if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {

            process.reset();
            result.setText(number);

            process.start();
            return;
        }

        result.setText(result.getText() + number);
        updateExpression(result);
    }

    public void handleAnother(int viewId) {
        if (viewId == R.id.e) {

            if (result.getText().toString().startsWith("=") || result.getText().equals("Error") ) {

                process.reset();
                result.setText("e");

                process.start();
                return;
            }

            if(result.getText().length() == 1 && result.getText().equals("0"))
            {
                result.setText("e");
                process.start();
                return;
            }

            result.setText(result.getText() + "e");
            updateExpression(result);
        }

        else if (viewId == R.id.mu) {

            if (result.getText().toString().startsWith("=") || result.getText().equals("Error")) {

                process.reset();
                result.setText("μ");

                process.start();
                return;
            }

            if(result.getText().length() == 1 && result.getText().equals("0"))
            {
                result.setText("μ");
                process.start();
                return;
            }
            result.setText(result.getText() + "μ");
            updateExpression(result);
        }

        else if (viewId == R.id.sin) {

            if (result.getText().toString().startsWith("=") || result.getText().equals("Error")) {
                process.reset();
                result.setText("sin");

                process.start();
                return;
            }

            if(result.getText().length() == 1 && result.getText().equals("0"))
            {
                result.setText("sin");
                process.start();
                return;
            }

            result.setText(result.getText() + "sin");
            updateExpression(result);
        }

        else if (viewId == R.id.deg) {
            isDegree = !isDegree;
        }

        else if (viewId == R.id.Ac) {
            process.reset();
        }

        else if (viewId == R.id.delete) {

            if (result.getText().length() == 1 || result.getText().equals("Error")) {

                result.setText("0");

            }
            else {
                result.setText(result.getText().subSequence(0, result.getText().length() - 1));
            }
        }

        else if (viewId == R.id.devide) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "/");
            updateExpression(result);
        }

        else if (viewId == R.id.plus) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "+");
            updateExpression(result);
        }

        else if (viewId == R.id.minus) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "-");
            updateExpression(result);
        }

        else if (viewId == R.id.multi) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "*");
            updateExpression(result);;
        }

        else if (viewId == R.id.decimal) {

            if ((result.getText().length() == 1 && result.getText().equals("0"))) {

                result.setText(result.getText() + ".");
                process.start();
                return;
            }

            if (result.getText().charAt(0) == '=' || result.getText().equals("Error")) {
                result.setText("0.");
                process.start();
                return;
            }

            result.setText(result.getText() + ".");
        }

        else if (viewId == R.id.submit) {

            String sub = result.getText().toString();

            if (sub.isEmpty()) {
                return;
            }

            try {
                sub = prepareExpression(sub);
                double answer = Calculator.calculate(sub);

                colorExpression(subtend, sub);

                DecimalFormat df = new DecimalFormat("0.##########");
                df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

                if (answer == (long) answer) {
                    result.setText("=" + df.format(answer));
                } else {
                    result.setText("=" + df.format(answer));
                }

                process.stop();

            } catch (Exception e) {
                result.setText("Error");
                process.stop();
            }
        }
    }
}

