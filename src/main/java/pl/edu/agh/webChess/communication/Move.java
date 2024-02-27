package pl.edu.agh.webChess.communication;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

public class Move {

    Position from;
    Position to;

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
