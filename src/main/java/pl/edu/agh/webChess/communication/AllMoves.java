package pl.edu.agh.webChess.communication;

import pl.edu.agh.webChess.game.chess.auxiliary.Moves;

import java.util.List;

public class AllMoves {

    List<Moves> possibleMoves;
    Move lastMove;
    boolean colour;

    public AllMoves(List<Moves> possibleMoves, Move lastMove, boolean colour) {
        this.possibleMoves = possibleMoves;
        this.lastMove = lastMove;
        this.colour = colour;
    }

    public List<Moves> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Moves> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public boolean isColour() {
        return colour;
    }

    public void setColour(boolean colour) {
        this.colour = colour;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }
}
