package org.jonas.enterpriseproject.api.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.api.service.ApiService;
import org.jonas.enterpriseproject.spell.dto.SpellDTO;
import org.jonas.enterpriseproject.spell.dto.SpellNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<SpellDTO>> getSpellByName(@Valid @ModelAttribute SpellNameDTO name) {
            return apiService.fetchSpellByName(name)
                    .map(spell -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spell));
    }

    @GetMapping("/all-names")
    public Mono<ResponseEntity<List<String>>> getSpellNames() {
            return apiService.fetchAllSpellNames();
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<SpellDTO>>> getAllSpells() {
            return apiService.fetchAllSpells()
                    .map(spellList -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spellList));
    }

    @GetMapping("/samples")
    public Mono<ResponseEntity<List<SpellDTO>>> sample() {
            return apiService.fetchUnAuthSample()
                    .map(spellList -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(spellList));
    }

}
