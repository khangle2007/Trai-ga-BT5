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

    private int currentProgress = 0;
    private boolean isRunning = false;
    private OnProcessCompleteListener listener;

    public Process(ProgressBar progressBar, TextView percent) {

        this.progressBar = progressBar;
        this.percent = percent;

        handler = new Handler(Looper.getMainLooper());

        update = new Runnable() {
            @Override
            public void run() {

                if (currentProgress >= 100) {
                    stop();

                    if (listener != null) {
                        listener.onComplete();
                        return;
                    }
                }

                currentProgress++;

                progressBar.setProgress(currentProgress, false);
                percent.setText(currentProgress + "%");

                handler.postDelayed(this, 100);
            }
        };
    }

    public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnProcessCompleteListener {
        void onComplete();
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void start() {
        handler.removeCallbacks(update);
        isRunning = true;
        handler.post(update);
    }

    public void stop() {
        handler.removeCallbacks(update);
        isRunning = false;
    }

    public void reset() {
        stop();

        currentProgress = 0;

        progressBar.setProgress(0, false);
        percent.setText("0%");
    }

    public void restoreProgress(int progress) {
        currentProgress = Math.max(0, Math.min(progress, 100));

        progressBar.setProgress(currentProgress);
        percent.setText(currentProgress + "%");
    }

}