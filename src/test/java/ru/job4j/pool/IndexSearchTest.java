package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexSearchTest {

    @Test
    void whenTypeIsStringAndSizeOver10() {
        String[] arr = new String[14];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(i);
        }
        assertThat(IndexSearch.search(arr, "12")).isEqualTo(12);
    }

    @Test
    void whenTypeIsIntegerAndSizeLess10() {
        Integer[] arr = new Integer[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(IndexSearch.search(arr, 5)).isEqualTo(5);
    }

    @Test
    void whenElementNotFound() {
        Integer[] arr = new Integer[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(IndexSearch.search(arr, 20)).isEqualTo(-1);
    }
}