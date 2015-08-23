package com.pinguinson.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.pinguinson.sudoku.GameEngine;
import com.pinguinson.sudoku.R;

/**
 * Created by pinguinson on 8/17/15.
 */
public class SudokuCell extends TextView {

    private int number;
    private int x;
    private int y;
    private boolean isValid;
    private boolean isSelected;
    private boolean showIfValid;
    Typeface handwritten;

    public static final int TEXT_STYLE_NORMAL = 0;
    public static final int TEXT_STYLE_BOLD = 1;
    public static final int TEXT_STYLE_ITALIC = 2;

    public SudokuCell(Context context, AttributeSet attrs) {
        super(context, attrs);

        handwritten = Typeface.createFromAsset(context.getAssets(), "fonts/leckerli-one.ttf");
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
        this.number = number;
        sendUpdatedValueToGameEngine();
        this.isValid = GameEngine.getInstance().testCell(x, y);
        invalidate();
    }

    private void sendUpdatedValueToGameEngine() {
        GameEngine.getInstance().setCell(x, y, number);
    }

    public void setSelection(boolean selected) {
        this.isSelected = selected;
        invalidate();
    }

    public void linkCell(int x, int y) {
        setX(x);
        setY(y);
        this.setNumber(GameEngine.getInstance().getCurrentGrid()[x][y]);
        GameEngine.getInstance().linkCell(x, y, this);
    }

    public int getNumber() {
        return number;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXValue() {
        return x;
    }

    public int getYValue() {
        return y;
    }

    public void setShowIfValid(boolean showIfValid) {
        this.showIfValid = showIfValid;
        invalidate();
    }

    public void clearCell() {
        this.setNumber(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFonts();
        setCellText();
        setBackground();
    }

    private void setFonts() {
        if (!GameEngine.getInstance().isMutable(x, y)) {
            this.setTypeface(null, TEXT_STYLE_BOLD);
            this.setTextColor(getResources().getColor(R.color.font_color_immutable));
        } else {
            this.setTypeface(handwritten, TEXT_STYLE_NORMAL);
            if (isSelected) {
                this.setTextColor(getResources().getColor(R.color.font_color_selected));
            } else {
                if (showIfValid) {
                    if (isValid) {
                        this.setTextColor(getResources().getColor(R.color.font_color_mutable));
                    } else {
                        this.setTextColor(getResources().getColor(R.color.font_color_invalid));
                    }
                } else {
                    this.setTextColor(getResources().getColor(R.color.font_color_mutable));
                }
            }
        }
    }

    private void setCellText() {
        String cellText = " ";
        if (number != 0) {
            cellText = String.valueOf(number);
        }
        if (number == 0 && isSelected) {
            cellText = "?";
        }
        this.setGravity(Gravity.CENTER);
        this.setText(cellText);
    }

    private void setBackground() {
        int blockColumn = x / 3;
        int blockRow = y / 3;
        if ((blockColumn + blockRow) % 2 == 0) {
            this.setBackgroundResource(R.drawable.cell_shape_1);
        } else {
            this.setBackgroundResource(R.drawable.cell_shape_2);
        }
    }
}

