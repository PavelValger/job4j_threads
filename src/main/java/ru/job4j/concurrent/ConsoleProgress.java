package main.java.ru.job4j.concurrent;

import static java.lang.System.out;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        out.println("Loading ... |.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char symbol : process) {
                    if (!Thread.currentThread().isInterrupted()) {
                        out.printf("\r load: %s", symbol);
                        Thread.sleep(500);
                    }
                }
            }
        } catch (InterruptedException e) {
            out.println(System.lineSeparator() + "Загрузка прервана");
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
