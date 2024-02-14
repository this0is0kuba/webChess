package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoard() {
        emptyBoard = new Board(true);
    }

    @Test
    void checkIfMovesArePossible() {

        Piece queen = new Queen(true, 6, 3);
        emptyBoard.setPiece(queen);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = queen.getPossibleMoves(pieces);

        assertEquals(23, possibleMoves.size());
    }

    @Test
    void checkCorrectMoves() {

        Piece queen = new Queen(true, 6, 3);
        Piece queen2 = new Queen(true, 5, 3);
        Piece queen3 = new Queen(false, 5, 4);

        emptyBoard.setPiece(queen);
        emptyBoard.setPiece(queen2);
        emptyBoard.setPiece(queen3);

        Piece[][] pieces = emptyBoard.getPieces();

        List<Position> possibleMoves = queen.getPossibleMoves(pieces);
        List<Position> possibleMoves2 = queen2.getPossibleMoves(pieces);
        List<Position> possibleMoves3 = queen3.getPossibleMoves(pieces);

        assertEquals(14, possibleMoves.size());
        assertEquals(20, possibleMoves2.size());
        assertEquals(21, possibleMoves3.size());
    }

}