package pl.edu.agh.webChess.game.room;

public enum RoomTime {
    M1("1 minute"),
    M3("3 minutes"),
    M5("5 minutes"),
    M10("10 minutes"),
    M20("20 minutes"),
    M30("30 minutes"),
    H1("1 hour");

    private final String label;

    RoomTime(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}