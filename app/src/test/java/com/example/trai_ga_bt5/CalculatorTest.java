
package com.example.trai_ga_bt5;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void testAddition() {
        assertEquals(8, Calculator.calculate("5+3"), 0.000001);
    }

    @Test
    public void testSubtraction() {
        assertEquals(6, Calculator.calculate("10-4"), 0.000001);
    }

    @Test
    public void testMultiplication() {
        assertEquals(15, Calculator.calculate("5*3"), 0.000001);
    }

    @Test
    public void testDivision() {
        assertEquals(5, Calculator.calculate("10/2"), 0.000001);
    }

    @Test
    public void testOperatorPrecedence() {
        assertEquals(14, Calculator.calculate("2+3*4"), 0.000001);
    }

    @Test
    public void testNegativeNumber() {
        assertEquals(-2, Calculator.calculate("-5+3"), 0.000001);
    }

    @Test
    public void testNegativeMultiplication() {
        assertEquals(-10, Calculator.calculate("5*-2"), 0.000001);
    }

    @Test
    public void testDoubleNumber() {
        assertEquals(4, Calculator.calculate("2.5+1.5"), 0.000001);
    }

    @Test
    public void testEulerConstant() {
        assertEquals(Math.E, Calculator.calculate("e"), 0.000001);
    }

    @Test
    public void testPiConstant() {
        assertEquals(Math.PI, Calculator.calculate("μ"), 0.000001);
    }

    @Test
    public void testImplicitMultiplication() {
        assertEquals(2 * Math.E, Calculator.calculate("2e"), 0.000001);
    }

    @Test
    public void testImplicitMultiplicationWithPi() {
        assertEquals(2 * Math.PI, Calculator.calculate("2μ"), 0.000001);
    }

    @Test
    public void testSinInRadian() {
        assertEquals(Math.sin(1), Calculator.calculate("sin1"), 0.000001);
    }

    @Test
    public void testSinInDegree() {
        assertEquals(0.5, Calculator.calculate("sin30", true), 0.000001);
    }

    @Test
    public void testSin90Degree() {
        assertEquals(1, Calculator.calculate("sin90", true), 0.000001);
    }

    @Test
    public void testSin180Degree() {
        assertEquals(0, Calculator.calculate("sin180", true), 0.000001);
    }

    @Test
    public void testSinWithAddition() {
        assertEquals(2.5, Calculator.calculate("2+sin30", true), 0.000001);
    }

    @Test
    public void testImplicitMultiplicationWithSin() {
        assertEquals(1, Calculator.calculate("2sin30", true), 0.000001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        Calculator.calculate("5/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingOperand() {
        Calculator.calculate("5+");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpression() {
        Calculator.calculate("5*/2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultipleDecimalPoints() {
        Calculator.calculate("2.5.5");
    }

    @Test
    public void testSinWithAdditionAfter() {
        assertEquals(5.5, Calculator.calculate("sin30+5", true), 0.000001);
    }
}
