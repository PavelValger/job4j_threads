package ru.job4j.concurrent;

import static java.lang.System.out;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (char symbol : process) {
                out.printf("\r load: %s", symbol);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    out.println(System.lineSeparator() + "Загрузка прервана");
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
