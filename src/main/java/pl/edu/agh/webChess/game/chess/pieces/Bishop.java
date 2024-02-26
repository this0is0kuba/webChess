package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    public Bishop(Bishop bishop) {
        super(bishop);
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        searchTopLeft(board, allMoves);
        searchTopRight(board, allMoves);
        searchBottomRight(board, allMoves);
        searchBottomLeft(board, allMoves);

        return allMoves;
    }

    void searchTopLeft(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row - i < 0 || column - i < 0)
                return;

            Piece piece = board[row - i][column - i];

            if(piece != null && piece.colour != colour) {

                moves.add(new Position(row - i, column - i));
                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached here then it means piece is null

            moves.add(new Position(row - i, column - i));
        }
    }

    void searchTopRight(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row - i < 0 || column + i >= 8)
                return;

            Piece piece = board[row - i][column + i];

            if(piece != null && piece.colour != colour) {

                moves.add(new Position(row - i, column + i));
                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached here then it means piece is null

            moves.add(new Position(row - i, column + i));
        }
    }
    void searchBottomRight(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row + i >= 8 || column + i >= 8)
                return;

            Piece piece = board[row + i][column + i];

            if(piece != null && piece.colour != colour) {

                moves.add(new Position(row + i, column + i));
                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached here then it means piece is null

            moves.add(new Position(row + i, column + i));
        }
    }

    void searchBottomLeft(Piece[][] board, List<Position> moves) {

        for(int i = 1; i < 8; i++) {

            if(row + i >= 8 || column - i < 0)
                return;

            Piece piece = board[row + i][column - i];

            if(piece != null && piece.colour != colour) {

                moves.add(new Position(row + i, column - i));
                return;
            }

            if(piece != null && piece.colour == colour)
                return;

            // if reached here then it means piece is null

            moves.add(new Position(row + i, column - i));
        }
    }

    @Override
    public void move(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
    }
}
