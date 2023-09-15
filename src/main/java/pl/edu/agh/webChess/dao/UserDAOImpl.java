package pl.edu.agh.webChess.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.webChess.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByName(String name) {

        TypedQuery<User> query = entityManager.createQuery("from User where userName = :name", User.class);
        query.setParameter("name", name);

        User user;

        try {
            user = query.getSingleResult();
        } catch(Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    public void save(User user) {
        entityManager.merge(user);
    }
}
