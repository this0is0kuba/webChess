package pl.edu.agh.webChess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.webChess.game.Room;
import pl.edu.agh.webChess.game.RoomManager;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    @GetMapping("/")
    public String showLobbyPage(Model model) {

        model.addAttribute("rooms", RoomManager.getRooms());

        return "lobby-page";
    }

    @GetMapping("/createRoom")
    public String showCreationOfRoomForm(Model model) {
        model.addAttribute("room", new Room());

        return "create-room-form";
    }

    @PostMapping("/processCreationOfRoom")
    public String processCreationOfRoom() {


        return "lobby-page";
    }

}
