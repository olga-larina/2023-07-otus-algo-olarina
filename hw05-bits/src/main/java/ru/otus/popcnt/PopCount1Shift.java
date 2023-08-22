package ru.otus.popcnt;

import java.math.BigInteger;

public class PopCount1Shift implements PopCount {

    @Override
    public int popcnt(BigInteger mask) {
        int cnt = 0;
        while (mask.compareTo(BigInteger.ZERO) > 0) { // mask > 0
            cnt += mask.and(BigInteger.ONE).intValue(); // cnt += (int) (mask & 1)
            mask = mask.shiftRight(1); // mask >>= 1
        }
        return cnt;
    }
}
