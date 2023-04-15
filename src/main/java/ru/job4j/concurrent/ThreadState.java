package main.java.ru.job4j.concurrent;

public class ThreadState {
    private static final Thread.State TERMINATED = Thread.State.TERMINATED;
    private static final String SEPARATOR = System.lineSeparator();

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.printf("Нить first - %s, Нить second - %s%s",
                first.getState(), second.getState(), SEPARATOR);
        first.start();
        second.start();
        while (first.getState() != TERMINATED || second.getState() != TERMINATED) {
            System.out.printf("Нить first - %s, Нить second - %s%s",
                    first.getState(), second.getState(), SEPARATOR);
        }
        System.out.printf("Нить first %s, Нить second - %s%s",
                first.getState(), second.getState(), SEPARATOR);
        System.out.printf("Работа нити %s завершена%s",
                Thread.currentThread().getName(), SEPARATOR);
    }
}
