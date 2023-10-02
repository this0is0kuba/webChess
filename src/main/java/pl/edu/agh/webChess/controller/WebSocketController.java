package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.edu.agh.webChess.communication.GameInfo;
import pl.edu.agh.webChess.communication.Message;
import pl.edu.agh.webChess.game.RoomManager;

@Controller
public class WebSocketController {

    private RoomManager roomManager;

    @Autowired
    WebSocketController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @MessageMapping("game/room/{roomNumber}/chat")
    @SendTo("/topic/room/{roomNumber}/chat")
    public Message handleMessage(@DestinationVariable String roomNumber, Message message) {

        roomManager.addMessageToRoom(message.getFrom() + ": " + message.getContent(), Integer.parseInt(roomNumber));

        return message;
    }

    @MessageMapping("game/room/{roomNumber}/game")
    @SendTo("/topic/room/{roomNumber}/game")
    public GameInfo handleGameInfo(GameInfo gameInfo) {

        return gameInfo;
    }
}
