package pl.edu.agh.webChess.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.agh.webChess.entity.Role;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.entity.UserStatistics;


public interface UserService extends UserDetailsService {

    User findUserByName(String name);

    Role findRoleByName(String name);

    void save(User user);

    UserStatistics findUserStatisticsByUserName(String name);
}
