package ru.otus.popcnt;

import java.math.BigInteger;

public class PopCount3Cache implements PopCount {

    private static final int[] bits;
    private static final PopCount popCount = new PopCount2Subtract();
    private static final BigInteger base = BigInteger.valueOf(255);

    static {
        bits = new int[256];
        for (int j = 0; j < 256; j++) {
            bits[j] = popCount.popcnt(BigInteger.valueOf(j));
        }
    }

    @Override
    public int popcnt(BigInteger mask) {
        int cnt = 0;
        while (mask.compareTo(BigInteger.ZERO) > 0) { // mask > 0
            cnt += bits[mask.and(base).intValue()]; // cnt += bits[mask & 255]
            mask = mask.shiftRight(8); // mask >>= 8
        }
        return cnt;
    }
}
