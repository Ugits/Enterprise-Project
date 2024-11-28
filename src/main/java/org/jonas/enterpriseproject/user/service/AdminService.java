package org.jonas.enterpriseproject.user.service;

import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepositoryCustom;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminService {

    private final UserRepositoryCustom userRepositoryCustom;

    public AdminService(UserRepositoryCustom userRepositoryCustom) {
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @Transactional
    public void deleteUser(String username) {

        CustomUser customUser = userRepositoryCustom
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        userRepositoryCustom.delete(customUser);
    }
}
