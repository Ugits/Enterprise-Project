package org.jonas.enterpriseproject.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.dao.UserDAO;

import java.util.Optional;

public class UserRepositoryCustomImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CustomUser> findByUsername(String username) {
        System.out.println("Running UserDAO Custom Query");
        String query = "SELECT u FROM CustomUser u WHERE u.username = :username";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<CustomUser> findByUsernameIgnoreCase(String username) {
        System.out.println("Running UserDAO Custom Query");
        String query = "SELECT u FROM CustomUser u WHERE LOWER(u.username) = LOWER(:username) ";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }
}
