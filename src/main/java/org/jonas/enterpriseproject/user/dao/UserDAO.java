package org.jonas.enterpriseproject.user.dao;

import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import java.util.Optional;

public interface UserDAO {
    void save(CustomUser user);
    void update(CustomUser user);
    void delete(CustomUser user);
    Optional<CustomUser> findById(Long id);
    Optional<CustomUser> findByUsername(String username);
    //boolean existsByUsername(String username);
    void test(CustomUser user);
}

// QUALIFYER