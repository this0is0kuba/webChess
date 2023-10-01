package pl.edu.agh.webChess.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.game.Room;
import pl.edu.agh.webChess.game.RoomManager;
import pl.edu.agh.webChess.model.Message;
import pl.edu.agh.webChess.service.UserService;

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
    ) throws NoHandlerFoundException {

        Room room = roomManager.getRoom(Integer.parseInt(roomNumber));

        if(room == null)
            throw new NoHandlerFoundException("GET", "/" + roomNumber, null);

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


        if(userName.equals(ownerName)) {
            model.addAttribute("user", ownerName);
            model.addAttribute("opponent", guestName);
        }
        else if(userName.equals(guestName)) {
            model.addAttribute("opponent", ownerName);
            model.addAttribute("user", guestName);
        }
        else {
            return "authentication/access-denied";
        }

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

    @MessageMapping("game/room/{roomNumber}/chat")
    @SendTo("/topic/room/{roomNumber}/chat")
    public Message handleMessage(Message message) {

        return new Message(message.getFrom(), message.getContent());
    }
}
