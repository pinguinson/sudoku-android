package com.pinguinson.sudoku.generator;

import java.util.Random;

/**
 * Created by pinguinson on 8/24/15.
 */
public class SeedGenerator {
    private SudokuBoard board;
    private Random rand;

    public SeedGenerator(int size) {
        this.board = new SudokuSolver(new SudokuBoard(size)).solve();
        this.rand = new Random();
    }

    //generating solution
    public SudokuBoard generate() {
        do {
            shuffle();
        } while (!board.isValidSeed());
        return board;
    }


    private void swapRows(int row1, int row2) {
        for (int column = 0; column < board.size; column++) {
            swapCells(row1, column, row2, column);
        }
    }

    private void swapColumns(int column1, int column2) {
        for (int row = 0; row < board.size; row++) {
            swapCells(row, column1, row, column2);
        }
    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        int buffer = board.getCell(x1, y1);
        board.setCell(x1, y1, board.getCell(x2, y2));
        board.setCell(x2, y2, buffer);
    }

    private int randomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    private void swapRandomRows() {
        int row1 = randomInt(0, board.size);
        int row2 = randomInt(0, board.size);
        while (row1 == row2) {
            row2 = randomInt(0, board.size);
        }
        swapRows(row1, row2);
    }

    private void swapRandomColumns() {
        int column1 = randomInt(0, board.size);
        int column2;
        do {
            column2 = randomInt(0, board.size);
        } while (column1 == column2);
        swapColumns(column1, column2);
    }

    private void shuffleRows() {
        for (int i = 0; i < 100; i++) {
            swapRandomRows();
        }
    }

    private void shuffleColumns() {
        for (int i = 0; i < 100; i++) {
            swapRandomColumns();
        }
    }

    private void shuffle() {
        shuffleRows();
        shuffleColumns();
    }
}
