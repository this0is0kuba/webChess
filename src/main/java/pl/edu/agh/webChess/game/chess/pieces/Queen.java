package pl.edu.agh.webChess.game.chess.pieces;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public String name = "QUEEN";
    public Queen(boolean colour, int row, int column) {
        super(colour, row, column);
    }

    public Queen(Queen queen) {
        super(queen);
    }

    @Override
    public List<Position> getAllMoves(Piece[][] board) {

        List<Position> allMoves = new ArrayList<>();

        searchTop(board, allMoves);
        searchBottom(board, allMoves);
        searchRight(board, allMoves);
        searchLeft(board, allMoves);
        searchTopLeft(board, allMoves);
        searchTopRight(board, allMoves);
        searchBottomRight(board, allMoves);
        searchBottomLeft(board, allMoves);

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

        for (int i = 1; i < 8; i++) {

            if (column - i < 0)
                return;

            Piece piece = board[row][column - i];

            if (piece != null && piece.colour != colour) {

                Position position = new Position(row, column - i);
                moves.add(position);

                return;
            }

            if (piece != null && piece.colour == colour)
                return;

            // if reached this then it means piece is null

            Position position = new Position(row, column - i);
            moves.add(position);
        }
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
