package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

public abstract class Piece {

    protected boolean colour; // true - white, false - black
    protected int row;
    protected int column;

    public Piece(boolean colour, int row, int column) {
        this.colour = colour;
        this.row = row;
        this.column = column;
    }

    public abstract List<Position> getAllMoves(Piece[][] board); // it includes moves after which you are mated
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
