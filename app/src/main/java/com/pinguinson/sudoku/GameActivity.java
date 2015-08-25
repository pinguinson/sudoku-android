package com.pinguinson.sudoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pinguinson.sudoku.view.SudokuCell;

import java.io.FileOutputStream;

public class GameActivity extends Activity {

    Context context;
    SudokuCell currentCell;

    protected SudokuApplication app;

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
                startNewGame(intent.getIntExtra(MainMenuActivity.DIFFICULTY, TaskGenerator.DIFFICULTY_BEGINNER));
                break;
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        app = (SudokuApplication) getApplication();

        AdView mAdView = (AdView) findViewById(R.id.adGame);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setButtonListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, FlurryConstants.API_KEY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    private void setButtonListeners() {
        setButtonListenersMainGrid();
        setButtonListenersSmallGrid();
    }

    private void setButtonListenersSmallGrid() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // show interest in events resulting from ACTION_DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) return true;
                // don't handle event unless its ACTION_UP so "doSomething()" only runs once.
                if (event.getAction() != MotionEvent.ACTION_UP) return false;
                Integer tag = ((Integer) v.getTag());
                if (currentCell != null) {
                    releaseMainGrid();
                    setPressedSmallButton(tag, currentCell.toggleAvailable(tag));
                }
                return true;
            }
        };
        for (int i = 0; i < BUTTON_SMALL_IDS.length; i++) {
            Button smallGridButton = (Button) findViewById(BUTTON_SMALL_IDS[i]);
            smallGridButton.setOnTouchListener(listener);
            smallGridButton.setTag(i + 1);
        }
    }

    private void setButtonListenersMainGrid() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // show interest in events resulting from ACTION_DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) return true;
                // don't handle event unless its ACTION_UP so "doSomething()" only runs once.
                if (event.getAction() != MotionEvent.ACTION_UP) return false;
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
            Button mainGridButton = (Button) findViewById(BUTTON_MAIN_IDS[i]);
            mainGridButton.setOnTouchListener(listener);
            mainGridButton.setTag(i + 1);
        }
    }

    private Button getSmallButtonByNumber(int number) {
        return (Button) findViewById(BUTTON_SMALL_IDS[number - 1]);
    }

    private Button getMainButtonByNumber(int number) {
        return (Button) findViewById(BUTTON_MAIN_IDS[number - 1]);
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

    private void startNewGame(int level) {
        TaskGenerator taskGenerator = new TaskGenerator();
        int[][] task = taskGenerator.generateTask(level);
        int[][] solution = taskGenerator.generateSolution(level);
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
            for (int i = 0; i < BUTTON_MAIN_IDS.length; i++) {
                Button mainGridButton = (Button) findViewById(BUTTON_MAIN_IDS[i]);
                mainGridButton.setPressed(i + 1 == currentCell.getNumber());
            }
            for (int i = 0; i < BUTTON_SMALL_IDS.length; i++) {
                Button smallGridButton = (Button) findViewById(BUTTON_SMALL_IDS[i]);
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
            Button mainGridButton = (Button) findViewById(BUTTON_MAIN_ID);
            mainGridButton.setPressed(false);
        }
    }

    private void releaseSmallGrid() {
        for (int BUTTON_SMALL_ID : BUTTON_SMALL_IDS) {
            Button smallGridButton = (Button) findViewById(BUTTON_SMALL_ID);
            smallGridButton.setPressed(false);
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
