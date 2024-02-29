package pl.edu.agh.webChess.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.agh.webChess.entity.Role;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.entity.UserStatistics;

import java.util.Collection;


public interface UserService extends UserDetailsService {

    User findUserByName(String name);

    Role findRoleByName(String name);

    void save(User user);

    UserStatistics findUserStatisticsByUserName(String name);

    long findUserRankingByUserName(String name);

    Collection<User> findTop10UsersByPoints();
    void updateUsersStatisticsAfterGame(User winner, User loser, boolean draw);
}
