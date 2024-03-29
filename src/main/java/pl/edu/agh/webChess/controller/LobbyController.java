package pl.edu.agh.webChess.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.game.room.Room;
import pl.edu.agh.webChess.game.room.RoomManager;
import pl.edu.agh.webChess.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/lobby")
public class LobbyController {
    private RoomManager roomManager;
    private UserService userService;

    public LobbyController(UserService userService, RoomManager roomManager) {
        this.roomManager = roomManager;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showLobbyPage(Model model) {

        List<Room> rooms = roomManager.getRooms();

        model.addAttribute("rooms", rooms);

        return "lobby-page";
    }

    @GetMapping("/createRoom")
    public String showCreationOfRoomForm(Model model) {

        model.addAttribute("room", new Room());

        return "create-room-form";
    }

    @PostMapping("/processCreationOfRoom")
    public String processCreationOfRoom(
            @ModelAttribute("room") Room room
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findUserByName(userName);

        try {
            Room theRoom = roomManager.createRoom(room, user);

            return "redirect:/game/" + room.getCode();
        }
        catch (Exception e) {
            return "redirect:/lobby/?error";
        }
    }
}
