package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public String name = "KNIGHT";
    public Knight(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    public Knight(Knight knight) {
        super(knight);
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        Position[] knightMoves = new Position[]{
            new Position(row - 2, column + 1),
            new Position(row - 1, column + 2),
            new Position(row + 1, column + 2),
            new Position(row + 2, column + 1),
            new Position(row + 2, column - 1),
            new Position(row + 1, column - 2),
            new Position(row - 1, column - 2),
            new Position(row - 2, column - 1)
        };

        for (Position knightMove : knightMoves) {

            int newRow = knightMove.getRow();
            int newCol = knightMove.getCol();

            if (newRow >= 8 || newRow < 0 || newCol >= 8 || newCol < 0)
                continue;

            Piece piece = board[newRow][newCol];

            if (piece != null && piece.colour != colour) {
                allMoves.add(knightMove);
                continue;
            }

            if (piece != null && piece.colour == colour)
                continue;

            // if reached then it means the piece is null

            allMoves.add(knightMove);
        }

        return allMoves;
    }

    @Override
    public void move(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
    }
}
