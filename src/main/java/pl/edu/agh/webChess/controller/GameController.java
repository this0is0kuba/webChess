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
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.pieces.Piece;
import pl.edu.agh.webChess.game.room.Room;
import pl.edu.agh.webChess.game.room.RoomInfo;
import pl.edu.agh.webChess.game.room.RoomManager;
import pl.edu.agh.webChess.game.room.Status;
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
        int intRoomNumber = Integer.parseInt(roomNumber);
        model.addAttribute("roomNumber", roomNumber);

        Room room = roomManager.getRoom(intRoomNumber);

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

        if(userName.equals(ownerName)) {
            model.addAttribute("user", ownerName);
            model.addAttribute("opponent", guestName);
            model.addAttribute("userStatusColour", room.isOwnerReady() ? "green" : "red");
            model.addAttribute("opponentStatusColour", room.isGuestReady() ? "green" : "red");
            model.addAttribute("userReady", room.isOwnerReady());
            model.addAttribute("isWhite", room.getIsWhite());

            Piece[][] pieces;

            if(room.getIsWhite())
                pieces = roomManager.getRoom(intRoomNumber).getBoard().getPieces();
            else
                pieces = roomManager.getRoom(intRoomNumber).getBoard().getReversedPieces();

            model.addAttribute("pieces", pieces);
        }
        else if(userName.equals(guestName)) {
            model.addAttribute("opponent", ownerName);
            model.addAttribute("user", guestName);
            model.addAttribute("opponentStatusColour", room.isOwnerReady() ? "green" : "red");
            model.addAttribute("userStatusColour", room.isGuestReady() ? "green" : "red");
            model.addAttribute("userReady", room.isGuestReady());
            model.addAttribute("isWhite", !room.getIsWhite());

            Piece[][] pieces;

            if(room.getIsWhite())
                pieces = roomManager.getRoom(intRoomNumber).getBoard().getReversedPieces();
            else
                pieces = roomManager.getRoom(intRoomNumber).getBoard().getPieces();

            model.addAttribute("pieces", pieces);
        }
        else {
            return "authentication/access-denied";
        }

        model.addAttribute("isWhiteTour", room.isWhiteTour());

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

    @PutMapping("/processLeavingRoom")
    public ResponseEntity<String> processLeavingRoom(
            @RequestBody RoomInfo roomInfo
            ) {

        Room room = roomManager.getRoom(roomInfo.getRoomNumber());

        int roomNumber = roomInfo.getRoomNumber();
        String username = roomInfo.getUsername();

        User owner = room.getOwner();
        User guest = room.getGuest();

        String guestName = null;
        String ownerName = owner.getUserName();

        if(guest != null)
            guestName = room.getGuest().getUserName();

        if(username.equals(ownerName))
            roomManager.removeRoom(room);

        if(username.equals(guestName))
            roomManager.removeGuest(room);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
