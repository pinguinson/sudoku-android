package com.pinguinson.sudoku;

import android.app.Application;

import com.flurry.android.FlurryAgent;

/**
 * Created by pinguinson on 8/25/15.
 */
public class SudokuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // configure Flurry
        FlurryAgent.setLogEnabled(true);

        // init Flurry
        FlurryAgent.init(this, FlurryConstants.API_KEY);
    }
}