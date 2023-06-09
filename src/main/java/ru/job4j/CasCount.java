package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CasCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int expectedValue;
        int newValue;
        do {
            expectedValue = count.get();
            newValue = expectedValue + 1;
        } while (!count.compareAndSet(expectedValue, newValue));
    }

    public int get() {
        return count.get();
    }
}
