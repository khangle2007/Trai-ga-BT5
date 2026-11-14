package com.example.trai_ga_bt5;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button0, button1, button2, button3, button4;
    private Button button5, button6, button7, button8, button9;

    private Button deleteAll, devide, sum, minus;
    private Button multiply, decimal, submit;

    private ImageButton delete;

    private TextView subtend, result, percent;
    private ProgressBar progressBar;

    private Handler handler;
    private Runnable update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View glow = findViewById(R.id.view);

        if (android.os.Build.VERSION.SDK_INT >= 31) {
            glow.setRenderEffect(
                    android.graphics.RenderEffect.createBlurEffect(
                            25f,
                            25f,
                            android.graphics.Shader.TileMode.CLAMP
                    )
            );
        }

        button0 = findViewById(R.id.button20);
        button1 = findViewById(R.id.button16);
        button2 = findViewById(R.id.button17);
        button3 = findViewById(R.id.button18);
        button4 = findViewById(R.id.button13);
        button5 = findViewById(R.id.button14);
        button6 = findViewById(R.id.button15);
        button7 = findViewById(R.id.button9);
        button8 = findViewById(R.id.button10);
        button9 = findViewById(R.id.button11);

        delete = findViewById(R.id.imageButton);
        deleteAll = findViewById(R.id.button5);
        devide = findViewById(R.id.button7);
        sum = findViewById(R.id.button19);
        minus = findViewById(R.id.button12);
        multiply = findViewById(R.id.button8);
        decimal = findViewById(R.id.button21);
        submit = findViewById(R.id.button22);

        subtend = findViewById(R.id.textView3);
        result = findViewById(R.id.textView4);

        progressBar = findViewById(R.id.progressBar);
        percent = findViewById(R.id.textView);

        handler = new Handler(Looper.getMainLooper());

        update = new Runnable() {
            public void run() {
                int current = progressBar.getProgress();

                if (current < 100) {
                    progressBar.setProgress(current + 1);
                    int num = Integer.parseInt(percent.getText().subSequence(0, percent.getText().length() - 1).toString());
                    percent.setText(num + 1 + "%");
                    handler.postDelayed(this, 100);
                } else {
                    handler.removeCallbacks(this);
                }

                if (current >= 100) {
                    progressBar.setProgress(0);
                    percent.setText("0%");
                    subtend.setText("");
                    result.setText("0");
                }
            }
        };

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        delete.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
        devide.setOnClickListener(this);
        sum.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        decimal.setOnClickListener(this);
        submit.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets imeInset = insets.getInsets(WindowInsetsCompat.Type.ime());

            result.setTranslationY(-imeInset.bottom);

            return insets;
        });

        subtend.setText("");
        result.setText("0");

    }

    public void onClick(View v) {

        int viewId = v.getId();

        if (viewId == R.id.button20) {
            handleNumber("0");
        }

        else if (viewId == R.id.button16) {
            handleNumber("1");
        }

        else if (viewId == R.id.button17) {
            handleNumber("2");
        }

        else if (viewId == R.id.button18) {
            handleNumber("3");
        }

        else if (viewId == R.id.button13) {
            handleNumber("4");
        }

        else if (viewId == R.id.button14) {
            handleNumber("5");
        }

        else if (viewId == R.id.button15) {
            handleNumber("6");
        }

        else if (viewId == R.id.button9) {
            handleNumber("7");
        }

        else if (viewId == R.id.button10) {
            handleNumber("8");
        }

        else if (viewId == R.id.button11) {
            handleNumber("9");
        }

        else if (viewId == R.id.button5) {
            subtend.setText("");
            result.setText("0");
            progressBar.setProgress(0);
            percent.setText("0%");
        }

        else if (viewId == R.id.imageButton) {

            if (result.getText().length() == 1 || result.getText().equals("Error")) {

                result.setText("0");

            } else {
                result.setText(result.getText().subSequence(0, result.getText().length() - 1));
            }
        }

        else if (viewId == R.id.button7) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "/");
        }

        else if (viewId == R.id.button19) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "+");
        }

        else if (viewId == R.id.button12) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "-");
        }

        else if (viewId == R.id.button8) {

            if ((result.getText().length() == 1 && result.getText().equals("0")) || result.getText().equals("Error")) {
                return;
            }

            result.setText(result.getText() + "*");
        }

        else if (viewId == R.id.button21) {

            if ((result.getText().length() == 1 && result.getText().equals("0"))) {

                result.setText(result.getText() + ".");
                handler.post(update);
                return;
            }

            if (result.getText().charAt(0) == '=' || result.getText().equals("Error")) {
                result.setText("0.");
                handler.post(update);
                return;
            }

            result.setText(result.getText() + ".");
        }

        else if (viewId == R.id.button22) {

            String sub = result.getText().toString();

            if (sub.isEmpty()) {
                return;
            }

            try {
                double answer = calculate(sub);

                colorExpression(subtend, sub);
                result.setText("=" + String.valueOf(answer));

                handler.removeCallbacks(update);

            } catch (Exception e) {
                result.setText("Error");
            }
        }
    }

    private void handleNumber(String number) {

        if ((result.getText().length() == 1 && result.getText().equals("0"))
                || result.getText().equals("Error")) {

            result.setText(number);
            handler.post(update);
            return;
        }

        if (result.getText().charAt(0) == '=') {

            result.setText(number);

            progressBar.setProgress(0);
            percent.setText("0%");
            subtend.setText("");

            handler.post(update);
            return;
        }

        result.setText(result.getText() + number);
    }

    private int precedence(char operator) {

        if (operator == '*' || operator == '/') {
            return 2;
        }

        if (operator == '+' || operator == '-') {
            return 1;
        }

        return 0;
    }

    private double applyOperation(double a, double b, char operator) {

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
    private double calculate(String expression) {

        Stack<Double> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        int i = 0;

        while (i < expression.length()) {

            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {

                StringBuilder number = new StringBuilder();

                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i))
                                || expression.charAt(i) == '.')) {

                    number.append(expression.charAt(i));
                    i++;
                }

                numberStack.push(Double.parseDouble(number.toString()));
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/') {

                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {

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

    private void colorExpression(TextView textView, String expression) {

        SpannableString spannable = new SpannableString(expression);

        int operatorColor = Color.parseColor("#5689F5");

        for (int i = 0; i < expression.length(); i++) {

            char c = expression.charAt(i);

            if (c == '+' || c == '-' || c == '*' || c == '/') {

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
}

