package pl.edu.agh.webChess.model;

import pl.edu.agh.webChess.entity.User;

public class UserRanking {

    private User user;
    private long ranking;

    public UserRanking(User user, long ranking) {
        this.user = user;
        this.ranking = ranking;
    }

    public User getUser() {
        return user;
    }

    public long getRanking() {
        return ranking;
    }
}
