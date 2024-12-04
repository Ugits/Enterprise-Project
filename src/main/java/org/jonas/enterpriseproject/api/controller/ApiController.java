package org.jonas.enterpriseproject.api.controller;

import org.jonas.enterpriseproject.api.service.ApiService;
import org.jonas.enterpriseproject.spell.model.SpellDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }


    // TODO CHANGE NAME.. could it collide
    // TODO will it make an anonymous 2nd request if /api has .permitAll ??

    @GetMapping("/all-spells")
    public Mono<ResponseEntity<List<SpellDTO>>> getAllSpells(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Auth " + authentication.getName() + "fetch");
            return apiService.fetchAllSpells()
                    .map(spellList -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spellList));

        }

        return apiService.fetchUnAuthSample()
                .map(spellList -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(spellList));

    }

    @GetMapping("/hi")
    public ResponseEntity<String> hi() {
        System.out.println("Enter hi in controller");
        return ResponseEntity.ok().body("hi there");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("HI AUTH USER");
        }

        return ResponseEntity.ok().body("HI ANONYMOUS USER (UNAUTHORIZED)");
    }

}
