package com.pinguinson.sudoku.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pinguinson.sudoku.engine.GameEngine;
import com.pinguinson.sudoku.MainActivity;
import com.pinguinson.sudoku.R;
import com.pinguinson.sudoku.engine.TaskGenerator;
import com.pinguinson.sudoku.cell.SudokuCell;

import java.io.FileOutputStream;

/**
 * Created by pinguinson on 8/25/15.
 */
public class GameFragment extends Fragment {

    View thisView;
    SudokuCell currentCell;
    MainActivity mainActivity;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_game, null);
        setButtonListeners();
        this.mainActivity = (MainActivity) getActivity();
        return thisView;
    }

    private void setButtonListeners() {
        setButtonListenersMainGrid();
        setButtonListenersSmallGrid();
    }

    private void setButtonListenersSmallGrid() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }

                Integer tag = ((Integer) v.getTag());
                if (currentCell != null) {
                    releaseMainGrid();
                    setPressedSmallButton(tag, currentCell.toggleAvailable(tag));
                }
                return true;
            }
        };
        for (int i = 0; i < BUTTON_SMALL_IDS.length; i++) {
            Button smallGridButton = (Button) thisView.findViewById(BUTTON_SMALL_IDS[i]);
            smallGridButton.setOnTouchListener(listener);
            smallGridButton.setTag(i + 1);
        }
    }

    private void setButtonListenersMainGrid() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }

                Integer id = ((Integer) v.getTag());
                if (currentCell != null) {
                    releaseSmallGrid();
                    if (id == currentCell.getNumber()) {
                        releaseMainButton();
                        currentCell.clearCell();
                    } else {
                        releaseMainButton();
                        currentCell.setNumber(id);
                        pressMainButton();
                    }
                }
                return true;
            }
        };
        for (int i = 0; i < BUTTON_MAIN_IDS.length; i++) {
            Button mainGridButton = (Button) thisView.findViewById(BUTTON_MAIN_IDS[i]);
            mainGridButton.setOnTouchListener(listener);
            mainGridButton.setTag(i + 1);
        }
    }

    private Button getSmallButtonByNumber(int number) {
        return (Button) thisView.findViewById(BUTTON_SMALL_IDS[number - 1]);
    }

    private Button getMainButtonByNumber(int number) {
        return (Button) thisView.findViewById(BUTTON_MAIN_IDS[number - 1]);
    }

    private void setPressedSmallButton(int number, boolean isPressed) {
        getSmallButtonByNumber(number).setPressed(isPressed);
    }

    private void releaseMainButton() {
        if (currentCell.getNumber() != 0) {
            getMainButtonByNumber(currentCell.getNumber()).setPressed(false);
        }
    }

    private void pressMainButton() {
        getMainButtonByNumber(currentCell.getNumber()).setPressed(true);
    }

    public void startNewGame(int level) {
        TaskGenerator taskGenerator = new TaskGenerator();
        int[][] task = taskGenerator.generateTask(level);
        int[][] solution = taskGenerator.generateSolution(level);
        GameEngine.getInstance().setGrids(solution, task);
        saveGame();
        GameEngine.getInstance().setGameFragment(this);
    }

    public void saveGame() {
        String filename = MainActivity.SAVE_FILE;
        String save = GameEngine.getInstance().saveGrids();
        FileOutputStream outputStream;

        try {
            outputStream = mainActivity.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(save.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void continueGame(String load) {
        if (load == null) {
            Log.e("LOAD_ERROR", "failed to load saved game");
        } else {
            Log.e("LOAD_SUCCESS", "successfully loaded saved game");
            GameEngine.getInstance().loadGrids(load);
            GameEngine.getInstance().setGameFragment(this);
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
            for (int i = 0; i < BUTTON_MAIN_IDS.length; i++) {
                Button mainGridButton = (Button) thisView.findViewById(BUTTON_MAIN_IDS[i]);
                mainGridButton.setPressed(i + 1 == currentCell.getNumber());
            }
            for (int i = 0; i < BUTTON_SMALL_IDS.length; i++) {
                Button smallGridButton = (Button) thisView.findViewById(BUTTON_SMALL_IDS[i]);
                smallGridButton.setPressed(currentCell.isAvailable(i + 1));
            }
        }
    }

    public void deselectCell() {
        if (getCurrentCell() != null) {
            getCurrentCell().setSelection(false);
            currentCell = null;
        }
        releaseMainGrid();
        releaseSmallGrid();
    }

    private void releaseMainGrid() {
        for (int BUTTON_MAIN_ID : BUTTON_MAIN_IDS) {
            Button mainGridButton = (Button) thisView.findViewById(BUTTON_MAIN_ID);
            mainGridButton.setPressed(false);
        }
    }

    private void releaseSmallGrid() {
        for (int BUTTON_SMALL_ID : BUTTON_SMALL_IDS) {
            Button smallGridButton = (Button) thisView.findViewById(BUTTON_SMALL_ID);
            smallGridButton.setPressed(false);
        }
    }
}
