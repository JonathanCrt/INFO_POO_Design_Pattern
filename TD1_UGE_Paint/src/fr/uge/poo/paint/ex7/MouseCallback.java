package fr.uge.poo.paint.ex7;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface MouseCallback {
    void waitOnClick(BiConsumer<Integer, Integer> biConsumer);
}
