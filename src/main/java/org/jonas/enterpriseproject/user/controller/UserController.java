package org.jonas.enterpriseproject.user.controller;

import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;

import org.jonas.enterpriseproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<CustomUserDTO> register(@RequestBody @Validated CustomUserDTO customUserDTO) {
        System.out.println("ENTER POSTMAPPING /REGISTER");
        return userService.createUser(customUserDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "works";
    }

}
