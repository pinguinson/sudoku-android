package com.pinguinson.sudoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pinguinson.sudoku.view.SudokuCell;

import java.io.FileOutputStream;

public class GameActivity extends Activity {

    Context context;
    SudokuCell currentCell;

    private static final int[] BUTTON_IDS = {
            R.id.setCellValue1,
            R.id.setCellValue2,
            R.id.setCellValue3,
            R.id.setCellValue4,
            R.id.setCellValue5,
            R.id.setCellValue6,
            R.id.setCellValue7,
            R.id.setCellValue8,
            R.id.setCellValue9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        currentCell = null;
        Intent intent = getIntent();
        int mode = intent.getIntExtra(MainMenuActivity.MODE, MainMenuActivity.MODE_NEW);
        switch (mode) {
            case MainMenuActivity.MODE_CONTINUE:
                continueGame(intent.getStringExtra(MainMenuActivity.LOAD));
                break;
            case MainMenuActivity.MODE_NEW:
                startNewGame();
                break;
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        View.OnClickListener myListener = new View.OnClickListener() {
            public void onClick(View v) {
                Integer tag = ((Integer) v.getTag());
                if (currentCell != null) {
                    if (tag == currentCell.getNumber()) {
                        currentCell.clearCell();
                    } else {
                        currentCell.setNumber(tag);
                    }
                    updateButtons();
                }
            }
        };
        for(int i = 0; i < BUTTON_IDS.length; i++) {
            Button button = (Button)findViewById(BUTTON_IDS[i]);
            button.setOnClickListener(myListener);
            button.setTag(i + 1);
        }
    }

    private void startNewGame() {
        int[][] solution = SudokuGenerator.generateSolution();
        int[][] task = SudokuGenerator.generateTask();
        GameEngine.getInstance().setGrids(solution, task);
        GameEngine.getInstance().setGameActivity(this);
    }

    private void continueGame(String load) {
        if (load == null) {
            Log.e("LOAD_ERROR", "failed to load saved game");
        } else {
            Log.e("LOAD_SUCCESS", "successfully loaded saved game");
            GameEngine.getInstance().loadGrids(load);
            GameEngine.getInstance().setGameActivity(this);
        }
    }

    public SudokuCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(SudokuCell currentCell) {
        deselectCell();
        this.currentCell = currentCell;
        if (currentCell != null) {
            updateButtons();
            currentCell.setSelection(true);
        }
    }

    public void deselectCell() {
        if (getCurrentCell() != null) {
            getCurrentCell().setSelection(false);
            currentCell = null;
            releaseButtons();
        }
    }

    private void updateButtons() {
        releaseButtons();
        int currentCellNumber = getCurrentCell().getNumber();
        if (currentCellNumber > 0) {
            int buttonId = BUTTON_IDS[currentCellNumber - 1];
            Button selectedButton = (Button) findViewById(buttonId);
            selectedButton.setPressed(true);
            Log.d("BUTTON", "button #" + currentCellNumber + " pressed");
        }
    }

    private void releaseButtons() {
        for (int i = 0; i < 9; i++) {
            int buttonId = BUTTON_IDS[i];
            Button selectedButton = (Button) findViewById(buttonId);
            selectedButton.setPressed(false);
        }
    }

    public void saveGame() {
        String filename = MainMenuActivity.SAVE_FILE;
        String save = GameEngine.getInstance().saveGrids();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(save.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("SAVE", "successfully saved current game");
    }
}
