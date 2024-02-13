package pl.edu.agh.webChess.game.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.webChess.game.chess.auxiliary.Position;
import pl.edu.agh.webChess.game.chess.pieces.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board emptyBoard;
    private Board initialBoard;

    @BeforeEach
    void createNewInstanceOfBoards() {

        emptyBoard = new Board(true);
        initialBoard = new Board(false);
    }

    @Test
    void checkEmptyBoard() {

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                assertNull(emptyBoard.getPiece(i, j));
    }

    @Test
    void checkInitialBoard() {

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                assertNotNull(initialBoard.getPiece(i, j));

        for(int i = 2; i < 6; i++)
            for(int j = 2; j < 6; j++)
                assertNull(initialBoard.getPiece(i, j));

        for(int i = 6; i < 8; i++)
            for(int j = 6; j < 8; j++)
                assertNotNull(initialBoard.getPiece(i, j));
    }

    @Test
    void checkInitialBoard2() {

        assertInstanceOf(Rook.class, initialBoard.getPiece(0, 0));
        assertInstanceOf(Knight.class, initialBoard.getPiece(0, 1));
        assertInstanceOf(Bishop.class, initialBoard.getPiece(0, 2));
        assertInstanceOf(Queen.class, initialBoard.getPiece(0, 3));
        assertInstanceOf(King.class, initialBoard.getPiece(0, 4));
        assertInstanceOf(Bishop.class, initialBoard.getPiece(0, 5));
        assertInstanceOf(Knight.class, initialBoard.getPiece(0, 6));
        assertInstanceOf(Rook.class, initialBoard.getPiece(0, 7));

        for(int i = 0; i < 8; i++)
            assertInstanceOf(Pawn.class, initialBoard.getPiece(1, i));
    }

    @Test
    void setPiece() {

        Pawn pawn = new Pawn(true, 6, 3);
        emptyBoard.setPiece(pawn, 3, 3);

        assertInstanceOf(Pawn.class, emptyBoard.getPiece(3, 3));
    }

    @Test
    void movePiece() {

        Position from = new Position(6, 3);
        Position to = new Position(4, 3);
        initialBoard.movePiece(from , to);

        assertNull(initialBoard.getPiece(6, 3));
        assertNotNull(initialBoard.getPiece(4, 3));
    }
}