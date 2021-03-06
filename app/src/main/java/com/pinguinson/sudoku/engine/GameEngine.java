package com.pinguinson.sudoku.engine;

import android.util.Log;

import com.pinguinson.sudoku.cell.SudokuCell;
import com.pinguinson.sudoku.fragments.GameFragment;

import java.util.Arrays;

/**
 * Created by pinguinson on 8/17/15.
 */
public class GameEngine {
    private static GameEngine instance;
    private int[][] task;
    private int[][] currentGrid;
    private int[][] solution;
    private SudokuCell[][] cells = new SudokuCell[9][9];
    private boolean[][][] availableNumbers;
    private int emptyCells;
    private GameFragment gameFragment;

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void setGameFragment(GameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }

    public void setGrids(int[][] solution, int[][] task) {
        this.solution = solution;
        this.task = task;
        this.currentGrid = new int[9][9];
        for (int i = 0; i < task.length; i++) {
            System.arraycopy(task[i], 0, currentGrid[i], 0, task[i].length);
        }
        this.availableNumbers = new boolean[9][9][9];
        countEmptyCells();
    }

    public boolean[] getAvailableNumbers(int x, int y) {
        return availableNumbers[x][y];
    }

    public boolean toggleAvailable(int x, int y, int number) {
        availableNumbers[x][y][number - 1] = !availableNumbers[x][y][number - 1];
        gameFragment.saveGame();
        return isAvailable(x, y, number);
    }

    public boolean isAvailable(int x, int y, int number) {
        return availableNumbers[x][y][number - 1];
    }

    public String saveGrids() {
        return gridToString(task) + gridToString(solution) +
                gridToString(currentGrid) + availableToString();
    }

    public void loadGrids(String load) {
        // FIXME: 8/23/15
        this.task = stringToGrid(load.substring(0, 81));
        this.solution = stringToGrid(load.substring(81, 162));
        this.currentGrid = stringToGrid(load.substring(162, 243));
        loadAvailable(load.substring(243));
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (cells[x][y] != null) {
                    cells[x][y].setNumber(currentGrid[x][y]);
                }
            }
        }
        countEmptyCells();
        Log.d("LOAD", "successfully converted string to grids");
    }

    public void countEmptyCells() {
        emptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (task[i][j] == 0) {
                    emptyCells++;
                }
            }
        }
    }

    private String availableToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                for (int z = 0; z < 9; z++) {
                    if (availableNumbers[x][y][z]) {
                        stringBuilder.append("1");
                    } else {
                        stringBuilder.append("0");
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private void loadAvailable(String save) {
        this.availableNumbers = new boolean[9][9][9];
        int currentChar = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                for (int z = 0; z < 9; z++) {
                    availableNumbers[x][y][z] = save.charAt(currentChar++) == '1';
                }
            }
        }
    }

    private String gridToString(int[][] grid) {
        String convertedGrid = "";
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                convertedGrid += String.valueOf(grid[x][y]);
            }
        }
        return convertedGrid;
    }

    private int[][] stringToGrid(String string) {
        int curChar = 0;
        int[][] grid = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = Integer.valueOf(string.substring(curChar, curChar + 1));
                curChar++;
            }
        }
        return grid;
    }

    public void linkCell(int x, int y, SudokuCell cell) {
        cells[x][y] = cell;
    }

    public boolean testSolution() {
        boolean isCorrect = true;
        if (gameFragment.getCurrentCell() != null) {
            gameFragment.getCurrentCell().setSelection(false);
        }
        gameFragment.setCurrentCell(null);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (currentGrid[x][y] != solution[x][y]) {
                    isCorrect = false;
                }
            }
        }
        return isCorrect;
    }

    public int getNumber(int x, int y) {
        return currentGrid[x][y];
    }

    public void setCell(int x, int y, int number) {
        if (number != 0) {
            Arrays.fill(availableNumbers[x][y], false);
        }
        if (number != 0 && currentGrid[x][y] != 0) {
            emptyCells--;
        }
        currentGrid[x][y] = number;
        if (emptyCells == 0) {
            testSolution();
        }
        gameFragment.saveGame();
    }

    public boolean isMutable(int x, int y) {
        return task[x][y] == 0;
    }

    public boolean[] getHint(int x, int y) {
        boolean[] available = new boolean[10];
        for (int i = 1; i <= 9; i++) {
            available[i] = true;
        }

        //block
        for (int i = (x / 3) * 3; i <= (x / 3) * 3 + 2; i++) {
            for (int j = (y / 3) * 3; j <= (y / 3) * 3 + 2; j++) {
                available[currentGrid[i][j]] = false;
            }
        }

        //row
        for (int i = 0; i < 9; i++) {
            available[currentGrid[x][i]] = false;
        }

        //column
        for (int i = 0; i < 9; i++) {
            available[currentGrid[i][y]] = false;
        }
        return available;
    }
}
