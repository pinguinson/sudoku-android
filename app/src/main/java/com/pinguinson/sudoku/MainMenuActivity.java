package com.pinguinson.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainMenuActivity extends Activity {
    public static final String DIFFICULTY = "difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_NORMAL = 1;
    public static final int DIFFICULTY_HARD = 2;
    public static final int DIFFICULTY_INSANE = 3;

    public static final String MODE = "game mode";
    public static final int MODE_CONTINUE = 0;
    public static final int MODE_NEW = 1;

    public static final String LOAD = "loaded file";
    public static final String SAVE_FILE = "savedGame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    //TODO: make it possible to continue game
    public void continueGame(View view) {
        String filename = SAVE_FILE;
        File file = new File(getFilesDir(), filename);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            Log.d("LOAD", "failed to load saved game");
            return;
        }
        String load = text.toString();
        Log.d("LOAD", "loaded saved game");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MODE, MODE_CONTINUE);
        intent.putExtra(LOAD, load);
        startActivity(intent);

    }

    //TODO: choose difficulty level
    public void startNewGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(DIFFICULTY, DIFFICULTY_EASY);
        intent.putExtra(MODE, MODE_NEW);
        startActivity(intent);
    }
}
