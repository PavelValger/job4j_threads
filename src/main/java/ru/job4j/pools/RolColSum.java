package ru.job4j.pools;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    private static Sums[] sums;

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{rowSum=%d, colSum=%d}".formatted(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(0, 0);
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i].rowSum += matrix[i][j];
                sums[j].colSum += matrix[i][j];
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        thenComposeExample(matrix);
        return sums;
    }

    private static CompletableFuture<Void> createSums(int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> {
                    sums = new Sums[matrix.length];
                    for (int i = 0; i < sums.length; i++) {
                        sums[i] = new Sums(0, 0);
                    }
                });
    }

    private static CompletableFuture<Void> countLines(int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix[i].length; j++) {
                            sums[i].rowSum += matrix[i][j];
                        }
                    }
                });
    }

    private static CompletableFuture<Void> countColumns(int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int[] ints : matrix) {
                        for (int j = 0; j < ints.length; j++) {
                            sums[j].colSum += ints[j];
                        }
                    }
                });
    }

    private static CompletableFuture<Void> allOfExample(int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> CompletableFuture.allOf(countLines(matrix), countColumns(matrix)));
    }

    private static void thenComposeExample(int[][] matrix) {
        CompletableFuture<Void> result = createSums(matrix).thenCompose(a -> allOfExample(matrix));
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int[][] array = {
                {1},
                {2, 3},
                {4, 5, 6}};
        System.out.println(Arrays.toString(sum(array)));
        System.out.println(Arrays.toString(asyncSum(array)));
    }
}
