package main.java.ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    private static int validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of parameters");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new RuntimeException("The link is broken");
        }
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(nfe.getMessage());
        }
        return speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("qwerty.jpeg")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long startTimeStamp = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long stopTimeStamp = System.currentTimeMillis();
                int difference = (int) (stopTimeStamp - startTimeStamp);
                if (difference < speed) {
                    try {
                        Thread.sleep(speed - difference);
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Program aborted");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int speed = validate(args);
        Thread wget = new Thread(new Wget(args[0], speed));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Program aborted");
        }
    }
}
