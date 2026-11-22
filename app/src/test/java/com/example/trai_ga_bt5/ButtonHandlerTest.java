
package com.example.trai_ga_bt5;

import static org.junit.Assert.*;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35)
public class ButtonHandlerTest {

    private Expression expression;
    private ButtonHandler buttonHandler;
    private Process process;
    private ProgressBar progressBar;
    private TextView percent;

    @Before
    public void setUp() {
        expression = new Expression();

        progressBar = new ProgressBar(
                RuntimeEnvironment.getApplication(),
                null,
                android.R.attr.progressBarStyleHorizontal
        );

        progressBar.setMax(100);
        progressBar.setIndeterminate(false);

        percent = new TextView(RuntimeEnvironment.getApplication());
        percent.setText("0%");

        process = new Process(progressBar, percent);

        buttonHandler = new ButtonHandler(
                expression,
                RuntimeEnvironment.getApplication(),
                process
        );
    }

    @After
    public void tearDown() {
        // Dừng Handler sau mỗi test
        process.stop();
    }

    @Test
    public void testHandleNumber() {
        buttonHandler.handleNumber("1");
        buttonHandler.handleNumber("2");
        buttonHandler.handleNumber("3");

        assertEquals("123", expression.getExpression());
    }

    @Test
    public void testHandleNumberFromZero() {
        expression.setExpression("0");

        buttonHandler.handleNumber("5");

        assertEquals("5", expression.getExpression());
    }

    @Test
    public void testHandlePlus() {
        expression.setExpression("2");

        buttonHandler.handleOperator(R.id.plus);

        assertEquals("2+", expression.getExpression());
    }

    @Test
    public void testHandleMultiply() {
        expression.setExpression("3");

        buttonHandler.handleOperator(R.id.multi);

        assertEquals("3*", expression.getExpression());
    }

    @Test
    public void testHandleSin() {
        buttonHandler.handleOperator(R.id.sin);

        assertEquals("sin", expression.getExpression());
    }

    @Test
    public void testToggleDegree() {
        assertFalse(buttonHandler.isDegree());

        buttonHandler.handleOperator(R.id.deg);

        assertTrue(buttonHandler.isDegree());

        buttonHandler.handleOperator(R.id.deg);

        assertFalse(buttonHandler.isDegree());
    }

    @Test
    public void testDelete() {
        expression.setExpression("123");

        buttonHandler.handleOperator(R.id.delete);

        assertEquals("12", expression.getExpression());
    }

    @Test
    public void testDeleteLastCharacter() {
        expression.setExpression("5");

        buttonHandler.handleOperator(R.id.delete);

        assertEquals("0", expression.getExpression());
    }

    @Test
    public void testClear() {
        expression.setExpression("123+456");

        buttonHandler.handleOperator(R.id.Ac);

        assertEquals("", expression.getExpression());
        assertEquals(0, progressBar.getProgress());
        assertEquals("0%", percent.getText().toString());
    }

    @Test
    public void testCalculate() {
        expression.setExpression("2+3");

        buttonHandler.handleOperator(R.id.submit);

        assertTrue(expression.getExpression().startsWith("="));

        double answer = Double.parseDouble(
                expression.getExpression().substring(1)
        );

        assertEquals(5.0, answer, 0.000001);
    }

    @Test
    public void testDivideByZero() {
        expression.setExpression("5/0");

        buttonHandler.handleOperator(R.id.submit);

        assertEquals("Divide by zero", expression.getExpression());
    }

    @Test
    public void testInvalidExpression() {
        expression.setExpression("2+");

        buttonHandler.handleOperator(R.id.submit);

        assertEquals(
                "Expression invalid",
                expression.getExpression()
        );
    }

    @Test
    public void testOnClickNumber() {
        View button = new View(RuntimeEnvironment.getApplication());
        button.setId(R.id.number7);

        buttonHandler.onClick(button);

        assertEquals("7", expression.getExpression());
    }

    @Test
    public void testOnClickPlus() {
        expression.setExpression("7");

        View button = new View(RuntimeEnvironment.getApplication());
        button.setId(R.id.plus);

        buttonHandler.onClick(button);

        assertEquals("7+", expression.getExpression());
    }

    @Test
    public void testSetDegree() {
        buttonHandler.setDegree(true);

        assertTrue(buttonHandler.isDegree());

        buttonHandler.setDegree(false);

        assertFalse(buttonHandler.isDegree());
    }
}

