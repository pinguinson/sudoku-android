package com.pinguinson.sudoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pinguinson.sudoku.generator.Difficulty;
import com.pinguinson.sudoku.generator.SudokuBoard;
import com.pinguinson.sudoku.generator.SudokuSolver;
import com.pinguinson.sudoku.generator.TaskGenerator;
import com.pinguinson.sudoku.view.SudokuCell;

import java.io.FileOutputStream;

public class GameActivity extends Activity {

    Context context;
    SudokuCell currentCell;

    private static final int[] BUTTON_MAIN_IDS = {
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

    private static final int[] BUTTON_SMALL_IDS = {
            R.id.setAvailable1,
            R.id.setAvailable2,
            R.id.setAvailable3,
            R.id.setAvailable4,
            R.id.setAvailable5,
            R.id.setAvailable6,
            R.id.setAvailable7,
            R.id.setAvailable8,
            R.id.setAvailable9,
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
        setButtonListeners();
    }

    private void setButtonListeners() {
        setButtonListenersMainGrid();
        setButtonListenersSmallGrid();
    }

    private void setButtonListenersSmallGrid() {
        View.OnClickListener smallGridListener = new View.OnClickListener() {
            public void onClick(View v) {
                Integer tag = ((Integer) v.getTag());
                if (currentCell != null) {
                    currentCell.toggleAvailable(tag - 1);
                }
            }
        };
        for (int i = 0; i < BUTTON_SMALL_IDS.length; i++) {
            Button smallGridButton = (Button) findViewById(BUTTON_SMALL_IDS[i]);
            smallGridButton.setOnClickListener(smallGridListener);
            smallGridButton.setTag(i + 1);
        }
    }

    private void setButtonListenersMainGrid() {
        View.OnClickListener mainGridListener = new View.OnClickListener() {
            public void onClick(View v) {
                Integer tag = ((Integer) v.getTag());
                if (currentCell != null) {
                    if (tag == currentCell.getNumber()) {
                        currentCell.clearCell();
                    } else {
                        currentCell.setNumber(tag);
                    }
                }
            }
        };
        for (int i = 0; i < BUTTON_MAIN_IDS.length; i++) {
            Button mainGridButton = (Button) findViewById(BUTTON_MAIN_IDS[i]);
            mainGridButton.setOnClickListener(mainGridListener);
            mainGridButton.setTag(i + 1);
        }
    }

    private void startNewGame() {
        SudokuBoard generatedTask = new TaskGenerator(9).generateTask(Difficulty.HARD);
        int[][] task = generatedTask.getBoard();
        int[][] solution = SudokuSolver.solve(generatedTask).getBoard();
        GameEngine.getInstance().setGrids(solution, task);
        saveGame();
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
            currentCell.setSelection(true);
        }
    }

    public void deselectCell() {
        if (getCurrentCell() != null) {
            getCurrentCell().setSelection(false);
            currentCell = null;
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
    }
}
