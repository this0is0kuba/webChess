package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoard() {
        emptyBoard = new Board(true);
    }

    @Test
    void checkIfMovesArePossible() {

        Piece knight = new Knight(true, 6, 3);
        emptyBoard.setPiece(knight);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = knight.getPossibleMoves(pieces);

        assertEquals(6, possibleMoves.size());
    }

    @Test
    void checkCorrectMoves() {

        Piece knight = new Knight(true, 6, 3);
        Piece knight2 = new Knight(true, 4, 4);
        Piece knight3 = new Knight(false, 7, 1);

        emptyBoard.setPiece(knight);
        emptyBoard.setPiece(knight2);
        emptyBoard.setPiece(knight3);

        Piece[][] pieces = emptyBoard.getPieces();

        List<Position> possibleMoves = knight.getPossibleMoves(pieces);
        List<Position> possibleMoves2 = knight2.getPossibleMoves(pieces);
        List<Position> possibleMoves3 = knight3.getPossibleMoves(pieces);

        assertEquals(5, possibleMoves.size());
        assertEquals(7, possibleMoves2.size());
        assertEquals(3, possibleMoves3.size());
    }
}