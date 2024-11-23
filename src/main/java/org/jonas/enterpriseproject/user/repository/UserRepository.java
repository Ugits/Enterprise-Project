package org.jonas.enterpriseproject.user.repository;

import org.jonas.enterpriseproject.user.repository.dao.UserDAO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long>{
    // Add custom query´s that all DAOs use, in here (IF GENERIC DAO)
    Optional<CustomUser> findByUsername(String username);

}
