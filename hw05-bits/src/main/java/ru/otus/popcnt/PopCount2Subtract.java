package ru.otus.popcnt;

import java.math.BigInteger;

public class PopCount2Subtract implements PopCount {

    @Override
    public int popcnt(BigInteger mask) {
        int cnt = 0;
        while (mask.compareTo(BigInteger.ZERO) > 0) { // mask > 0
            cnt++;
            mask = mask.and(mask.subtract(BigInteger.ONE)); // mask &= mask - 1
        }
        return cnt;
    }
}
