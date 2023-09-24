package pl.edu.agh.webChess.game;

import org.springframework.stereotype.Component;
import pl.edu.agh.webChess.entity.User;

import java.util.*;

@Component
public class RoomManager {

    private List<Room> rooms = new ArrayList<>();
    private Set<Integer> usedCodes = new HashSet<>();
    private Set<String> activeUsers = new HashSet<>();
    private Random random = new Random();
    private int minCode = 1000;
    private int maxCode = 9999;

    public Room createRoom(Room room, User user) {

        if(activeUsers.contains(user.getUserName()))
            throw new RuntimeException("You are in another room now!");

        room.setCode(generateUniqueCode());
        room.setOwner(user);
        room.setStatus(Status.SEARCHING);

        activeUsers.add(user.getUserName());
        usedCodes.add(room.getCode());
        rooms.add(room);

        return room;
    }

    public void removeRoom(Room room) {

        activeUsers.remove(room.getOwner().getUserName());

        User guest = room.getGuest();

        if(guest != null)
            activeUsers.remove(guest.getUserName());

        usedCodes.remove(room.getCode());
        rooms.remove(room);
    }

    public Room joinRoom(int roomCode, User guest) {

        Room room = this.getRoom(roomCode);

        if(room == null)
            return null;

        if(activeUsers.contains(guest.getUserName()))
            throw new RuntimeException("You are already in the room!");

        if(room.getGuest() != null) {
            throw new RuntimeException("This room is full!");
        }

        room.setStatus(Status.SEARCHING);
        room.setGuest(guest);
        activeUsers.add(guest.getUserName());

        return room;
    }

    private int generateUniqueCode() {

        int randomCode = random.nextInt(maxCode - minCode) + minCode;

        while(usedCodes.contains(randomCode)) {
            randomCode = random.nextInt(maxCode - minCode) + minCode;
        }

        return randomCode;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int code) {

        for(Room room: rooms) {

            if(room.getCode() == code)
                return room;
        }

        return null;
    }
}
