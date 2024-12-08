package org.jonas.enterpriseproject.api.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.api.service.ApiService;
import org.jonas.enterpriseproject.spell.dto.SpellDTO;
import org.jonas.enterpriseproject.spell.dto.SpellNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/spell")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/{name}")
    public Mono<ResponseEntity<SpellDTO>> getSpellByName(@Valid @ModelAttribute SpellNameDTO name, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return apiService.fetchSpellByName(name)
                    .map(spell -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spell));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @GetMapping("/all-names")
    public Mono<ResponseEntity<List<String>>> getSpellNames(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return apiService.fetchAllSpellNames();
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }


    @GetMapping("/all")
    public Mono<ResponseEntity<List<SpellDTO>>> getAllSpells(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return apiService.fetchAllSpells()
                    .map(spellList -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spellList));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

    }

    @GetMapping("/samples")
    public Mono<ResponseEntity<List<SpellDTO>>> sample(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return apiService.fetchUnAuthSample()
                    .map(spellList -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spellList));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

}
