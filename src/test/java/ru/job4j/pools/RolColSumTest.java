package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void thenSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        Sums exp1 = new Sums(6, 12);
        Sums exp2 = new Sums(15, 15);
        Sums exp3 = new Sums(24, 18);
        Sums[] expected = {exp1, exp2, exp3};
        assertThat(RolColSum.sum(matrix)).containsExactly(expected);
    }

    @Test
    void thenAsyncSum() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {4, 5, 6, 5},
                {7, 8, 9, 5}};
        Sums exp1 = new Sums(10, 13);
        Sums exp2 = new Sums(10, 17);
        Sums exp3 = new Sums(20, 21);
        Sums exp4 = new Sums(29, 18);
        Sums[] expected = {exp1, exp2, exp3, exp4};
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(expected);
    }
}