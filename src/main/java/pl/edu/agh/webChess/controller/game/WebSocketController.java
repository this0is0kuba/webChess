package pl.edu.agh.webChess.controller.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.edu.agh.webChess.controller.communication.GameInfo;
import pl.edu.agh.webChess.model.Message;

@Controller
public class WebSocketController {

    @MessageMapping("game/room/{roomNumber}/chat")
    @SendTo("/topic/room/{roomNumber}/chat")
    public Message handleMessage(Message message) {

        return message;
    }

    @MessageMapping("game/room/{roomNumber}/game")
    @SendTo("/topic/room/{roomNumber}/game")
    public GameInfo handleGameInfo(GameInfo gameInfo) {

        return gameInfo;
    }
}
