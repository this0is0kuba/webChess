package pl.edu.agh.webChess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.webChess.game.RoomManager;

@Controller
@RequestMapping("/game")
public class GameController {

    private RoomManager roomManager;

    public GameController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @GetMapping("/{roomNumber}")
    public String showRoomPage(@PathVariable String roomNumber) {

        System.out.println(roomManager.getRooms());

        return "room-page";
    }
}
