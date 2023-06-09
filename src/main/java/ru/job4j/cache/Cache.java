package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunction = (key, baseInMemory) -> {
            if (model.getVersion() != baseInMemory.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base newBase = new Base(key, baseInMemory.getVersion() + 1);
            newBase.setName(baseInMemory.getName());
            return newBase;
        };
        return memory.computeIfPresent(model.getId(), biFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getMemory() {
        return new HashMap<>(memory);
    }
}
