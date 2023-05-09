package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void thenSum() {
        int[][] matrix = {
                {1},
                {2, 3},
                {4, 5, 6}};
        RolColSum.Sums exp1 = new RolColSum.Sums(1, 7);
        RolColSum.Sums exp2 = new RolColSum.Sums(5, 8);
        RolColSum.Sums exp3 = new RolColSum.Sums(15, 6);
        RolColSum.Sums[] expected = {exp1, exp2, exp3};
        assertThat(RolColSum.sum(matrix)).containsExactly(expected);
    }

    @Test
    void thenAsyncSum() {
        int[][] matrix = {
                {1},
                {2, 3},
                {4, 5, 6}};
        RolColSum.Sums exp1 = new RolColSum.Sums(1, 7);
        RolColSum.Sums exp2 = new RolColSum.Sums(5, 8);
        RolColSum.Sums exp3 = new RolColSum.Sums(15, 6);
        RolColSum.Sums[] expected = {exp1, exp2, exp3};
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(expected);
    }
}