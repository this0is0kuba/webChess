package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.webChess.game.chess.auxiliary.MateFunctions.isYourKingMatedAfterYourMove;

public class Pawn extends Piece {
    private boolean lastMoveWas2 = false;

    public Pawn(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    @Override
    public void move(int newRow, int newColumn) {

        if( Math.abs(newRow - row) == 2)
            lastMoveWas2 = true;
        else
            lastMoveWas2 = false;

        row = newRow;
        column = newColumn;
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        if(colour)
            return getAllMovesForWhite(board);
        else
            return getAllMovesForBlack(board);
    }

    public List<Position> getAllMovesForWhite(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        if(board[row - 1][column] == null) {
            allMoves.add(new Position(row - 1, column));

            if(row == 6 && board[row - 2][column] == null)
                allMoves.add( new Position(row - 2, column));
        }

        if(column - 1 >= 0 && board[row - 1][column - 1] != null && !board[row - 1][column - 1].getColour())
            allMoves.add( new Position(row - 1, column - 1));

        if(column + 1 < 8 && board[row - 1][column + 1] != null && !board[row - 1][column + 1].getColour())
            allMoves.add( new Position(row - 1, column + 1));

        if(column - 1 >= 0 && board[row][column - 1] instanceof Pawn && !board[row][column - 1].getColour() && ((Pawn) board[row][column - 1]).lastMoveWas2)
            allMoves.add(new Position(row - 1, column - 1));

        if(column + 1 < 8 && board[row][column + 1] instanceof Pawn && !board[row][column + 1].getColour() && ((Pawn) board[row][column + 1]).lastMoveWas2)
            allMoves.add(new Position(row - 1, column + 1));

        return allMoves;
    }

    public List<Position> getAllMovesForBlack(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        if(board[row + 1][column] == null) {
            allMoves.add(new Position(row + 1, column));

            if(row == 1 && board[row + 2][column] == null)
                allMoves.add( new Position(row + 2, column));
        }

        if(column - 1 >= 0 && board[row + 1][column - 1] != null && board[row + 1][column - 1].getColour())
            allMoves.add( new Position(row + 1, column - 1));

        if(column + 1 < 8 && board[row + 1][column + 1] != null && board[row + 1][column + 1].getColour())
            allMoves.add( new Position(row + 1, column + 1));

        if(column - 1 >= 0 && board[row][column - 1] instanceof Pawn && board[row][column - 1].getColour() && ((Pawn) board[row][column - 1]).lastMoveWas2)
            allMoves.add(new Position(row + 1, column - 1));

        if(column + 1 < 8 && board[row][column + 1] instanceof Pawn && board[row][column + 1].getColour() && ((Pawn) board[row][column + 1]).lastMoveWas2)
            allMoves.add(new Position(row + 1, column + 1));

        return allMoves;
    }

    public void setLastMoveWas2(boolean lastMoveWas2) {
        this.lastMoveWas2 = lastMoveWas2;
    }
}
