package org.jonas.enterpriseproject.user.service;

import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.auth.dto.AuthenticationResponse;
import org.jonas.enterpriseproject.auth.jwt.JWTService;
import org.jonas.enterpriseproject.exception.UserAlreadyExistsException;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepositoryCustom;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

import static org.jonas.enterpriseproject.user.authorities.UserRole.USER;

@Service
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepositoryCustom userRepositoryCustom;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository, UserRepositoryCustom userRepositoryCustom) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @Transactional
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



        if (userRepositoryCustom.findByUsername(customUserDTO.username()).isPresent()) {
            throw new UserAlreadyExistsException("A user with username " + customUserDTO.username() + " already exists");
        }

        userRepositoryCustom.save(customUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(customUserDTO);

    }

    @Transactional
    public ResponseEntity<CustomUserDTO> deleteAuthenticatedUser(UserDetails userDetails) {

        if (Objects.isNull(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUser customUser = userRepositoryCustom
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername() + " not found"));

        userRepositoryCustom.delete(customUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CustomUserDTO(customUser.getUsername()));

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
}
