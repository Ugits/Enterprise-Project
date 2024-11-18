package org.jonas.enterpriseproject.user.repository.dao;

import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.CrudMethods;

import java.util.Optional;

public interface UserDAO {
    Optional<CustomUser> findByUsername(String username);
}

// QUALIFYER