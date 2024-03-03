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
import pl.edu.agh.webChess.service.UserService;

import java.util.List;

@Controller
public class WebSocketController {

    private UserService userService;
    private RoomManager roomManager;

    @Autowired
    WebSocketController(RoomManager roomManager, UserService userService) {
        this.roomManager = roomManager;
        this.userService = userService;
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

        if(gameInfo.getInfo().equals("userLeaved")) {

            Room room = roomManager.getRoom(intRoomNumber);

            if(gameInfo.getUsername().equals(room.getOwner().getUserName())) {

                if(room.isOwnerReady() && room.isGuestReady())
                    updateUserStatisticsWhenIsWinner(!room.getIsWhite(), room);

                return new GameInfo("ownerLeaved", gameInfo.getUsername());
            }

            else {

                if(room.isOwnerReady() && room.isGuestReady())
                    updateUserStatisticsWhenIsWinner(room.getIsWhite(), room);

                roomManager.getRoom(intRoomNumber).resetWhenUserLeave();
                return new GameInfo("guestLeaved", gameInfo.getUsername());
            }

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

        int endGameInfo;

        room.setWhiteTour(!room.isWhiteTour());

        if(colour) {

            board.movePiece(move.getFrom(), move.getTo());
            move.reverse();

            endGameInfo = checkEndGame(false, room);

            return new AllMoves(board.getAllPossibleMovesForReversedBorder(false), move, true, endGameInfo, room.getWhiteTime());
        }

        else {

            move.reverse();
            board.movePiece(move.getFrom(), move.getTo());

            endGameInfo = checkEndGame(true, room);

            return new AllMoves(board.getAllPossibleMoves(true), move, false, endGameInfo, room.getBlackTime());
        }

    }

    private int checkEndGame(boolean colour, Room room) {

        Board board = room.getBoard();

        if(board.checkEndGame(colour) == 0) {

            room.resetAfterEndOfTheGame();

            updateUserStatisticsWhenIsWinner(!colour, room);

            return 0;
        }

        if(board.checkEndGame(colour) == 1) {

            room.resetAfterEndOfTheGame();

            updateUserStatisticsWhenIsDraw(room);

            return 1;
        }

        return 2;
    }

    private void updateUserStatisticsWhenIsWinner(boolean winnerColour, Room room) {

        if(winnerColour && room.getIsWhite())
            userService.updateUsersStatisticsAfterGame(room.getOwner(), room.getGuest(), false);

        if(!winnerColour && room.getIsWhite())
            userService.updateUsersStatisticsAfterGame(room.getGuest(), room.getOwner(), false);

        if(winnerColour && !room.getIsWhite())
            userService.updateUsersStatisticsAfterGame(room.getGuest(), room.getOwner(), false);

        if(!winnerColour && !room.getIsWhite())
            userService.updateUsersStatisticsAfterGame(room.getOwner(), room.getGuest(), false);
    }

    private void updateUserStatisticsWhenIsDraw(Room room) {
        userService.updateUsersStatisticsAfterGame(room.getOwner(), room.getGuest(), true);
    }

}
