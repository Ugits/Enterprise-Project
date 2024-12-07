package org.jonas.enterpriseproject.user.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<CustomUser> findByUsername(String username) {
        System.out.println("Running UserDAO Custom Query");
        String query = "SELECT u FROM CustomUser u WHERE u.username = :username ";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public Optional<CustomUser> findByUsernameIgnoreCase(String username) {
        System.out.println("Running UserDAO Custom Query");
        String query = "SELECT u FROM CustomUser u WHERE u.username ILIKE :username ";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }
}
