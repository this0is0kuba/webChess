package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

public class King extends Piece{

    public King(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {
        return null;
    }

    @Override
    public void move(int newRow, int newColumn) {

    }
}
