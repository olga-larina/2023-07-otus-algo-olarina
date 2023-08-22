package ru.otus.chess;

import java.math.BigInteger;

/**
 * Задача про короля
 */
public class King implements BiChess {

    @Override
    public BigInteger moveMask(int position) {
        BigInteger biPosition = BigInteger.ONE.shiftLeft(position); // << position
        BigInteger notA = new BigInteger("fefefefefefefefe", 16); // установлены все биты, кроме вертикали A
        BigInteger notH = new BigInteger("7f7f7f7f7f7f7f7f", 16); // установлены все биты, кроме вертикали H
        BigInteger not8 = new BigInteger("ffffffffffffff", 16); // установлены все биты, кроме горизонтали 8
        BigInteger biPositionNotA = biPosition.and(notA); // &
        BigInteger biPositionNotH = biPosition.and(notH);
        BigInteger biPositionNotA8 = biPositionNotA.and(not8);
        BigInteger biPositionNot8 = biPosition.and(not8);
        BigInteger biPositionNotH8 = biPositionNotH.and(not8);
        // shiftLeft <<
        // shiftRight >>
        // при перемещении вверх не должны выходить за 8 горизонталь => исключаем движение вверх, если находится на 8 горизонтали
        // при перемещении вниз не должны выходить за 0 горизонталь => не нужно дополнительно обрабатывать
        // при перемещении влево не должны выходить за вертикаль A => исключаем движение влево, если находится на вертикали A
        // при перемещении вправо не должны выходить за вертикаль H => исключаем движение вправо, если находится на вертикали H
        // если находится на позиции B3 (17)
        return (biPositionNotA8.shiftLeft(7)).or(biPositionNot8.shiftLeft(8)).or(biPositionNotH8.shiftLeft(9)) // A4 (24) - B4 (25) - C4 (26)
            .or(biPositionNotA.shiftRight(1)).or(biPositionNotH.shiftLeft(1)) // A3 (16) - C3 (18)
            .or(biPositionNotA.shiftRight(9)).or(biPosition.shiftRight(8)).or(biPositionNotH.shiftRight(7)) // A2 (08) - B2 (09) - C2 (10)
        ;
    }
}
