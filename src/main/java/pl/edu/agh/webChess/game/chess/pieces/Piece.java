package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

public abstract class Piece {

    protected boolean colour; // true - white, false - black
    protected int row;
    protected int column;

    public abstract List<Position> getAllMoves(Piece[][] board);
    public abstract List<Position> getPossibleMoves(Piece[][] board);
    public abstract void move(int newRow, int newColumn);

    public boolean checkAttackOnKing(Piece[][] board) {

        for (Position position: getAllMoves(board)) {

            Piece piece = board[position.getRow()][position.getCol()];

            if(piece instanceof King)
                return true;
        }

        return false;
    }

    public boolean getColour() {
        return colour;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
