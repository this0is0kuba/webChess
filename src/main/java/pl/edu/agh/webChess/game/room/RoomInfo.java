package pl.edu.agh.webChess.game.room;

public class RoomInfo {

    private int roomNumber;
    private String username;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "roomNumber=" + roomNumber +
                ", username='" + username + '\'' +
                '}';
    }
}
