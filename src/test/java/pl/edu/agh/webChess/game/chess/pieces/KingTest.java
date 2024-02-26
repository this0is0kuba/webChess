package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoard() {
        emptyBoard = new Board(true);
    }

    @Test
    void checkIfMovesArePossible() {

        Piece king = new King(true, 6, 3);
        emptyBoard.setPiece(king);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = king.getPossibleMoves(pieces);

        assertEquals(8, possibleMoves.size());
    }

    @Test
    void checkCorrectMoves() {

        Piece king = new King(true, 7, 4);
        Piece king2 = new King(false, 0, 4);

        king.move(7, 0);
        king2.move(5, 1);

        emptyBoard.setPiece(king);
        emptyBoard.setPiece(king2);

        Piece[][] pieces = emptyBoard.getPieces();

        List<Position> possibleMoves = king.getPossibleMoves(pieces);
        List<Position> possibleMoves2 = king2.getPossibleMoves(pieces);

        assertEquals(1, possibleMoves.size());
        assertEquals(6, possibleMoves2.size());
    }
}