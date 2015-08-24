package com.pinguinson.sudoku.generator;

public class Main {
    public static void main(String[] args) {
        int[][] board = {{0, 6, 0, 1, 0, 4, 0, 5, 0},
                {0, 0, 8, 3, 0, 5, 6, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 1},
                {8, 0, 0, 4, 0, 7, 0, 0, 6},
                {0, 0, 6, 0, 0, 0, 3, 0, 0},
                {7, 0, 0, 9, 0, 1, 0, 0, 4},
                {5, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 7, 2, 0, 6, 9, 0, 0},
                {0, 4, 0, 5, 0, 8, 0, 7, 0}};
        for (Difficulty difficulty : Difficulty.values()) {
            System.out.println("Diff: " + difficulty);
            SudokuBoard task = new TaskGenerator(9).generateTask(difficulty);
            System.out.println("Task: ");
            task.printBoard();
            System.out.println("Solution: ");
            SudokuSolver.solve(task).printBoard();

        }
    }
}