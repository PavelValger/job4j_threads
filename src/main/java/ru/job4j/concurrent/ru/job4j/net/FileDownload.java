package main.java.ru.job4j.concurrent.ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        String file = "https://mobimg.b-cdn.net/v3/fetch/08/08ac6b2156eecdf7476dd43a91312a9a.jpeg?w=1470&r=0.5625";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("qwerty.jpeg")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
