package pl.edu.agh.webChess.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.webChess.entity.UserStatistics;

@Repository
public class UserStatisticsDAOImpl implements UserStatisticsDAO {

    EntityManager entityManager;

    @Autowired
    UserStatisticsDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserStatistics findUserStatisticsByUserName(String name) {

        String jpql =   "SELECT us " +
                        "FROM User u " +
                        "JOIN u.userStatistics us " +
                        "WHERE u.userName = :userName";

        TypedQuery<UserStatistics> query = entityManager.createQuery(jpql, UserStatistics.class);
        query.setParameter("userName", name);

        return query.getSingleResult();
    }
}
