package pl.edu.agh.webChess.communication;

public class GameInfo {

    private String info;
    private String username;

    GameInfo() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameInfo(String info) {
        this.info = info;
    }

    public GameInfo(String info, String username) {
        this.info = info;
        this.username = username;
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
                ", username='" + username + '\'' +
                '}';
    }
}
