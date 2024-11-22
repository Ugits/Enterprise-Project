package org.jonas.enterpriseproject.user.repository.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jonas.enterpriseproject.user.repository.dao.UserDAO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<CustomUser> findByUsername(String username) {
        String query = "SELECT u FROM CustomUser u WHERE u.username = :username";
        return entityManager.createQuery(query, CustomUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }


    //
//    @Override
//    public void save(CustomUser user) {
//        entityManager.persist(user);
//    }
//
//    @Query
//    public void test(CustomUser user) {
//        entityManager.persist(user);
//    }
//
//    @Override
//    public void update(CustomUser user) {
//        entityManager.merge(user);
//    }
//
//    @Override
//    public void delete(CustomUser user) {
//            entityManager.remove(user);
//    }
//
//    @Override
//    public Optional<CustomUser> findById(Long id) {
//        CustomUser user = entityManager.find(CustomUser.class, id);
//        return Optional.ofNullable(user);
//    }
//
//    @Override
//    public Optional<CustomUser> findByUsername(String username) {
//        String jpql = "SELECT u FROM CustomUser u WHERE u.username = :username";
//        TypedQuery<CustomUser> query = entityManager.createQuery(jpql, CustomUser.class);
//        query.setParameter("username", username);
//        return query.getResultStream().findFirst();
//    }

//    @Override
//    public boolean existsByUsername(String username) {
//        String jpql = "SELECT COUNT(u) FROM CustomUser u WHERE u.username = :username";
//        Long count = entityManager.createQuery(jpql, Long.class)
//                .setParameter("username", username)
//                .getSingleResult();
//        return count > 0;
//    }
}
