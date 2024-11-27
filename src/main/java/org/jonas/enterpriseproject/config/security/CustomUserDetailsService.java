package org.jonas.enterpriseproject.config.security;

import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryCustom userRepositoryCustom;

    @Autowired
    public CustomUserDetailsService(UserRepositoryCustom userRepositoryCustom) {
        this.userRepositoryCustom = userRepositoryCustom;

    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser customUser = userRepositoryCustom
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        System.out.println("LOADING USER TO SPRING... " + customUser);
        return new CustomUserDetails(customUser);
    }
}
