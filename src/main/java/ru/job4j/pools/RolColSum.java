package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(0, 0);
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            count(i, matrix, sums);
        }
        return sums;
    }

    private static void count(int i, int[][] matrix, Sums[] sums) {
        CompletableFuture.runAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    sums[i] = new Sums(0, 0);
                    int rowSum = 0;
                    int colSum = 0;
                    for (int j = 0; j < matrix[i].length; j++) {
                        rowSum += matrix[i][j];
                        colSum += matrix[j][i];
                    }
                    sums[i].setRowSum(rowSum);
                    sums[i].setColSum(colSum);
                }
        ).join();
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {4, 5, 6, 5},
                {7, 8, 9, 5}};
        int count = 0;
        while (count != 50) {
            RolColSum.asyncSum(matrix);
            count++;
        }
    }
}
