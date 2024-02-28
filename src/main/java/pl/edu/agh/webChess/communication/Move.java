package pl.edu.agh.webChess.communication;

import pl.edu.agh.webChess.game.chess.auxiliary.Position;

public class Move {

    Position from;
    Position to;

    boolean colour;

    public boolean isColour() {
        return colour;
    }

    public void setColour(boolean colour) {
        this.colour = colour;
    }

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

    public void reverse() {

        from.setRow(7 - from.getRow());
        from.setCol(7 - from.getCol());

        to.setRow(7 - to.getRow());
        to.setCol(7 - to.getCol());
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                ", colour=" + colour +
                '}';
    }
}
