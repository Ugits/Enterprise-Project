package org.jonas.enterpriseproject.user.service;

import org.jonas.enterpriseproject.user.dao.UserDAO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminService {

    private final UserDAO userDAO;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserDAO userDAO, UserRepository userRepository) {
        this.userDAO = userDAO;
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteUser(String username) {

        CustomUser customUser = userDAO
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        userRepository.delete(customUser);
    }
}
