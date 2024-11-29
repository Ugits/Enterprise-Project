package org.jonas.enterpriseproject.user.dao;

import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDAO {
    Optional<CustomUser> findByUsername(String username);
    Optional<CustomUser> findByUsernameIgnoreCase(String username);
}

// QUALIFYER

