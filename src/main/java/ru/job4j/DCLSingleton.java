package main.java.ru.job4j;

public final class DCLSingleton {
    private static volatile DCLSingleton inst;

    public DCLSingleton() {
    }

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
}