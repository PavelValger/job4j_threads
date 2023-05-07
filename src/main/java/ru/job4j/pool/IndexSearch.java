package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;

    public IndexSearch(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    private static <T> int linearSearch(T[] array, int from, int to, T obj) {
        for (int i = from; i <= to; i++) {
            if (obj.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch(array, from, to, obj);
        }
        int mid = (from + to) / 2;
        IndexSearch<T> left = new IndexSearch<>(array, from, mid, obj);
        IndexSearch<T> right = new IndexSearch<>(array, mid + 1, to, obj);
        left.fork();
        right.fork();
        Integer rslLeft = left.join();
        Integer rslRight = right.join();
        return Math.max(rslLeft, rslRight);
    }

    public static <T> int search(T[] array, T obj) {
        return new ForkJoinPool().invoke(new IndexSearch<>(array, 0, array.length - 1, obj));
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        System.out.println(search(arr, 150));
    }
}
