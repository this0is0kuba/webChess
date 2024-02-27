package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.edu.agh.webChess.communication.GameInfo;
import pl.edu.agh.webChess.communication.Message;
import pl.edu.agh.webChess.communication.Move;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Moves;
import pl.edu.agh.webChess.game.room.RoomManager;
import pl.edu.agh.webChess.game.room.Status;

import java.util.List;

@Controller
public class WebSocketController {

    private RoomManager roomManager;

    @Autowired
    WebSocketController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @MessageMapping("game/room/{roomNumber}/chat")
    @SendTo("topic/room/{roomNumber}/chat")
    public Message handleMessage(@DestinationVariable String roomNumber, Message message) {

        roomManager.addMessageToRoom(message.getFrom() + ": " + message.getContent(), Integer.parseInt(roomNumber));

        return message;
    }

    @MessageMapping("game/room/{roomNumber}/game")
    @SendTo("topic/room/{roomNumber}/game")
    public GameInfo handleGameInfo(@DestinationVariable String roomNumber, GameInfo gameInfo) {

        int intRoomNumber = Integer.parseInt(roomNumber);

        if(gameInfo.getInfo().equals("userJoined")) {
            roomManager.setConnection(Integer.parseInt(roomNumber));
            return gameInfo;
        }

        if(gameInfo.getInfo().equals("ready"))
            roomManager.setUserReady(gameInfo.getUsername(), intRoomNumber);

        if(roomManager.getRoomStatus(intRoomNumber).equals(Status.PLAYING))
            return new GameInfo("start", gameInfo.getUsername());

        return new GameInfo("ready", gameInfo.getUsername());
    }

    @MessageMapping("game/room/{roomNumber}/gameMoves")
    @SendTo("topic/room/{roomNumber}/gameMoves")
    public List<Moves> handleMoveInfo(@DestinationVariable String roomNumber, Move move) {

        int intRoomNumber = Integer.parseInt(roomNumber);

        Board board = roomManager.getRoom(intRoomNumber).getBoard();
        boolean colour = board.getPiece(move.getFrom().getRow(), move.getFrom().getCol()).getColour();

        board.movePiece(move.getFrom(), move.getTo());

        return board.getAllPossibleMoves(colour);
    }

    @MessageMapping("game/room/{roomNumber}/firstMoves")
    @SendTo("topic/room/{roomNumber}/firstMoves")
    public List<Moves> returnFirstMoves(@DestinationVariable String roomNumber, GameInfo gameInfo) {

        int intRoomNumber = Integer.parseInt(roomNumber);

        Board board = roomManager.getRoom(intRoomNumber).getBoard();
        boolean colour = gameInfo.getInfo().equals("true");

        return board.getAllPossibleMoves(colour);
    }
}
