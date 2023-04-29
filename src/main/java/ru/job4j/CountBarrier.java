package ru.job4j;

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

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(2);
        Thread master = new Thread(
                () -> {
                    barrier.count();
                    barrier.count();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
