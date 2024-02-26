package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    boolean moved = false;

    public Rook(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    public Rook(Rook rook) {
        super(rook);
        moved = rook.moved;
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        searchTop(board, allMoves);
        searchBottom(board, allMoves);
        searchRight(board, allMoves);
        searchLeft(board, allMoves);

        return allMoves;
    }

    public void searchBottom(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row + i >= 8)
                return;

            Piece piece = board[row + i][column];

            if(piece != null && piece.colour != colour) {

                Position position = new Position(row + i, column);
                moves.add(position);

                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached this then it means piece is null

            Position position = new Position(row + i, column);
            moves.add(position);
        }
    }

    public void searchTop(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row - i < 0)
                return;

            Piece piece = board[row - i][column];

            if(piece != null && piece.colour != colour) {

                Position position = new Position(row - i, column);
                moves.add(position);

                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached this then it means piece is null

            Position position = new Position(row - i, column);
            moves.add(position);
        }
    }

    public void searchRight(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(column + i >= 8)
                return;

            Piece piece = board[row][column + i];

            if(piece != null && piece.colour != colour) {

                Position position = new Position(row, column + i);
                moves.add(position);

                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached this then it means piece is null

            Position position = new Position(row, column + i);
            moves.add(position);
        }
    }

    public void searchLeft(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(column - i < 0)
                return;

            Piece piece = board[row][column - i];

            if(piece != null && piece.colour != colour) {

                Position position = new Position(row, column - i);
                moves.add(position);

                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached this then it means piece is null

            Position position = new Position(row, column - i);
            moves.add(position);
        }
    }

    @Override
    public void move(int newRow, int newColumn) {

        moved = true;

        row = newRow;
        column = newColumn;
    }

    public boolean isMoved() {
        return moved;
    }
}
