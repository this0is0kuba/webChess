package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    boolean moved = false;

    public King(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {

                int cRow = row - 1 + i;
                int cCol = column - 1 + j;

                if(i == 1 && j == 1)
                    continue;

                if(cRow < 0 || cRow >= 8 || cCol < 0 || cCol >= 8)
                    continue;

                Piece piece = board[cRow][cCol];

                if(piece != null && piece.colour != colour) {

                    allMoves.add(new Position(cRow, cCol));
                    continue;
                }

                if(piece != null && piece.colour == colour)
                    continue;

                // if reached here then it means piece is null

                allMoves.add(new Position(cRow, cCol));
            }

        return allMoves;
    }

    @Override
    public void move(int newRow, int newColumn) {

        moved = true;

        row = newRow;
        column = newColumn;
    }
}
