package pl.edu.agh.webChess.game.room;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.game.chess.Board;
import pl.edu.agh.webChess.service.UserService;
import pl.edu.agh.webChess.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Room {

    private int code;
    private User owner;
    private User guest;

    private RoomTime time;
    private long whiteTime;
    private long blackTime;

    private boolean pause = true;
    private Timer timer = new Timer();
    TimerTask intervalTask = new TimerTask() {
        @Override
        public void run() {

            if(pause)
                return;

            if(isWhiteTour)
                whiteTime -= 20;
            else
                blackTime -= 20;

            if(whiteTime <= 0 || blackTime <= 0) {
                pause = true;
                emitTimeExpired();
            }
        }
    };

    private Status status;
    private String password;
    private boolean isWhite;
    private List<String> chat = new ArrayList<>();
    private boolean ownerReady;
    private boolean guestReady;
    private boolean isConnectionEstablished;
    private boolean isWhiteTour = true;
    private Board board = new Board(false);

    private User winner;
    private User loser;

    @Autowired
    private RoomManager roomManager;

    public Room() {
        timer.scheduleAtFixedRate(intervalTask, 0, 20);
    }

    public boolean isOwnerReady() {
        return ownerReady;
    }

    public void setOwnerReady(boolean ownerReady) {
        this.ownerReady = ownerReady;
        if(guestReady) {
            whiteTime = time.convertToMilliseconds();
            blackTime = time.convertToMilliseconds();
            pause = false;
        }
    }

    public boolean isGuestReady() {
        return guestReady;
    }

    public void setGuestReady(boolean guestReady) {
        this.guestReady = guestReady;
        if(ownerReady) {
            whiteTime = time.convertToMilliseconds();
            blackTime = time.convertToMilliseconds();
            pause = false;
        }
    }

    public boolean isConnectionEstablished() {
        return isConnectionEstablished;
    }

    public void setConnectionEstablished(boolean connectionEstablished) {
        isConnectionEstablished = connectionEstablished;
    }

    public int getCode() {
        return code;
    }

    public User getOwner() {
        return owner;
    }

    public User getGuest() {
        return guest;
    }

    public RoomTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public void setTime(RoomTime time) {
        this.time = time;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public List<String> getChat() {
        return chat;
    }

    public void addMessage(String message) {
        chat.add(message);
    }

    public boolean isWhiteTour() {
        return isWhiteTour;
    }

    public void setWhiteTour(boolean whiteTour) {
        isWhiteTour = whiteTour;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void resetAfterEndOfTheGame() {

        pause = true;

        status = Status.WAITING;
        ownerReady = false;
        guestReady = false;
        isWhiteTour = true;

        whiteTime = time.convertToMilliseconds();
        blackTime = time.convertToMilliseconds();

        board = new Board(false);

        winner = null;
        loser = null;
    }

    public void resetWhenUserLeave() {

        pause = true;

        status = Status.SEARCHING;
        ownerReady = false;
        guestReady = false;
        isWhiteTour = true;
        isConnectionEstablished = false;

        whiteTime = time.convertToMilliseconds();
        blackTime = time.convertToMilliseconds();

        board = new Board(false);

        winner = null;
        loser = null;
    }

    public long getWhiteTime() {
        return whiteTime;
    }

    public void setWhiteTime(long whiteTime) {
        this.whiteTime = whiteTime;
    }

    public long getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(long blackTime) {
        this.blackTime = blackTime;
    }

    private void emitTimeExpired() {

        pause = true;

        User winner;
        User loser;

        if (whiteTime <= 0 && isWhite) {
            loser = owner;
            winner = guest;
        }

        else if (blackTime <= 0 && isWhite) {
            loser = guest;
            winner = owner;
        }

        else if (blackTime <= 0) {
            loser = owner;
            winner = guest;
        }

        else {
            loser = guest;
            winner = owner;
        }

        this.winner = winner;
        this.loser = loser;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    @Override
    public String toString() {

        String guestString = (guest != null) ? guest.getUserName() : "null";

        return "Room{" +
                "code=" + code +
                ", owner=" + owner.getUserName() +
                ", guest=" + guestString +
                ", time=" + time +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", isWhite=" + isWhite +
                '}';
    }
}
