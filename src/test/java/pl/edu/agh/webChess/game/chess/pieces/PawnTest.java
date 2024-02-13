package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoards() {

        emptyBoard = new Board(true);
    }

    @Test
    void checkIfTwoMovesArePossible() {

        Pawn pawn = new Pawn(true, 6, 3);
        emptyBoard.setPiece(pawn, 6, 3);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[6][3].getPossibleMoves(pieces);

        assertEquals(2, possibleMoves.size());

        assertEquals(5, possibleMoves.get(0).getRow());
        assertEquals(3, possibleMoves.get(0).getCol());

        assertEquals(4, possibleMoves.get(1).getRow());
        assertEquals(3, possibleMoves.get(1).getCol());
    }

    @Test
    void checkCapture1() {

        Pawn pawn = new Pawn(true, 6, 3);
        emptyBoard.setPiece(pawn, 6, 3);

        Pawn opponentPawn1 = new Pawn(false, 5, 2);
        Pawn opponentPawn2 = new Pawn(false, 5, 4);

        emptyBoard.setPiece(opponentPawn1, 5, 2);
        emptyBoard.setPiece(opponentPawn2, 5, 4);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[6][3].getPossibleMoves(pieces);

        assertEquals(4, possibleMoves.size());

        assertEquals(5, possibleMoves.get(0).getRow());
        assertEquals(3, possibleMoves.get(0).getCol());

        assertEquals(4, possibleMoves.get(1).getRow());
        assertEquals(3, possibleMoves.get(1).getCol());

        assertEquals(5, possibleMoves.get(2).getRow());
        assertEquals(2, possibleMoves.get(2).getCol());

        assertEquals(5, possibleMoves.get(3).getRow());
        assertEquals(4, possibleMoves.get(3).getCol());
    }

    @Test
    void checkCapture2() {

        Pawn pawn = new Pawn(true, 6, 3);
        emptyBoard.setPiece(pawn, 6, 3);

        Pawn opponentPawn = new Pawn(false, 5, 2);
        Pawn yourPawn = new Pawn(true, 5, 4);

        emptyBoard.setPiece(opponentPawn, 5, 2);
        emptyBoard.setPiece(yourPawn, 5, 4);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[6][3].getPossibleMoves(pieces);

        assertEquals(3, possibleMoves.size());

        assertEquals(5, possibleMoves.get(0).getRow());
        assertEquals(3, possibleMoves.get(0).getCol());

        assertEquals(4, possibleMoves.get(1).getRow());
        assertEquals(3, possibleMoves.get(1).getCol());

        assertEquals(5, possibleMoves.get(2).getRow());
        assertEquals(2, possibleMoves.get(2).getCol());
    }

    @Test
    void checkCapture3() {

        Pawn pawn = new Pawn(true, 5, 0);
        emptyBoard.setPiece(pawn, 5, 0);

        Pawn opponentPawn = new Pawn(false, 4, 1);
        emptyBoard.setPiece(opponentPawn, 4, 1);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[5][0].getPossibleMoves(pieces);

        assertEquals(2, possibleMoves.size());

        assertEquals(4, possibleMoves.get(0).getRow());
        assertEquals(0, possibleMoves.get(0).getCol());

        assertEquals(4, possibleMoves.get(1).getRow());
        assertEquals(1, possibleMoves.get(1).getCol());
    }

    @Test
    void checkEnPassantCapture() {

        emptyBoard.setPiece(new Pawn(false, 1, 4), 1, 4);
        Position from2 = new Position(1, 4);
        Position to2 = new Position(4, 4);
        emptyBoard.movePiece(from2, to2);

        emptyBoard.setPiece(new Pawn(true, 6, 3), 6, 3);
        Position from = new Position(6, 3);
        Position to = new Position(4, 3);
        emptyBoard.movePiece(from, to);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[4][4].getPossibleMoves(pieces);

        assertEquals(2, possibleMoves.size());

        assertEquals(5, possibleMoves.get(0).getRow());
        assertEquals(4, possibleMoves.get(0).getCol());

        assertEquals(5, possibleMoves.get(1).getRow());
        assertEquals(3, possibleMoves.get(1).getCol());
    }


}