package org.jonas.enterpriseproject.user.controller;
import jakarta.validation.Valid;
import org.jonas.enterpriseproject.user.model.dto.DeleteUserDTO;
import org.jonas.enterpriseproject.user.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@Valid @ModelAttribute DeleteUserDTO deleteUserDTO) {
        adminService.deleteUser(deleteUserDTO.username());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
