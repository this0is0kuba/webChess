package pl.edu.agh.webChess.communication;

import pl.edu.agh.webChess.game.chess.auxiliary.Moves;

import java.util.List;

public class AllMoves {

    List<Moves> possibleMoves;
    Move lastMove;
    boolean colour;
    int infoAboutEndGame = 2; // 0 - lose, 1 - draw, 2 - game is on
    long time;

    public AllMoves(List<Moves> possibleMoves, Move lastMove, boolean colour) {
        this.possibleMoves = possibleMoves;
        this.lastMove = lastMove;
        this.colour = colour;
    }

    public AllMoves(List<Moves> possibleMoves, Move lastMove, boolean colour, int infoAboutEndGame, long time) {
        this.possibleMoves = possibleMoves;
        this.lastMove = lastMove;
        this.colour = colour;
        this.infoAboutEndGame = infoAboutEndGame;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public int getInfoAboutEndGame() {
        return infoAboutEndGame;
    }

    public void setInfoAboutEndGame(int infoAboutEndGame) {
        this.infoAboutEndGame = infoAboutEndGame;
    }
}
