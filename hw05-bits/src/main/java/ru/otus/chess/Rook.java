package ru.otus.chess;

import java.math.BigInteger;

/**
 * Задача про ладью
 */
public class Rook implements BiChess {

    @Override
    public BigInteger moveMask(int position) {
        BigInteger all64 = new BigInteger("ffffffffffffffff", 16); // установлены все биты шахматной доски
        // position = 8 * y + x (где y - цифры, x - буквы)
        // вертикаль A, сдвинутая влево (т.е. вертикаль, соответствующая позиции)
        BigInteger vertical = new BigInteger("101010101010101", 16).shiftLeft(position % 8);
        // горизонталь 1, сдвинутая влево
        BigInteger horizontal = new BigInteger("ff", 16).shiftLeft(position - position % 8);
        // при перемещении вверх не должны выходить за 8 горизонталь => применяем к итоговой маске all64, чтобы оставаться внутри доски
        // может двигаться по той же вертикали и по той же горизонтали
        return all64.and(vertical.xor(horizontal));
    }
}
