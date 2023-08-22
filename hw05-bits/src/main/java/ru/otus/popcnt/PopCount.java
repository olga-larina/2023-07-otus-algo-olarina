package ru.otus.popcnt;

import java.math.BigInteger;

/**
 * Подсчёт единичных бит
 */
public interface PopCount {

    int popcnt(BigInteger mask);
}
