package com.pinguinson.sudoku.generator;

/**
 * Created by pinguinson on 8/24/15.
 */
public class Ranker {
    public static int rank(SudokuBoard task) {
        int givenLevel = 1;
        int iterationsLevel = 1;
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.fitsIterations(SudokuSolver.getIterations(task))) {
                iterationsLevel = difficulty.getLevel();
            }
            if (difficulty.fitsGiven(task.countGivenCells())) {
                givenLevel = difficulty.getLevel();
            }
        }
        return (int) Math.round(0.4 * givenLevel + 0.6 * iterationsLevel);
    }
}
