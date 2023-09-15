package pl.edu.agh.webChess.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "games_won")
    private int gamesWon;

    @Column(name = "games_lost")
    private int gamesLost;

    @Column(name = "games_drawn")
    private int gamesDrown;

    @Column(name = "points")
    private int points;

    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    public UserStatistics() {}

    public UserStatistics(int gamesWon, int gamesLost, int gamesDrown, int points) {
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesDrown = gamesDrown;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesDrown() {
        return gamesDrown;
    }

    public void setGamesDrown(int gamesDrown) {
        this.gamesDrown = gamesDrown;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "userStatistics{" +
                "id=" + id +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", gamesDrown=" + gamesDrown +
                ", points=" + points +
                '}';
    }
}
