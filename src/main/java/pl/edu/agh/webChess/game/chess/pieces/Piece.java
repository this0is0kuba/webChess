package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.webChess.game.chess.auxiliary.MateFunctions.isYourKingMatedAfterYourMove;

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
    public abstract void move(int newRow, int newColumn);

    public List<Position> getPossibleMoves(Piece[][] board) {

        List<Position> possibleMoves = new ArrayList<>();

        for(Position to : getAllMoves(board)) {

            Position from = new Position(row, column);

            if (!isYourKingMatedAfterYourMove(board, from, to))
                possibleMoves.add(to);
        }

        return possibleMoves;
    }

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
