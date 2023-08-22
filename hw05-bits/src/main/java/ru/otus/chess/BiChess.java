package ru.otus.chess;

import java.math.BigInteger;

public interface BiChess {

    /**
     * Найти битовую маску полей, куда может ходить
     * @param position индекс позиции от 0 до 63
     * @return число с установленными битами тех полей, куда возможны ходы
     */
    BigInteger moveMask(int position);
}
