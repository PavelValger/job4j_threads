package main.java.ru.job4j.concurrent;

public class ThreadState {
    private static final Thread.State TERMINATED = Thread.State.TERMINATED;

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.printf("Нить first - %s, Нить second - %s\n", first.getState(), second.getState());
        first.start();
        second.start();
        while (first.getState() != TERMINATED) {
            System.out.printf("Состояние нити first - %s\n", first.getState());
        }
        while (second.getState() != TERMINATED) {
            System.out.printf("Состояние нити second - %s\n", second.getState());
        }
        System.out.printf("Нить first %s, Нить second - %s\n", first.getState(), second.getState());
        System.out.printf("Работа нити %s завершена\n", Thread.currentThread().getName());
    }
}
