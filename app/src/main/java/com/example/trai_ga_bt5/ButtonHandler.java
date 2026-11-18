package com.example.trai_ga_bt5;

import android.view.View;

public class ButtonHandler implements View.OnClickListener {

    private Expression expression;

    public ButtonHandler(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.button20) expression.handleNumber("0");
        else if (viewId == R.id.button16) expression.handleNumber("1");
        else if (viewId == R.id.button17) expression.handleNumber("2");
        else if (viewId == R.id.button18) expression.handleNumber("3");
        else if (viewId == R.id.button13) expression.handleNumber("4");
        else if (viewId == R.id.button14) expression.handleNumber("5");
        else if (viewId == R.id.button15) expression.handleNumber("6");
        else if (viewId == R.id.button9) expression.handleNumber("7");
        else if (viewId == R.id.button10) expression.handleNumber("8");
        else if (viewId == R.id.button11) expression.handleNumber("9");
        else {
            expression.handleAnother(viewId);
        }
    }
}