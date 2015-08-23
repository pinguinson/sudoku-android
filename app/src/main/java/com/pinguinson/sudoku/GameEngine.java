package com.pinguinson.sudoku;

import android.util.Log;

import com.pinguinson.sudoku.view.SudokuCell;

/**
 * Created by pinguinson on 8/17/15.
 */
public class GameEngine {
    private static GameEngine instance;
    private int[][] task;
    private int[][] currentGrid;
    private int[][] solution;
    private SudokuCell[][] cells = new SudokuCell[9][9];
    private int emptyCells;
    private boolean isInTestMode;
    private GameActivity gameActivity;

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public void setGrids(int[][] solution, int[][] task) {
        this.solution = solution;
        this.task = task;
        this.currentGrid = new int[9][9];
        for (int i = 0; i < task.length; i++) {
            System.arraycopy(task[i], 0, currentGrid[i], 0, task[i].length);
        }
        countEmptyCells();
    }

    public boolean isInProgress() {
        return task != null;
    }

    public String saveGrids() {
        return gridToString(task) + gridToString(solution) + gridToString(currentGrid);
    }

    public void loadGrids(String load) {
        this.task = stringToGrid(load.substring(0, 81));
        this.solution = stringToGrid(load.substring(81, 162));
        int[][] loadedCurrentGrid = stringToGrid(load.substring(162, 243));
        this.currentGrid = loadedCurrentGrid;
        countEmptyCells();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (cells[x][y] != null) {
                    cells[x][y].setNumber(loadedCurrentGrid[x][y]);
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

    public int[][] getCurrentGrid() {
        return currentGrid;
    }

    public int[][] getTask() {
        return task;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void linkCell(int x, int y, SudokuCell cell) {
        cells[x][y] = cell;
    }

    public boolean testSolution() {
        if (isInTestMode) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    cells[x][y].setShowIfValid(false);
                }
            }
            return false;
        } else {
            boolean isCorrect = true;
            if (gameActivity.getCurrentCell() != null) {
                gameActivity.getCurrentCell().setSelection(false);
            }
            gameActivity.setCurrentCell(null);
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    cells[x][y].setShowIfValid(true);
                    if (currentGrid[x][y] != solution[x][y]) {
                        isCorrect = false;
                    }
                }
            }
            return isCorrect;
        }
    }

    public void showChanges() {
        System.out.println();
        System.out.println();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                char ans = '0';
                if (currentGrid[x][y] != task[x][y]) {
                    ans = 'X';
                }
                if (x % 3 != 2) {
                    System.out.print(ans + " ");
                } else {
                    System.out.print(ans + "|");
                }
            }
            System.out.println();
            if (y % 3 == 2) {
                System.out.println("------------------");
            }
        }
    }

    public void setCell(int x, int y, int number) {
        if (number != 0 && currentGrid[x][y] != 0) {
            emptyCells--;
        }
        currentGrid[x][y] = number;
        if (emptyCells == 0) {
            testSolution();
        }
        gameActivity.saveGame();
    }

    public boolean testCell(int x, int y) {
        return (currentGrid[x][y] == solution[x][y]);
    }

    public boolean isMutable(int x, int y) {
        return task[x][y] == 0;
    }

    public boolean[] availableNumbers(int x, int y) {
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
