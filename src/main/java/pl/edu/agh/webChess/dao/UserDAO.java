package pl.edu.agh.webChess.dao;

import pl.edu.agh.webChess.entity.User;

public interface UserDAO {

    User findByName(String name);

    void save(User user);
}
