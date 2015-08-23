package com.pinguinson.sudoku.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pinguinson.sudoku.GameActivity;
import com.pinguinson.sudoku.GameEngine;

/**
 * Created by pinguinson on 8/17/15.
 */
public class SudokuGridView extends GridView {

    public SudokuGridView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SudokuGridViewAdapter gridViewAdapter = new SudokuGridViewAdapter(context);
        setAdapter(gridViewAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SudokuCell cell = (SudokuCell) view;

                //Checks if cell is mutable
                boolean isMutable = GameEngine.getInstance().isMutable(cell.getXValue(), cell.getYValue());
                GameActivity gameActivity = (GameActivity) context;

                //deselect if clicked immutable or selected cell
                if (!isMutable || gameActivity.getCurrentCell() == cell) {
                    gameActivity.deselectCell();
                    return;
                }
                gameActivity.setCurrentCell(cell);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
