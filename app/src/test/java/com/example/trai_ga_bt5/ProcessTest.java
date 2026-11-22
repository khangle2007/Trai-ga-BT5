
package com.example.trai_ga_bt5;

import static org.junit.Assert.*;

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

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = 35)
public class ProcessTest {

    private ProgressBar progressBar;
    private TextView percent;
    private Process process;

    private boolean completed;

    @Before
    public void setUp() {
        progressBar = new ProgressBar(
                RuntimeEnvironment.getApplication(),
                null,
                android.R.attr.progressBarStyleHorizontal
        );

        percent = new TextView(RuntimeEnvironment.getApplication());

        progressBar.setIndeterminate(false);
        progressBar.setMax(100);

        percent.setText("0%");

        process = new Process(progressBar, percent);

        completed = false;

        process.setOnProcessCompleteListener(() -> completed = true);
    }

    @Test
    public void testStart() {
        process.start();

        assertTrue(process.isRunning());

        Shadows.shadowOf(Looper.getMainLooper()).idle();

        assertEquals(1, progressBar.getProgress());
        assertEquals("1%", percent.getText().toString());
    }

    @Test
    public void testStop() {
        process.start();

        Shadows.shadowOf(Looper.getMainLooper()).idle();

        process.stop();

        assertFalse(process.isRunning());

        Shadows.shadowOf(Looper.getMainLooper())
                .idleFor(500, TimeUnit.MILLISECONDS);

        assertEquals(1, progressBar.getProgress());
        assertEquals("1%", percent.getText().toString());
    }

    @Test
    public void testReset() {
        process.restoreProgress(50);

        process.reset();

        assertEquals(0, progressBar.getProgress());
        assertEquals("0%", percent.getText().toString());
        assertFalse(process.isRunning());
    }

    @Test
    public void testRestoreProgress() {
        process.restoreProgress(45);

        assertEquals(45, progressBar.getProgress());
        assertEquals("45%", percent.getText().toString());
        assertFalse(process.isRunning());
    }

    @Test
    public void testRestoreProgressLimit() {
        process.restoreProgress(150);

        assertEquals(100, progressBar.getProgress());
        assertEquals("100%", percent.getText().toString());

        process.restoreProgress(-10);

        assertEquals(0, progressBar.getProgress());
        assertEquals("0%", percent.getText().toString());
    }

    @Test
    public void testProcessCompletion() {
        process.start();

        Shadows.shadowOf(Looper.getMainLooper())
                .idleFor(11, TimeUnit.SECONDS);

        assertTrue(completed);
        assertFalse(process.isRunning());
        assertEquals(100, progressBar.getProgress());
        assertEquals("100%", percent.getText().toString());
    }

    @Test
    public void testStartAfterRestoreProgress() {
        process.restoreProgress(50);

        process.start();

        Shadows.shadowOf(Looper.getMainLooper()).idle();

        assertEquals(51, progressBar.getProgress());
        assertEquals("51%", percent.getText().toString());
        assertTrue(process.isRunning());
    }
}

