package pl.edu.agh.webChess.game.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    private Board emptyBoard;

    @BeforeEach
    void createNewInstanceOfBoard() {
        emptyBoard = new Board(true);
    }

    @Test
    void checkIfMovesArePossible() {

        Piece rook = new Rook(true, 6, 3);
        emptyBoard.setPiece(rook);

        Piece[][] pieces = emptyBoard.getPieces();
        List<Position> possibleMoves = pieces[6][3].getPossibleMoves(pieces);

        assertEquals(14, possibleMoves.size());
    }

    @Test
    void checkCorrectMoves() {

        Piece rook = new Rook(true, 6, 3);
        Piece rook2 = new Rook(true, 6, 4);
        Piece rook3 = new Rook(false, 5, 3);

        emptyBoard.setPiece(rook);
        emptyBoard.setPiece(rook2);
        emptyBoard.setPiece(rook3);

        Piece[][] pieces = emptyBoard.getPieces();

        List<Position> possibleMoves = rook.getPossibleMoves(pieces);
        List<Position> possibleMoves2 = rook2.getPossibleMoves(pieces);
        List<Position> possibleMoves3 = rook3.getPossibleMoves(pieces);

        assertEquals(5, possibleMoves.size());
        assertEquals(10, possibleMoves2.size());
        assertEquals(13, possibleMoves3.size());
    }

}