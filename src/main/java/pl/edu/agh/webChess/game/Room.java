package pl.edu.agh.webChess.game;

public class Room {

    private int code;
    private String owner;
    private String guest;
    private RoomTime time;
    private Status status;
    private String password;

    public Room() {}

    public int getCode() {
        return code;
    }

    public String getOwner() {
        return owner;
    }

    public String getGuest() {
        return guest;
    }

    public RoomTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setTime(RoomTime time) {
        this.time = time;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
