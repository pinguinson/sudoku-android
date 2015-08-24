package com.pinguinson.sudoku.generator;

public class SudokuBoard {
    public static final int EMPTY = 0;      // Empty cells marker
    final int size;           // Size of the board (number of rows and columns)
    final int box_size;       // Size of the inner boxes

    private int[][] board;    // 2D array representing the game board

    public SudokuBoard(int size) {
        board = new int[size][size];
        this.size = size;
        this.box_size = (int) Math.sqrt(size);
    }

    public SudokuBoard(int[][] board) {
        this(board.length);
        this.board = board;
    }

    public void setCell(int x, int y, int number) {
        board[x][y] = number;
    }

    public int getCell(int x, int y) {
        return board[x][y];
    }

    public int[] getRow(int x) {
        int[] row = new int[size];
        System.arraycopy(board[x], 0, row, 0, size);
        return row;
    }

    public int[] getColumn(int y) {
        int[] column = new int[size];
        for (int x = 0; x < size; x++) {
            column[x] = board[x][y];
        }
        return column;
    }

    public int[] getBlock(int x, int y) {
        int lowerX = (x / 3) * 3;
        int higherX = lowerX + 2;
        int lowerY = (y / 3) * 3;
        int higherY = lowerY + 2;
        int[] block = new int[size];
        int index = 0;
        for (int i = lowerX; i <= higherX; i++) {
            for (int j = lowerY; j <= higherY; j++) {
                block[index++] = board[i][j];
            }
        }
        return block;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public SudokuBoard copy() {
        int[][] newBoard = new int[size][size];
        for (int x = 0; x < size; x++) {
            System.arraycopy(board[x], 0, newBoard[x], 0, size);
        }
        return new SudokuBoard(newBoard);
    }

    public boolean isValidSeed() {
        return (testRows() && testColumns() && testBlocks());
    }

    private boolean isValidUnit(int[] unit) {
        int[] encountered = new int[size];
        for (int anUnit : unit) {
            encountered[anUnit - 1]++;
        }
        for (int i = 0; i < size; i++) {
            if (encountered[i] != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidRow(int x) {
        return isValidUnit(getRow(x));
    }

    private boolean testRows() {
        for (int row = 0; row < size; row++) {
            if (!isValidRow(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean testColumns() {
        for (int column = 0; column < size; column++) {
            if (!isValidColumn(column)) {
                return false;
            }
        }
        return true;
    }

    private boolean testBlocks() {
        for (int x = 0; x < size; x += 3) {
            for (int y = 0; y < size; y += 3) {
                if (!isValidBlock(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidColumn(int y) {
        return isValidUnit(getColumn(y));
    }

    private boolean isValidBlock(int x, int y) {
        return isValidUnit(getBlock(x, y));
    }

    public int countEmptyCells() {
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] == 0) {
                    total++;
                }
            }
        }
        return total;
    }

    public int countGivenCells() {
        return size * size - countEmptyCells();
    }

    public int[][] getBoard() {
        return board;
    }
}
