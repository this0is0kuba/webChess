package pl.edu.agh.webChess.game.chess.auxiliary;

import pl.edu.agh.webChess.game.chess.pieces.*;

public class MateFunctions {

    // change this and do all copy of the board
    public static boolean isYourKingMatedAfterYourMove(Piece[][] board, Position from, Position to) {

        boolean opponentColour = !board[from.getRow()][from.getCol()].getColour();

        Piece[][] newBoard = new Piece[8][8];

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                newBoard[i][j] = board[i][j];

        Piece yourPiece = newBoard[from.getRow()][from.getCol()];
        Piece opponentPiece = newBoard[to.getRow()][to.getCol()];

        Piece yourPieceCopy = copyPiece(yourPiece);

        newBoard[to.getRow()][to.getCol()] = yourPieceCopy;
        newBoard[from.getRow()][from.getCol()] = null;

        yourPieceCopy.move(to.getRow(), to.getCol());

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)

                if(newBoard[i][j] != null && newBoard[i][j].getColour() == opponentColour && newBoard[i][j].checkAttackOnKing(newBoard)) {

                    newBoard[from.getRow()][from.getCol()] = yourPiece;
                    newBoard[to.getRow()][to.getCol()] = opponentPiece;
                    return true;
                }

        newBoard[from.getRow()][from.getCol()] = yourPieceCopy;
        newBoard[to.getRow()][to.getCol()] = opponentPiece;
        return false;
    }

    // Method works only when King And Rook haven't moved and is free space between them
    public static boolean checkIfCastlingIsPossible(Piece[][] board, King king, Rook rook) {

        if(isYourKingMatedNow(board, king.getColour()) || isYourRookAttackedNow(board, rook, rook.getColour()))
            return false;

        int k;

        if(king.getColumn() - rook.getColumn() > 0)
            k = -1;
        else
            k = 1;

        if(isYourKingMatedAfterYourMove(board, new Position(king.getRow(), king.getColumn()), new Position(king.getRow(), king.getColumn() + k)))
            return false;

        if(isYourKingMatedAfterYourMove(board, new Position(king.getRow(), king.getColumn()), new Position(king.getRow(), king.getColumn() + 2*k)))
            return false;

        return true;
    }

    public static boolean isYourKingMatedNow(Piece[][] board, boolean yourColour) {

        boolean opponentColour = !yourColour;

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)

                if(board[i][j] != null && board[i][j].getColour() == opponentColour && !(board[i][j] instanceof King) && board[i][j].checkAttackOnKing(board)) {
                    return true;
                }

        return false;
    }

    public static boolean isYourRookAttackedNow(Piece[][] board, Rook rook, boolean yourColour) {

        boolean opponentColour = !yourColour;

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)

                if(board[i][j] != null && board[i][j].getColour() == opponentColour && board[i][j].checkAttackOnRook(board, rook)) {
                    return true;
                }

        return false;
    }

    private static Piece copyPiece(Piece piece) {

        if(piece == null)
            return null;

        if(piece instanceof Bishop)
            return new Bishop( (Bishop) piece);

        if(piece instanceof King)
            return new King( (King) piece);

        if(piece instanceof Knight)
            return new Knight( (Knight) piece);

        if(piece instanceof Pawn)
            return new Pawn( (Pawn) piece);

        if(piece instanceof Queen)
            return new Queen( (Queen) piece);

        return new Rook( (Rook) piece);
    }
}
