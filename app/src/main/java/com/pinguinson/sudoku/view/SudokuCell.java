package com.pinguinson.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.pinguinson.sudoku.GameEngine;
import com.pinguinson.sudoku.R;

import java.util.Arrays;

/**
 * Created by pinguinson on 8/17/15.
 */
public class SudokuCell extends TextView {

    private int x;
    private int y;
    private boolean isSelected;

    public SudokuCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.MONOSPACE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }

    public void setNumber(int number) {
        GameEngine.getInstance().setCell(x, y, number);
    }

    public void setSelection(boolean selected) {
        this.isSelected = selected;
    }

    public boolean toggleAvailable(int number) {
        setNumber(0);
        return GameEngine.getInstance().toggleAvailable(x, y, number);
    }

    public boolean isAvailable(int number) {
        return GameEngine.getInstance().isAvailable(x, y, number);
    }

    public void linkCell(int x, int y) {
        this.x = x;
        this.y = y;
        GameEngine.getInstance().linkCell(x, y, this);
    }

    public int getNumber() {
        return GameEngine.getInstance().getNumber(x, y);
    }

    public int getXValue() {
        return x;
    }

    public int getYValue() {
        return y;
    }

    public void clearCell() {
        setNumber(0);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (getNumber() == 0) {
            setFontsSmall();
            setCellTextSmall();
        } else {
            setFontsMain();
            setCellTextMain();
        }
        setTextColor();
        setBackground();
    }

    private void setFontsMain() {
        setLines(1);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.cell_main_text_size));
    }

    private void setFontsSmall() {
        setLines(3);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.cell_small_text_size));
    }

    private void setCellTextMain() {
        String cellText = " ";
        if (getNumber() != 0) {
            cellText = String.valueOf(getNumber());
        }
        setText(cellText);
    }

    private void setCellTextSmall() {
        String[] values = new String[9];
        Arrays.fill(values, " ");
        boolean[] available = GameEngine.getInstance().getAvailableNumbers(x, y);
        for (int i = 0; i < 9; i++) {
            if (available[i])
                values[i] = String.valueOf(i + 1);
        }
        String cellText = values[0] + " " + values[1] + " " + values[2] + "\n" +
                values[3] + " " + values[4] + " " + values[5] + "\n" +
                values[6] + " " + values[7] + " " + values[8];
        this.setText(cellText);
    }

    private void setTextColor() {
        if (!GameEngine.getInstance().isMutable(x, y)) {
            setTextColor(getResources().getColor(R.color.font_color_immutable));
        } else {
            setTextColor(getResources().getColor(R.color.font_color_mutable));
        }
    }

    private void setBackground() {
        int blockColumn = x / 3;
        int blockRow = y / 3;
        if (isSelected) {
            setBackgroundResource(R.drawable.cell_shape_selected);
        } else {
            if ((blockColumn + blockRow) % 2 == 0) {
                setBackgroundResource(R.drawable.cell_shape_1);
            } else {
                setBackgroundResource(R.drawable.cell_shape_2);
            }
        }
    }
}

