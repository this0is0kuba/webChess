package pl.edu.agh.webChess.game;

import java.time.LocalTime;

public class Room {

    private int number;
    private String owner;
    private String guest;
    private LocalTime time;
    private Status status;

    public Room(String owner, int number, LocalTime time) {

        this.number = number;
        this.owner = owner;
        this.time = time;
        this.status = Status.SEARCHING;
    }

    public int getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public String getGuest() {
        return guest;
    }

    public LocalTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
