package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread another = new Thread(
                    () -> System.out.println(Thread.currentThread().getName())
            );
            another.start();
            Thread second = new Thread(
                    () -> System.out.println(Thread.currentThread().getName())
            );
            second.start();
            System.out.println(Thread.currentThread().getName());
        }
    }
}
