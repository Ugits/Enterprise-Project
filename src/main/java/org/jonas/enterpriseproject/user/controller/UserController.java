package org.jonas.enterpriseproject.user.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.jonas.enterpriseproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomUserDTO> register(@RequestBody @Valid CustomUserDTO customUserDTO) {
        System.out.println("ENTER POST MAPPING / REGISTER");
        return userService.createUser(customUserDTO);
    }

    @GetMapping("/credentials")
    public ResponseEntity<UserCredentialsDTO> getCredentials(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(
                new UserCredentialsDTO(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .filter(authority -> authority.startsWith("ROLE_"))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("User has no role"))
                                .substring(5)
                )
        );
    }


    @DeleteMapping("/delete-me")
    public ResponseEntity<CustomUserDTO> deleteLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.deleteAuthenticatedUser(userDetails);
    }

}
