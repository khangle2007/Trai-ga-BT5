
package com.example.trai_ga_bt5;

import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = 35)
public class ProcessTest {

    private ProgressBar progressBar;
    private TextView percent;
    private Process process;

    @Before
    public void setUp() {
        progressBar = new ProgressBar(RuntimeEnvironment.getApplication(), null, android.R.attr.progressBarStyleHorizontal);

        percent = new TextView(RuntimeEnvironment.getApplication());

        progressBar.setIndeterminate(false);
        progressBar.setMax(100);

        percent.setText("0%");

        process = new Process(progressBar, percent);
    }

    @Test
    public void testStart() {
        process.start();

        Shadows.shadowOf(Looper.getMainLooper()).idle();

        assertEquals(1, progressBar.getProgress());
        assertEquals("1%", percent.getText().toString());
    }

    @Test
    public void testStop() {
        process.start();

        Shadows.shadowOf(Looper.getMainLooper()).idle();

        process.stop();

        Shadows.shadowOf(Looper.getMainLooper()).idleFor(500, TimeUnit.MILLISECONDS);

        assertEquals(1, progressBar.getProgress());
        assertEquals("1%", percent.getText().toString());
    }

    @Test
    public void testReset() {
        progressBar.setProgress(50);
        percent.setText("50%");

        process.reset();

        assertEquals(0, progressBar.getProgress());
        assertEquals("0%", percent.getText().toString());
    }

    @Test
    public void testProcessCompletion() {
        process.start();

        Shadows.shadowOf(Looper.getMainLooper()).idleFor(11, TimeUnit.SECONDS);

        assertEquals(0, progressBar.getProgress());
        assertEquals("0%", percent.getText().toString());
    }
}
