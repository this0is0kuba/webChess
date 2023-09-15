package pl.edu.agh.webChess.dao;

import pl.edu.agh.webChess.entity.UserStatistics;

public interface UserStatisticsDAO {

    UserStatistics findUserStatisticsByUserName(String name);
}
