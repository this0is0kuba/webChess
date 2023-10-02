package pl.edu.agh.webChess.communication;

public class GameInfo {

    private String info;

    GameInfo() {}

    public GameInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "info='" + info + '\'' +
                '}';
    }
}
