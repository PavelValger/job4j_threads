package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private static final int DOWNLOAD_TIME = 1000;
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    private static int validate(String[] args) {
        if (args.length != 3) {
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
        if (!args[2].matches(".+\\..{3,4}")) {
            throw new IllegalArgumentException("Incorrect file name input.");
        }
        if (Paths.get(args[2]).toFile().exists()) {
            throw new IllegalArgumentException("File exist");
        }
        return speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int counterByte = 0;
            long start = System.currentTimeMillis();
            long startTimeStamp = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                counterByte += bytesRead;
                if (counterByte >= speed) {
                    long timeSleep = DOWNLOAD_TIME - (System.currentTimeMillis() - startTimeStamp);
                    if (timeSleep > 0) {
                        Thread.sleep(timeSleep);
                    }
                    counterByte = 0;
                    startTimeStamp = System.currentTimeMillis();
                }
            }
            long finish = System.currentTimeMillis();
            System.out.printf("The download time - %s seconds, speed - %s bytes per second, file size - %s bytes.",
                    (finish - start) / 1000, speed, Paths.get(fileName).toFile().length());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int speed = validate(args);
        Thread wget = new Thread(new Wget(args[0], speed, args[2]));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Program aborted");
        }
    }
}
