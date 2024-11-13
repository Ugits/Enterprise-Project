package org.jonas.enterpriseproject.user.service;

import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.jonas.enterpriseproject.user.authorities.UserRole.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<CustomUserDTO> createUser(CustomUserDTO customUserDTO) {

        CustomUser customUser = new CustomUser(
                customUserDTO.username(),
                passwordEncoder.encode(customUserDTO.password()),
                USER,
                true,
                true,
                true,
                true
        );

        if (userRepository.findByUsername(customUser.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userRepository.save(customUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(customUserDTO);

    }
}
