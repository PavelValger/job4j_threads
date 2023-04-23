package ru.job4j.io;

import java.io.*;

public final class SaveContent {
    private final File file;

    public SaveContent(final File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter o = new BufferedWriter(new FileWriter(file, true))) {
            o.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
