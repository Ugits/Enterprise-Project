package org.jonas.enterpriseproject.user.dao;

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
        String query = "SELECT u FROM CustomUser u WHERE LOWER(u.username) = LOWER(:username) ";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }



    //
//    @Override
//    public CustomUser save(CustomUser user) {
//        entityManager.persist(user);
//        return user;
//    }
//
//    @Override
//    public void update(CustomUser user) {
//        entityManager.merge(user);
//    }
//
//    @Override
//    public void delete(CustomUser user) {
//        entityManager.remove(user);
//    }
//
//    @Override
//    public Optional<CustomUser> findById(Long id) {
//        CustomUser user = entityManager.find(CustomUser.class, id);
//        return Optional.ofNullable(user);
//    }

//    @Override
//    public Optional<CustomUser> findByUsername(String username) {
//        String jpql = "SELECT u FROM CustomUser u WHERE u.username = :username";
//        TypedQuery<CustomUser> query = entityManager.createQuery(jpql, CustomUser.class);
//        query.setParameter("username", username);
//        return query.getResultStream().findFirst();
//    }
//}
//    @Override
//    public boolean existsByUsername(String username) {
//        String jpql = "SELECT COUNT(u) FROM CustomUser u WHERE u.username = :username";
//        Long count = entityManager.createQuery(jpql, Long.class)
//                .setParameter("username", username)
//                .getSingleResult();
//        return count > 0;
//    }
}
