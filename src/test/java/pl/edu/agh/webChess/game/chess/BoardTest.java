package pl.edu.agh.webChess.game.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.auxiliary.Moves;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;
import pl.edu.agh.webChess.game.chess.pieces.King;
import pl.edu.agh.webChess.game.chess.pieces.Piece;
import pl.edu.agh.webChess.game.chess.pieces.Rook;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board initialBoard;
    private Board emptyBoard;

    @BeforeEach
    void createNewBoard() {

        initialBoard = new Board(false);
        emptyBoard = new Board(true);
    }

    @Test
    void checkBlackPiecesFirstMoves() {

        Piece[][] pieces = initialBoard.getPieces();

        assertEquals(0, pieces[0][0].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[0][1].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][2].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][3].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][4].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][5].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[0][6].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][7].getPossibleMoves(pieces).size());

        for(int i = 0; i < 8; i++)
            assertEquals(2, pieces[1][i].getPossibleMoves(pieces).size());
    }

    @Test
    void checkWhitePiecesFirstMoves() {

        Piece[][] pieces = initialBoard.getPieces();

        assertEquals(0, pieces[7][0].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[7][1].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[7][2].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[7][3].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[7][4].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[7][5].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[7][6].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[7][7].getPossibleMoves(pieces).size());

        for(int i = 0; i < 8; i++)
            assertEquals(2, pieces[6][i].getPossibleMoves(pieces).size());
    }

    @Test
    void checkCastling() {

        Piece king = new King(true, 7, 4);
        Piece rook = new Rook(true, 7, 0);

        emptyBoard.setPiece(king);
        emptyBoard.setPiece(rook);

        Piece[][] pieces = emptyBoard.getPieces();

        assertEquals(6, king.getPossibleMoves(pieces).size());
        assertEquals(10, rook.getPossibleMoves(pieces).size());

        emptyBoard.movePiece(new Position(7, 4), new Position(7, 5));

        assertEquals(5, king.getPossibleMoves(pieces).size());
        assertEquals(11, rook.getPossibleMoves(pieces).size());
    }

    @Test
    void checkCorrectMovesAfterSomeMoves() {

        initialBoard.movePiece(new Position(6, 4), new Position(4, 4));
        initialBoard.movePiece(new Position(1, 4), new Position(3, 4));
        initialBoard.movePiece(new Position(7, 3), new Position(3, 7));
        initialBoard.movePiece(new Position(0, 1), new Position(2, 2));
        initialBoard.movePiece(new Position(3, 7), new Position(3, 4));

        Piece[][] pieces = initialBoard.getPieces();

        assertEquals(0, pieces[0][0].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][2].getPossibleMoves(pieces).size());
        assertEquals(1, pieces[0][3].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][4].getPossibleMoves(pieces).size());
        assertEquals(1, pieces[0][5].getPossibleMoves(pieces).size());
        assertEquals(1, pieces[0][6].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][7].getPossibleMoves(pieces).size());

        for(int i = 0; i < 8; i++)
            if(i != 4)
                assertEquals(0, pieces[1][i].getPossibleMoves(pieces).size());

        assertEquals(2, pieces[2][2].getPossibleMoves(pieces).size());
    }

    @Test
    void checkCorrectMovesAfterSomeMoves2() {

        initialBoard.movePiece(new Position(6, 4), new Position(4, 4));
        initialBoard.movePiece(new Position(1, 4), new Position(3, 4));
        initialBoard.movePiece(new Position(7, 3), new Position(3, 7));
        initialBoard.movePiece(new Position(0, 1), new Position(2, 2));
        initialBoard.movePiece(new Position(3, 7), new Position(3, 4));

        initialBoard.movePiece(new Position(0, 3), new Position(1, 4));
        initialBoard.movePiece(new Position(6, 3), new Position(5, 3));
        initialBoard.movePiece(new Position(1, 3), new Position(2, 3));
        initialBoard.movePiece(new Position(7, 2), new Position(3, 6));
        initialBoard.movePiece(new Position(0, 2), new Position(4, 6));
        initialBoard.movePiece(new Position(7, 1), new Position(5, 2));

        Piece[][] pieces = initialBoard.getPieces();

        assertEquals(3, pieces[0][0].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[0][4].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][5].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[0][6].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[0][7].getPossibleMoves(pieces).size());

        assertEquals(2, pieces[1][0].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[1][1].getPossibleMoves(pieces).size());
        assertEquals(0, pieces[1][2].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[1][4].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[1][5].getPossibleMoves(pieces).size());
        assertEquals(1, pieces[1][6].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[1][7].getPossibleMoves(pieces).size());

        assertEquals(6, pieces[2][2].getPossibleMoves(pieces).size());
        assertEquals(2, pieces[2][3].getPossibleMoves(pieces).size());

        assertEquals(9, pieces[4][6].getPossibleMoves(pieces).size());

        // check castling for black

        for(Moves moves: initialBoard.getAllPossibleMoves(false)) {

            Position from = moves.getFrom();

            if(from.getRow() == 0 & from.getCol() == 4)
                assertEquals(3, moves.getTo().size());
        }

        // check castling for white

        for(Moves moves: initialBoard.getAllPossibleMoves(true)) {

            Position from = moves.getFrom();

            if(from.getRow() == 7 & from.getCol() == 5)
                assertEquals(1, moves.getTo().size());
        }
    }
}