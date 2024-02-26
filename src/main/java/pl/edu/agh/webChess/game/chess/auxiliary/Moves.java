package pl.edu.agh.webChess.game.chess.auxiliary;

import java.util.List;

public class Moves {

    Position from;
    List<Position> to;

    public Moves(Position from, List<Position> to) {
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public List<Position> getTo() {
        return to;
    }
}
