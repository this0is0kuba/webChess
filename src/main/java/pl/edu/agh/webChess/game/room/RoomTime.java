package pl.edu.agh.webChess.game.room;

public enum RoomTime {

    S5("5 seconds"),
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

    public long convertToMilliseconds() {

        long minute = 1000 * 60;

        return switch (this) {
            case S5 -> minute / 12;
            case M1 -> minute;
            case M3 -> minute * 3;
            case M5 -> minute * 5;
            case M10 -> minute * 10;
            case M20 -> minute * 20;
            case M30 -> minute * 30;
            case H1 -> minute * 60;
        };
    }
}