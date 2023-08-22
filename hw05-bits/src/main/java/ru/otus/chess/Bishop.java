package ru.otus.chess;

import java.math.BigInteger;

/**
 * Задача про слона
 */
public class Bishop implements BiChess {

    @Override
    public BigInteger moveMask(int position) {
        BigInteger all64 = new BigInteger("ffffffffffffffff", 16); // установлены все биты шахматной доски
        // position = 8 * y + x  (где y - цифры, x - буквы)
        int x = position % 8;
        int y = position / 8;
        BigInteger diagonalA1H8 = new BigInteger("8040201008040201", 16); // диагональ A1-H8
        BigInteger diagonalA8H1 = new BigInteger("102040810204080", 16); // диагональ A8-H1
        BigInteger triangleA8A1H1 = new BigInteger("103070f1f3f7f", 16); // треугольник A8-A1-H1 - без диагонали A8-H1
        BigInteger triangleA8H1H8 = new BigInteger("fefcf8f0e0c08000", 16); // треугольник A8-H1-H8 - без диагонали A8-H1
        BigInteger triangleA8A1H8 = new BigInteger("ff7f3f1f0f070301", 16); // треугольник A8-A1-H8 - без диагонали A1-H8
        BigInteger triangleA1H1H8 = new BigInteger("80c0e0f0f8fcfe", 16); // треугольник A1-H1-H8 - без диагонали A1-H8
        BigInteger diagonal1;
        BigInteger diagonal2;
        if (x == y) {
            diagonal1 = diagonalA1H8;
        } else if (x > y) {
            diagonal1 = triangleA1H1H8.and(diagonalA1H8.shiftLeft(x - y));
        } else {
            diagonal1 = triangleA8A1H8.and(diagonalA1H8.shiftRight(y - x));
        }
        if (y == 7 - x) {
            diagonal2 = diagonalA8H1;
        } else if (y > 7 - x) {
            diagonal2 = triangleA8H1H8.and(diagonalA8H1.shiftLeft(y - (7 - x)));
        } else {
            diagonal2 = triangleA8A1H1.and(diagonalA8H1.shiftRight((7 - x) - y));
        }
        // при перемещении вверх не должны выходить за 8 горизонталь => применяем к итоговой маске all64, чтобы оставаться внутри доски
        // может двигаться по диагоналям
        return all64.and(diagonal1.xor(diagonal2));
    }
}
