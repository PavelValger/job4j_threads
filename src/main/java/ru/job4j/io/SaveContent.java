package ru.job4j.io;

import java.io.*;

public final class SaveContent {
    private final File file;

    public SaveContent(final File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
