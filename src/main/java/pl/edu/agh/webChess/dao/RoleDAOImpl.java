package pl.edu.agh.webChess.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.webChess.entity.Role;

@Repository
public class RoleDAOImpl implements RoleDAO{

    private EntityManager entityManager;

    @Autowired
    RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByName(String name) {

        TypedQuery<Role> query = entityManager.createQuery("from Role where roleName = :name", Role.class);
        query.setParameter("name", name);

        Role role;

        try {
            role = query.getSingleResult();
        } catch(Exception e) {
            role = null;
        }

        return role;
    }
}
