package pl.edu.agh.webChess.game;

import org.springframework.stereotype.Component;
import pl.edu.agh.webChess.entity.User;

import java.util.*;

@Component
public class RoomManager {
    private List<Room> rooms = new ArrayList<>();
    private Set<Integer> usedCodes = new HashSet<>();
    private Random random = new Random();
    private int minCode = 1000;
    private int maxCode = 9999;

    public Room createRoom(Room room, User user) {

        room.setCode(generateUniqueCode());
        room.setOwner(user);
        room.setStatus(Status.SEARCHING);

        usedCodes.add(room.getCode());
        rooms.add(room);
        return room;
    }

    public void removeRoom(Room room) {

        usedCodes.remove(room.getCode());
        rooms.remove(room);
    }

    public Room joinRoom(int roomCode, User guest) {

        for(Room room : rooms) {

            if(room.getCode() == roomCode) {

                room.setGuest(guest);
                room.setStatus(Status.WAITING);
            }

            return room;
        }

        return null;
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
}
