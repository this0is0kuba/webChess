package pl.edu.agh.webChess.dao;

import pl.edu.agh.webChess.entity.User;

import java.util.Collection;

public interface UserDAO {

    User findByName(String name);

    void save(User user);

    Collection<User> find10UsersSortByPointsDesc();
}
