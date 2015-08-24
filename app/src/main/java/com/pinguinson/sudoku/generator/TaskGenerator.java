package com.pinguinson.sudoku.generator;

import java.util.Random;

/**
 * Created by pinguinson on 8/24/15.
 */
public class TaskGenerator {
    private int difficultyLevel;
    private SudokuBoard board;
    private Random rand;

    public TaskGenerator(int size) {
        this.board = new SeedGenerator(size).generate().copy();
        this.rand = new Random();
    }

    public SudokuBoard generateImpossibleTask() {
        for (int x = 0; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {
                digHole(x, y);
            }
        }
        return board;
    }

    public SudokuBoard generatePossibleTask(int givenCellsTarget) {
        int givenCells = 81;
        int iterationsSinceLastDig = 0;
        while (givenCells > givenCellsTarget) {
            int randomX = randomInt(0, board.size);
            int randomY = randomInt(0, board.size);
            if (digHole(randomX, randomY)) {
                givenCells--;
                iterationsSinceLastDig = 0;
            } else {
                iterationsSinceLastDig++;
                if (iterationsSinceLastDig > 200) {
                    System.err.println("failed");
                    return null;
                }
            }
        }
        return board;
    }

    public SudokuBoard generateTask(Difficulty difficulty) {
        if (difficulty.equals(Difficulty.EVIL)) {
            return generateImpossibleTask();
        } else {
            int givenCellsTarget = randomInt(difficulty.getGivenLowerBound(), difficulty.getGivenHigherBound());
            return generatePossibleTask(givenCellsTarget);
        }
    }

    private int randomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    //generating task
    private boolean digHole(int x, int y) {
        if (board.getCell(x, y) == SudokuBoard.EMPTY) {
            return false;
        }
        int number = board.getCell(x, y);
        for (int value = 1; value <= board.size; value++) {
            if (value != number) {
                board.setCell(x, y, value);
                if (SudokuSolver.isSolvable(board) && SudokuSolver.solve(board).isValidSeed()) {
                    board.setCell(x, y, number);
                    return false;
                }
            }
        }
        board.setCell(x, y, SudokuBoard.EMPTY);
        return true;
    }
}
