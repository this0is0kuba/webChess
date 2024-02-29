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

    @Override
    public long findRowNumberByUserNameSortByPoints(String name) {
//        String clearSQL =   "SELECT * FROM ( " +
//                            "   SELECT ROW_NUMBER() OVER (ORDER BY us.points), u.user_name " +
//                            "   FROM user_statistics us " +
//                            "   INNER JOIN users u ON u.id = us.id) n " +
//                            "n.user_name = :data";

        String jpql =   "SELECT count(us) + 1 " +
                        "FROM UserStatistics us " +
                        "WHERE us.points > " +
                        "( " +
                        "   SELECT us.points " +
                        "   FROM User u " +
                        "   JOIN u.userStatistics us " +
                        "   WHERE u.userName = :userName " +
                        ") ";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("userName", name);

        return query.getSingleResult();
    }

    @Override
    public void save(UserStatistics userStatistics) {
        entityManager.merge(userStatistics);
    }
}
