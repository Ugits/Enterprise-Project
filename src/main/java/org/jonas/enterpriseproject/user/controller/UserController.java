package org.jonas.enterpriseproject.user.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/test")
    public ResponseEntity<CustomUserDTO> test(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            // Handle the case where userDetails is null (unauthenticated user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        // Map UserDetails to UserDTO
        CustomUserDTO customUserDTO = new CustomUserDTO(
                userDetails.getUsername(),
                userDetails.getPassword()
        );

        // Optional: Log non-sensitive information
        System.out.println("Authenticated user: " + customUserDTO.username());
        return ResponseEntity.ok(customUserDTO);
    }


    @DeleteMapping("/delete-me")
    public ResponseEntity<CustomUserDTO> deleteLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.deleteAuthenticatedUser(userDetails);
    }

}
