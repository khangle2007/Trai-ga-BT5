package com.example.trai_ga_bt5;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{
    private View[] buttons;
    private TextView subtend, result, percent;
    private ProgressBar progressBar;

    private Handler handler;
    private Runnable update;
    private ButtonHandler buttonHandler;
    private Process process;
    private Expression expression;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("subtend", subtend.getText().toString());
        outState.putString("result", result.getText().toString());
        outState.putBoolean("isDegree", buttonHandler.isDegree());
        outState.putString("expression", expression.getExpression());
        outState.putInt("progress", progressBar.getProgress());
        outState.putBoolean("isRunning", process.isRunning());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttons = new View[]{
                findViewById(R.id.e),
                findViewById(R.id.sin),
                findViewById(R.id.mu),
                findViewById(R.id.deg),

                findViewById(R.id.number0),
                findViewById(R.id.number1),
                findViewById(R.id.number2),
                findViewById(R.id.number3),
                findViewById(R.id.number4),
                findViewById(R.id.number5),
                findViewById(R.id.number6),
                findViewById(R.id.number7),
                findViewById(R.id.number8),
                findViewById(R.id.number9),

                findViewById(R.id.delete),
                findViewById(R.id.Ac),
                findViewById(R.id.devide),
                findViewById(R.id.plus),
                findViewById(R.id.minus),
                findViewById(R.id.multi),
                findViewById(R.id.decimal),
                findViewById(R.id.submit)
        };

        subtend = findViewById(R.id.subtend);
        result = findViewById(R.id.result);

        progressBar = findViewById(R.id.progressBar);
        percent = findViewById(R.id.percent);

        process = new Process(progressBar, percent);

        expression = new Expression();
        expression.setExpression(result.getText().toString());

        buttonHandler = new ButtonHandler(expression, this, process);

        if (savedInstanceState != null) {

            expression.setExpression(
                    savedInstanceState.getString("expression", "0")
            );

            buttonHandler.setDegree(
                    savedInstanceState.getBoolean("isDegree", false)
            );

            int savedProgress = savedInstanceState.getInt("progress", 0);

            process.restoreProgress(savedProgress);

            if (savedInstanceState.getBoolean("isRunning", false)) {
                process.start();
            }

            result.setText(expression.getExpression());

            result.setText(savedInstanceState.getString(
                    "result", expression.getExpression()
            ));

            subtend.setText(savedInstanceState.getString("subtend", ""));
        }



        for (View button : buttons) {

            button.setOnClickListener(v -> {

                String previousExpression = expression.getExpression();

                buttonHandler.onClick(v);

                String currentExpression = expression.getExpression();

                TextView deg = findViewById(R.id.deg);

                if(buttonHandler.isDegree())
                {
                    deg.setText("rad");
                }
                else
                {
                    deg.setText("deg");
                }


                if (v.getId() == R.id.submit
                        && currentExpression.startsWith("=")) {

                    subtend.setText(previousExpression);
                    colorExpression(subtend, previousExpression);
                }


                result.setText(currentExpression);
                colorExpression(result, currentExpression);
            });
        }


        colorExpression(result, expression.getExpression());
        updateExpression(subtend);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets imeInset = insets.getInsets(WindowInsetsCompat.Type.ime());

                    result.setTranslationY(-imeInset.bottom);

                    return insets;
                }
        );
    }

    public void colorExpression(TextView textView, String expression) {

        SpannableString spannable = new SpannableString(expression);

        if (expression.equals("Divide by zero")
                || expression.equals("Invalid operand")
                || expression.equals("Expression invalid")
                || expression.equals("Error")) {

            textView.setText(spannable);
            return;
        }

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

    @Override
    protected void onStop() {
        super.onStop();

        if (process != null) {
            process.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (process != null) {
            process.stop();
        }
    }
}