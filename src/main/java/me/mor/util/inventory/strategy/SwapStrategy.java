package me.mor.util.inventory.strategy;

import me.mor.util.inventory.Result;

public interface SwapStrategy {
    boolean swap(Result result);

    boolean swapBack(int last, Result result);
}
