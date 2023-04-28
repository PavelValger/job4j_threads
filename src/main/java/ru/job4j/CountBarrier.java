package ru.job4j;

import java.util.function.Consumer;

public class CountBarrier {
    private final int total;
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void action(Consumer<Integer> consumer) {
        while (!Thread.currentThread().isInterrupted()) {
            consumer.accept(0);
            System.out.printf("%s %s%s", Thread.currentThread().getName(), Thread.currentThread().getState(),
                    System.lineSeparator());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(3);
        Thread master = new Thread(
                () -> action(integer -> barrier.count()),
                "Master"
        );
        Thread slave = new Thread(
                () -> action(integer -> barrier.await()),
                "Slave"
        );
        master.start();
        slave.start();
        Thread.sleep(10000);
        master.interrupt();
        slave.interrupt();
    }
}
