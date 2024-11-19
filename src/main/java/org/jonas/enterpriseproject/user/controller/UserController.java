package org.jonas.enterpriseproject.user.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<ResponseEntity<UserDetails>> test(@AuthenticationPrincipal UserDetails userDetails) {

        System.out.println(userDetails.getUsername() + " " + userDetails.getPassword() + " " + userDetails.getAuthorities());

        ResponseEntity<UserDetails> respons = ResponseEntity.ok(userDetails);
        System.out.println(respons);

        return ResponseEntity.ok(ResponseEntity.ok(userDetails));
    }

    @DeleteMapping("/delete-me")
    public ResponseEntity<CustomUserDTO> deleteLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.deleteAuthenticatedUser(userDetails);
    }

}
