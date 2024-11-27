package org.jonas.enterpriseproject.user.controller;

import org.jonas.enterpriseproject.user.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<Boolean> deleteUser(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(adminService.deleteUser(username));
    }

}
