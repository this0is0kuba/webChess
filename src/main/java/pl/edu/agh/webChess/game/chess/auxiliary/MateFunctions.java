package pl.edu.agh.webChess.game.chess.auxiliary;

import pl.edu.agh.webChess.game.chess.pieces.Piece;

public class MateFunctions {

    public static boolean isYourKingMatedAfterYourMove(Piece[][] board, Position from, Position to) {

        boolean opponentColour = board[from.getRow()][from.getCol()].getColour();

        Piece[][] newBoard = new Piece[8][8];

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                newBoard[i][j] = board[i][j];

        newBoard[to.getRow()][to.getCol()] = newBoard[from.getRow()][from.getCol()];
        newBoard[from.getRow()][from.getCol()] = null;

        newBoard[to.getRow()][to.getCol()].move(to.getRow(), to.getCol());

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)

                if(newBoard[i][j] != null && newBoard[i][j].getColour() == opponentColour && newBoard[i][j].checkAttackOnKing(newBoard)) {
                    newBoard[to.getRow()][to.getCol()].move(from.getRow(), from.getCol());
                    return true;
                }

        newBoard[to.getRow()][to.getCol()].move(from.getRow(), from.getCol());
        return false;
    }
}
