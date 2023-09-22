package pl.edu.agh.webChess.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.game.RoomManager;
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
    public String showRoomPage(@PathVariable String roomNumber) {

        //delete this
        System.out.println(roomManager.getRoom(Integer.parseInt(roomNumber)));

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

        if(roomManager.joinRoom(Integer.parseInt(roomNumber), user) != null)
            return ResponseEntity.ok(protocolAndHost + contextPath + "/game/" + roomNumber);
        else
            return ResponseEntity.ok("null");
    }
}
