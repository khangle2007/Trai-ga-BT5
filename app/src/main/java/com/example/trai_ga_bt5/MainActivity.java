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

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
    private View[] buttons;
    private TextView subtend, result, percent;
    private ProgressBar progressBar;

    private Handler handler;
    private Runnable update;

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

                findViewById(R.id.button20),
                findViewById(R.id.button16),
                findViewById(R.id.button17),
                findViewById(R.id.button18),
                findViewById(R.id.button13),
                findViewById(R.id.button14),
                findViewById(R.id.button15),
                findViewById(R.id.button9),
                findViewById(R.id.button10),
                findViewById(R.id.button11),

                findViewById(R.id.delete),
                findViewById(R.id.Ac),
                findViewById(R.id.devide),
                findViewById(R.id.plus),
                findViewById(R.id.minus),
                findViewById(R.id.multi),
                findViewById(R.id.decimal),
                findViewById(R.id.submit)
        };

        subtend = findViewById(R.id.textView3);
        result = findViewById(R.id.textView4);

        progressBar = findViewById(R.id.progressBar);
        percent = findViewById(R.id.textView);

        Process process = new Process(progressBar, percent, subtend, result);
        Expression expression = new Expression(result, subtend, process);

        for (View button : buttons) {
            button.setOnClickListener(new ButtonHandler(expression));
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets imeInset = insets.getInsets(WindowInsetsCompat.Type.ime());

            result.setTranslationY(-imeInset.bottom);

            return insets;
        });

        subtend.setText("");
        result.setText("0");

    }
}