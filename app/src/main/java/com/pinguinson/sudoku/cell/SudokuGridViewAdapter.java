package com.pinguinson.sudoku.cell;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pinguinson.sudoku.R;

/**
 * Created by pinguinson on 8/17/15.
 */
public class SudokuGridViewAdapter extends BaseAdapter {

    private Context context;

    public SudokuGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SudokuCell cell = (SudokuCell) convertView;
        if (cell == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            cell = (SudokuCell) inflater.inflate(R.layout.cell, parent, false);
            int x = position % 9;
            int y = position / 9;
            cell.linkCell(x, y);
        }
        return cell;
    }
}
