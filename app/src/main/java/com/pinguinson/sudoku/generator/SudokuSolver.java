package com.pinguinson.sudoku.generator;

public class SudokuSolver {
    private SudokuBoard sb;
    private SudokuBoard task;
    private int iterations;


    public SudokuSolver(SudokuBoard sb) {
        this.sb = sb.copy();
        this.task = sb.copy();
    }

    //Check if a number is, according to Sudoku rules, a legal candidate for the given cell.
    private boolean check(int num, int row, int col) {
        int r = (row / sb.box_size) * sb.box_size;
        int c = (col / sb.box_size) * sb.box_size;

        for (int i = 0; i < sb.size; i++) {
            if (sb.getCell(i, row) == num ||
                    sb.getCell(col, i) == num ||
                    sb.getCell(c + (i / sb.box_size), r + (i % sb.box_size)) == num) {
                return false;
            }
        }
        return true;
    }

    //Test all candidate numbers for a given cell until the board is complete.
    private boolean guess(int row, int col) {
        iterations++;
        int nextCol = (col + 1) % sb.size;
        int nextRow = (nextCol == 0) ? row + 1 : row;

        try {
            if (sb.getCell(col, row) != SudokuBoard.EMPTY)
                return guess(nextRow, nextCol);
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        for (int i = 1; i <= sb.size; i++) {
            if (check(i, row, col)) {
                sb.setCell(col, row, i);
                if (guess(nextRow, nextCol)) {
                    return true;
                }
            }
        }
        sb.setCell(col, row, SudokuBoard.EMPTY);
        return false;
    }

    public SudokuBoard solve() {
        boolean answer = guess(0, 0);
        if (answer) {
            return sb;
        } else {
            return null;
        }
    }

    public static SudokuBoard solve(SudokuBoard task) {
        return new SudokuSolver(task).solve();
    }

    public static boolean isSolvable(SudokuBoard task) {
        return solve(task) != null;
    }

    public int getIterations() {
        guess(0, 0);
        return iterations;
    }

    public static int getIterations(SudokuBoard task) {
        return new SudokuSolver(task).getIterations();
    }
}