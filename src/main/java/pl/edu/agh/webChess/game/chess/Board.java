package pl.edu.agh.webChess.game.chess;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;
import pl.edu.agh.webChess.game.chess.pieces.*;

public class Board {

    private Piece[][] pieces = new Piece[8][8];

    public Board(boolean emptyBoard) {

        if(emptyBoard)
            return;

        pieces[0][0] = new Rook(false, 0, 0);
        pieces[0][1] = new Knight(false, 0, 1);
        pieces[0][2] = new Bishop(false, 0, 2);
        pieces[0][3] = new Queen(false, 0, 3);
        pieces[0][4] = new King(false, 0, 4);
        pieces[0][5] = new Bishop(false, 0, 5);
        pieces[0][6] = new Knight(false, 0, 6);
        pieces[0][7] = new Rook(false, 0, 7);

        for(int i = 0; i < 8; i++)
            pieces[1][i] = new Pawn(false, 1, i);

        pieces[7][0] = new Rook(true, 7, 0);
        pieces[7][1] = new Knight(true, 7, 1);
        pieces[7][2] = new Bishop(true, 7, 2);
        pieces[7][3] = new Queen(true, 7, 3);
        pieces[7][4] = new King(true, 7, 4);
        pieces[7][5] = new Bishop(true, 7, 5);
        pieces[7][6] = new Knight(true, 7, 6);
        pieces[7][7] = new Rook(true, 7, 7);

        for(int i = 0; i < 8; i++)
            pieces[6][i] = new Pawn(true, 6, i);
    }

    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }

    public void setPiece(Piece piece) {
        pieces[piece.getRow()][piece.getColumn()] = piece;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void movePiece(Position from, Position to) {

        Piece piece = pieces[from.getRow()][from.getCol()];
        pieces[to.getRow()][to.getCol()] = piece;
        pieces[from.getRow()][from.getCol()] = null;

        piece.move(to.getRow(), to.getCol());
    }
}
