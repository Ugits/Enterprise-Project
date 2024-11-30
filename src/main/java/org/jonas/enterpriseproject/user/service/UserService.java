package org.jonas.enterpriseproject.user.service;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.auth.dto.AuthenticationResponse;
import org.jonas.enterpriseproject.auth.jwt.JWTService;
import org.jonas.enterpriseproject.exception.UserAlreadyExistsException;
import org.jonas.enterpriseproject.user.authorities.UserRole;
import org.jonas.enterpriseproject.user.dao.UserDAO;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTODEV;
import org.jonas.enterpriseproject.user.model.dto.SignupRequestDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import static org.jonas.enterpriseproject.user.authorities.UserRole.USER;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final UserRepository userRepository;


    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDAO userDAO, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.userRepository = userRepository;
    }


    @Transactional
    public ResponseEntity<CustomUserDTO> deleteAuthenticatedUser(UserDetails userDetails) {

        if (Objects.isNull(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUser customUser = userDAO
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

    @Transactional
    public ResponseEntity<UserCredentialsDTO> createUser(SignupRequestDTO signupRequestDTO) {

        CustomUser customUser = new CustomUser(
                signupRequestDTO.username(),
                passwordEncoder.encode(signupRequestDTO.password()),
                UserRole.valueOf(signupRequestDTO.role()),
                true,
                true,
                true,
                true
        );

        if (userDAO.findByUsernameIgnoreCase(customUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username " + signupRequestDTO.username() + " is already taken");
        }

        userRepository.save(customUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserCredentialsDTO(customUser.getUsername(),customUser.getPassword(),customUser.getUserRole().name()));

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
