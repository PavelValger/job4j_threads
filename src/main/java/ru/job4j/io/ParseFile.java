package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader i = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = i.read()) != -1) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

    public String getContent() {
        return content(character -> true);
    }

    public String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
}
