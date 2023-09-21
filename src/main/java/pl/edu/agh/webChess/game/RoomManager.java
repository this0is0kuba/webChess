package pl.edu.agh.webChess.game;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoomManager {
    private static List<Room> rooms = new ArrayList<>();
    private static Set<Integer> usedCodes = new HashSet<>();
    private static Random random = new Random();
    private static int minCode = 1000;
    private static int maxCode = 9999;

    public static Room createRoom(Room room) {

        room.setCode(generateUniqueCode());

        usedCodes.add(room.getCode());
        rooms.add(room);
        return room;
    }

    public static void removeRoom(Room room) {

        usedCodes.remove(room.getCode());
        rooms.remove(room);
    }

    public static Room joinRoom(String userName, int roomCode) {

        for(Room room : rooms) {

            if(room.getCode() == roomCode) {

                room.setGuest(userName);
                room.setStatus(Status.WAITING);
            }

            return room;
        }

        return null;
    }

    private static int generateUniqueCode() {

        int randomCode = random.nextInt(maxCode - minCode) + minCode;

        while(usedCodes.contains(randomCode)) {
            randomCode = random.nextInt(maxCode - minCode) + minCode;
        }

        return randomCode;
    }

    public static List<Room> getRooms() {
        return rooms;
    }
}
