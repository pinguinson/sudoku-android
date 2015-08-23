package com.pinguinson.sudoku;

import java.util.Random;

/**
 * Created by pinguinson on 8/17/15.
 */
public class SudokuGenerator {
    static int a[][] = new int[9][9];
    static int counter = 1;
    static int k1;
    static int k2;

    public static void generate() {
        int k;
        int n = 1;
        for (int i = 0; i < 9; i++) {
            k = n;
            for (int j = 0; j < 9; j++) {
                if (k <= 9) {
                    a[i][j] = k;
                    k++;
                } else {
                    k = 1;
                    a[i][j] = k;
                    k++;
                }
            }
            n = k + 3;
            if (k == 10) {
                n = 4;
            }
            if (n > 9) {
                n = (n % 9) + 1;
            }
        }
    }

    public static void permutationRow(int k1, int k2) {
        int temp;//k1 and k2 are two rows that we are selecting to interchange.
        for (int j = 0; j < 9; j++) {
            temp = a[k1][j];
            a[k1][j] = a[k2][j];
            a[k2][j] = temp;
        }
    }

    public static void permutationCol(int k1, int k2) {
        int temp;
        for (int j = 0; j < 9; j++) {
            temp = a[j][k1];
            a[j][k1] = a[j][k2];
            a[j][k2] = temp;
        }
    }

    public static void randomGen(int check) {
        int k1, k2, max = 2, min = 0;
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            //There are three groups.So we are using for loop three times.
            k1 = r.nextInt(max - min + 1) + min;
            //This while is just to ensure k1 is not equal to k2.
            do {
                k2 = r.nextInt(max - min + 1) + min;
            } while (k1 == k2);
            max += 3;
            min += 3;
            //check is global variable.
            //We are calling randomGen two time from the main func.
            //Once it will be called for columns and once for rows.
            if (check == 1)
                //calling a function to interchange the selected rows.
                permutationRow(k1, k2);
            else if (check == 0)
                permutationCol(k1, k2);
        }
    }

    public static void rowChange(int k1, int k2) {
        int temp;
        for (int n = 1; n <= 3; n++) {
            for (int i = 0; i < 9; i++) {
                temp = a[k1][i];
                a[k1][i] = a[k2][i];
                a[k2][i] = temp;
            }
            k1++;
            k2++;
        }
    }

    public static void colChange(int k1, int k2) {
        int temp;
        for (int n = 1; n <= 3; n++) {
            for (int i = 0; i < 9; i++) {
                temp = a[i][k1];
                a[i][k1] = a[i][k2];
                a[i][k2] = temp;
            }
            k1++;
            k2++;
        }
    }

    public static void strikeOut(int k1, int k2) {
        int row_from;
        int row_to;
        int col_from;
        int col_to;
        int i, j, b, c;
        int rem1, rem2;
        int flag;
        int count = 9;
        for (i = 1; i <= 9; i++) {
            flag = 1;
            for (j = 0; j < 9; j++) {
                if (j != k2) {
                    if (i == a[k1][j]) {
                        flag = 0;
                        break;
                    }
                }
            }
            if (flag == 1) {
                for (c = 0; c < 9; c++) {
                    if (c != k1) {
                        if (i == a[c][k2]) {
                            flag = 0;
                            break;
                        }
                    }
                }
            }
            if (flag == 1) {
                rem1 = k1 % 3;
                rem2 = k2 % 3;
                row_from = k1 - rem1;
                row_to = k1 + (2 - rem1);
                col_from = k2 - rem2;
                col_to = k2 + (2 - rem2);
                for (c = row_from; c <= row_to; c++) {
                    for (b = col_from; b <= col_to; b++) {
                        if (c != k1 && b != k2) {
                            if (i == a[c][b]) {
                                flag = 0;
                                break;
                            }
                        }
                    }
                }
            }
            if (flag == 0) {
                count--;
            }
        }
        if (count == 1) {
            a[k1][k2] = 0;
        }
    }

    public static int[][] generateSolution() {
        generate();
        randomGen(1);
        randomGen(0);

        Random rand = new Random();
        int n[] = {0, 3, 6};
        for (int i = 0; i < 2; i++) {
            k1 = n[rand.nextInt(n.length)];
            do {
                k2 = n[rand.nextInt(n.length)];
            } while (k1 == k2);
            if (counter == 1) {
                rowChange(k1, k2);
            } else {
                colChange(k1, k2);
            }
            counter++;
        }
        int[][] solution = new int[9][9];
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, solution[i], 0, a[i].length);
        }
        return solution;
    }

    public static int[][] generateTask() {
        //Striking out
        for (k1 = 0; k1 < 9; k1++) {
            for (k2 = 0; k2 < 9; k2++) {
                strikeOut(k1, k2);
            }
        }
        int[][] task = new int[9][9];
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, task[i], 0, a[i].length);
        }
        return task;
    }
}
