package pl.edu.agh.webChess.game;

import pl.edu.agh.webChess.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int code;
    private User owner;
    private User guest;
    private RoomTime time;
    private Status status;
    private String password;
    private boolean isWhite;
    private List<String> chat = new ArrayList<>();
    private boolean ownerReady;
    private boolean guestReady;

    public Room() {}

    public boolean isOwnerReady() {
        return ownerReady;
    }

    public void setOwnerReady(boolean ownerReady) {
        this.ownerReady = ownerReady;
    }

    public boolean isGuestReady() {
        return guestReady;
    }

    public void setGuestReady(boolean guestReady) {
        this.guestReady = guestReady;
    }

    public int getCode() {
        return code;
    }

    public User getOwner() {
        return owner;
    }

    public User getGuest() {
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

    public boolean getIsWhite() {
        return isWhite;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setGuest(User guest) {
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

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void addMessage(String message) {
        chat.add(message);
    }

    public List<String> getChat() {
        return chat;
    }

    @Override
    public String toString() {

        String guestString = (guest != null) ? guest.getUserName() : "null";

        return "Room{" +
                "code=" + code +
                ", owner=" + owner.getUserName() +
                ", guest=" + guestString +
                ", time=" + time +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", isWhite=" + isWhite +
                '}';
    }
}
