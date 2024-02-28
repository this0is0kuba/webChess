package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.edu.agh.webChess.communication.AllMoves;
import pl.edu.agh.webChess.communication.GameInfo;
import pl.edu.agh.webChess.communication.Message;
import pl.edu.agh.webChess.communication.Move;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.game.chess.auxiliary.Moves;
import pl.edu.agh.webChess.game.room.Room;
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
    public AllMoves handleMoveInfo(@DestinationVariable String roomNumber, Move move) {

        int intRoomNumber = Integer.parseInt(roomNumber);
        Room room = roomManager.getRoom(intRoomNumber);

        Board board = room.getBoard();
        boolean colour = move.isColour();

        if(move.getFrom() == null) {
            if(colour)
                return new AllMoves(board.getAllPossibleMoves(true), move, true);
            else
                return new AllMoves(board.getAllPossibleMovesForReversedBorder(false), move, false);
        }

        int endGameInfo = 2;

        room.setWhiteTour(!room.isWhiteTour());

        if(colour) {

            board.movePiece(move.getFrom(), move.getTo());
            move.reverse();

            if(board.checkEndGame(false) == 0) {
                System.out.println("Win");
                endGameInfo = 0;
            }
            if(board.checkEndGame(false) == 1) {
                System.out.println("Lose");
                endGameInfo = 1;
            }

            return new AllMoves(board.getAllPossibleMovesForReversedBorder(false), move, true, endGameInfo);
        }

        else {

            move.reverse();
            board.movePiece(move.getFrom(), move.getTo());

            if(board.checkEndGame(false) == 0) {
                System.out.println("Win");
                endGameInfo = 0;
            }
            if(board.checkEndGame(false) == 1) {
                System.out.println("Lose");
                endGameInfo = 1;
            }

            return new AllMoves(board.getAllPossibleMoves(true), move, false, endGameInfo);
        }

    }

}
