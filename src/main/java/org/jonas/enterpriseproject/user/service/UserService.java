package org.jonas.enterpriseproject.user.service;

import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<CustomUserDTO> deleteAuthenticatedUser(UserDetails userDetails) {

        if (Objects.isNull(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUser customUser = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername() + " not found"));

        userRepository.delete(customUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CustomUserDTO(customUser.getUsername()));

    }


    public UserCredentialsDTO extractCredentials(UserDetails userDetails) {

        if (userDetails == null) throw new IllegalStateException("UserDetails is null");

        return new UserCredentialsDTO(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(authority -> authority.startsWith("ROLE_"))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("User has no role"))
                        .substring(5)
        );
    }



//    @Transactional
//    public ResponseEntity<CustomUserDTO> createUserDEV(CustomUserDTODEV customUserDTODEV) {
//
//        CustomUser customUser = new CustomUser(
//                customUserDTODEV.username(),
//                passwordEncoder.encode(customUserDTODEV.password()),
//                customUserDTODEV.role(),
//                true,
//                true,
//                true,
//                true
//        );
//
//        if (userDAO.findByUsernameIgnoreCase(customUserDTODEV.username()).isPresent()) {
//            throw new UserAlreadyExistsException("Username " + customUserDTODEV.username() + " is already taken");
//        }
//
//        userRepository.save(customUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomUserDTO(customUser.getUsername(), customUser.getPassword()));
//
//    }

}
