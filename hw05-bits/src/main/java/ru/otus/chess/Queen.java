package ru.otus.chess;

import java.math.BigInteger;

/**
 * Задача про ферзя (по вертикали, горизонтали и диагонали)
 */
public class Queen implements BiChess {

    private final BiChess bishop = new Bishop();
    private final BiChess rook = new Rook();

    @Override
    public BigInteger moveMask(int position) {
        // совмещает ходы ладьи и слона
        return bishop.moveMask(position).or(rook.moveMask(position));
    }
}
