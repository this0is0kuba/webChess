package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoard() {
        emptyBoard = new Board(true);
    }

    @Test
    void checkIfMovesArePossible() {

        Piece bishop = new Bishop(true, 6, 3);
        emptyBoard.setPiece(bishop);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[6][3].getPossibleMoves(pieces);

        assertEquals(9, possibleMoves.size());
    }

    @Test
    void checkCorrectMoves() {

        Piece bishop = new Bishop(true, 6, 3);
        Piece bishop2 = new Bishop(false, 7, 2);
        Piece bishop3 = new Bishop(true, 3, 6);

        emptyBoard.setPiece(bishop);
        emptyBoard.setPiece(bishop2);
        emptyBoard.setPiece(bishop3);

        Piece[][] pieces = emptyBoard.getPieces();

        List<Position> possibleMoves = bishop.getPossibleMoves(pieces);
        List<Position> possibleMoves2 = bishop2.getPossibleMoves(pieces);
        List<Position> possibleMoves3 = bishop3.getPossibleMoves(pieces);

        assertEquals(7, possibleMoves.size());
        assertEquals(3, possibleMoves2.size());
        assertEquals(7, possibleMoves3.size());
    }

}