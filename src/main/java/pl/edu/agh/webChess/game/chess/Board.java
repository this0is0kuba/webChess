package pl.edu.agh.webChess.game.chess;

import pl.edu.agh.webChess.game.chess.auxiliary.Moves;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;
import pl.edu.agh.webChess.game.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.webChess.game.chess.auxiliary.MateFunctions.checkIfCastlingIsPossible;

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

    public Piece[][] getReversedPieces() {

        Piece[][] reversedPieces = new Piece[8][8];

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {
                reversedPieces[i][j] = pieces[7 - i][j];
            }

        return reversedPieces;
    }

    public void movePiece(Position from, Position to) {

        changeAllPawnsStatus();

        Piece piece = pieces[from.getRow()][from.getCol()];
        pieces[to.getRow()][to.getCol()] = piece;
        pieces[from.getRow()][from.getCol()] = null;

        piece.move(to.getRow(), to.getCol());
    }

    private void changeAllPawnsStatus() {

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)

                if(pieces[i][j] != null && pieces[i][j] instanceof Pawn)
                    ((Pawn) pieces[i][j]).setLastMoveWas2AsFalse();
    }

    public List<Moves> getAllPossibleMoves(boolean colour) {

        List<Moves> allPossibleMoves = new ArrayList<>();

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {

                Piece piece = pieces[i][j];

                if(piece != null && piece.getColour() == colour) {

                    Position from = new Position(i, j);
                    List<Position> possiblePositions = new ArrayList<>(pieces[i][j].getPossibleMoves(pieces));

                    if(piece instanceof King)
                        addCastling((King) piece, possiblePositions);

                    Moves moves = new Moves(from, possiblePositions);
                    allPossibleMoves.add(moves);
                }
            }

        return allPossibleMoves;
    }

    private void addCastling(King king, List<Position> possiblePositions) {

        if(king.isMoved())
            return;

        int row = king.getRow();
        int column = king.getColumn();
        boolean theColour = king.getColour();

        // check left rook

        if(pieces[row][column - 1] == null && pieces[row][column - 2] == null && pieces[row][column - 3] == null) {

            if(theColour) {
                if (pieces[7][0] instanceof Rook rook) {
                    if (!rook.isMoved() && checkIfCastlingIsPossible(pieces, king, rook))
                        possiblePositions.add(new Position(row, column - 2));
                }
            }
            else {
                if (pieces[0][0] instanceof Rook rook) {
                    if (!rook.isMoved() && checkIfCastlingIsPossible(pieces, king, rook))
                        possiblePositions.add(new Position(row, column - 2));
                }
            }

        }

        // check right rook

        if(pieces[row][column + 1] == null && pieces[row][column + 2] == null) {

            if(theColour) {
                if (pieces[7][7] instanceof Rook rook) {
                    if (!rook.isMoved() && checkIfCastlingIsPossible(pieces, king, rook))
                        possiblePositions.add(new Position(row, column + 2));
                }
            }
            else {
                if (pieces[0][7] instanceof Rook rook) {
                    if (!rook.isMoved() && checkIfCastlingIsPossible(pieces, king, rook))
                        possiblePositions.add(new Position(row, column + 2));
                }
            }

        }
    }
}
