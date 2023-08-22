package ru.otus.chess;

import java.math.BigInteger;

/**
 * Задача про коня
 */
public class Knight implements BiChess {

    @Override
    public BigInteger moveMask(int position) {
        BigInteger biPosition = BigInteger.ONE.shiftLeft(position); // << position
        BigInteger all64 = new BigInteger("ffffffffffffffff", 16); // установлены все биты шахматной доски
        BigInteger notA = new BigInteger("fefefefefefefefe", 16); // установлены все биты, кроме вертикали A
        BigInteger notH = new BigInteger("7f7f7f7f7f7f7f7f", 16); // установлены все биты, кроме вертикали H
        BigInteger notAB = new BigInteger("fcfcfcfcfcfcfcfc", 16); // установлены все биты, кроме вертикалей A и B
        BigInteger notGH = new BigInteger("3f3f3f3f3f3f3f3f", 16); // установлены все биты, кроме вертикалей G и H
        // при перемещении вверх не должны выходить за 8 горизонталь => применяем к итоговой маске all64, чтобы оставаться внутри доски
        // при перемещении влево на 2 не должны оказаться на G или H
        // при перемещении влево на 1 не должны оказаться на H
        // при перемещении вправо на 2 не должны оказаться на A или B
        // при перемещении вправо на 1 не должны оказаться на A
        // если находится на позиции D4 (27)
        return all64.and(
            notGH.and(
                (biPosition.shiftLeft(6)).or(biPosition.shiftRight(10)) // B5 (33) - B3 (17)
                )
                .or(notH.and(
                    (biPosition.shiftLeft(15)).or(biPosition.shiftRight(17)) // C6 (42) - C2 (10)
                ))
                .or(notA.and(
                    (biPosition.shiftLeft(17)).or(biPosition.shiftRight(15)) // E6 (44) - E2 (12)
                ))
                .or(notAB.and(
                    (biPosition.shiftLeft(10)).or(biPosition.shiftRight(6)) // F5 (37) - F3 (21)
                ))
        );
    }
}
