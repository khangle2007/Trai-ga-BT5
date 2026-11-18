
package com.example.trai_ga_bt5;

import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Process {

    private final Handler handler;
    private final Runnable update;

    private final ProgressBar progressBar;
    private final TextView percent;
    private final TextView subtend;
    private final TextView result;

    public Process(ProgressBar progressBar, TextView percent, TextView subtend, TextView result) {

        this.progressBar = progressBar;
        this.percent = percent;
        this.subtend = subtend;
        this.result = result;

        handler = new Handler(Looper.getMainLooper());

        update = new Runnable() {
            @Override
            public void run() {

                int current = progressBar.getProgress();

                if (current < 100) {

                    progressBar.setProgress(current + 1);

                    int num = Integer.parseInt(
                            percent.getText()
                                    .subSequence(0, percent.getText().length() - 1)
                                    .toString()
                    );

                    percent.setText(num + 1 + "%");

                    handler.postDelayed(this, 100);

                } else {

                    stop();
                    reset();

                    subtend.setText("");
                    result.setText("0");
                }
            }
        };
    }

    public void start() {
        handler.removeCallbacks(update);
        handler.post(update);
    }

    public void stop() {
        handler.removeCallbacks(update);
    }

    public void reset() {
        stop();
        progressBar.setProgress(0);
        percent.setText("0%");
        subtend.setText("");
        result.setText("0");
    }
}
