package pl.edu.agh.webChess.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.game.Room;
import pl.edu.agh.webChess.game.RoomManager;
import pl.edu.agh.webChess.game.Status;
import pl.edu.agh.webChess.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    private RoomManager roomManager;
    private UserService userService;
    private HttpServletRequest request;

    public GameController(UserService userService, RoomManager roomManager, HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
        this.userService = userService;
        this.roomManager = roomManager;
    }

    @GetMapping("/{roomNumber}")
    public String showRoomPage(
            @PathVariable String roomNumber,
            Model model
    ) {

        Room room = roomManager.getRoom(Integer.parseInt(roomNumber));

        if(room == null)
            return "authentication/access-denied";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User owner = room.getOwner();
        User guest = room.getGuest();

        String ownerName = null;
        String guestName = null;

        if(owner != null)
            ownerName = owner.getUserName();

        if(guest != null)
            guestName = guest.getUserName();

        int intRoomNumber = Integer.parseInt(roomNumber);

        if(userName.equals(ownerName)) {
            model.addAttribute("user", ownerName);
            model.addAttribute("opponent", guestName);
            model.addAttribute("userStatusColour", room.isOwnerReady() ? "green" : "red");
            model.addAttribute("opponentStatusColour", room.isGuestReady() ? "green" : "red");
            model.addAttribute("userReady", room.isOwnerReady());
        }
        else if(userName.equals(guestName)) {
            model.addAttribute("opponent", ownerName);
            model.addAttribute("user", guestName);
            model.addAttribute("opponentStatusColour", room.isOwnerReady() ? "green" : "red");
            model.addAttribute("userStatusColour", room.isGuestReady() ? "green" : "red");
            model.addAttribute("userReady", room.isGuestReady());
        }
        else {
            return "authentication/access-denied";
        }

        List<String> chat = room.getChat();
        model.addAttribute("chat", chat);

        Status roomStatus = roomManager.getRoomStatus(intRoomNumber);
        model.addAttribute("roomStatus", roomStatus);

        boolean isConnectionEstablished = roomManager.infoAboutConnection(intRoomNumber);
        model.addAttribute("connectionInfo", isConnectionEstablished);

        return "room-page";
    }

    @PutMapping ("/{roomNumber}")
    public ResponseEntity<String> joinTheRoom(
            @PathVariable String roomNumber
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findUserByName(userName);

        String contextPath = request.getContextPath();
        String protocolAndHost = "http://localhost:8080";

        try {
            Room room = roomManager.joinRoom(Integer.parseInt(roomNumber), user);

            if(room != null)
                return ResponseEntity.ok(protocolAndHost + contextPath + "/game/" + roomNumber);
            else
                return ResponseEntity.ok("null");

        }
        catch(Exception e) {

            if(e.getMessage().equals("This room is full!"))
                return ResponseEntity.ok(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
