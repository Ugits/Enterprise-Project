package org.jonas.enterpriseproject.auth.service;

import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.auth.dto.AuthenticationResponse;
import org.jonas.enterpriseproject.auth.jwt.JWTService;
import org.jonas.enterpriseproject.exception.UserAlreadyExistsException;
import org.jonas.enterpriseproject.user.authorities.UserRole;
import org.jonas.enterpriseproject.user.dao.UserDAO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDAO userDAO;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JWTService jwtService,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDAO = userDAO;
    }

    public AuthenticationResponse verify(AuthenticationRequest authenticationRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.username(),
                                authenticationRequest.password()
                        ));

        String generatedToken = jwtService.generateToken(authenticationRequest.username());
        System.out.println("Generated token: " + generatedToken);

        return
                new AuthenticationResponse(
                        generatedToken,
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .filter(authority -> authority.startsWith("ROLE_"))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("User has no role"))
                                .substring(5));

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

}
