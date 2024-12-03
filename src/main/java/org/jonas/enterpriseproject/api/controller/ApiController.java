package org.jonas.enterpriseproject.api.controller;

import org.jonas.enterpriseproject.api.service.ApiService;
import org.jonas.enterpriseproject.spell.model.SpellDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/spell/all")
    public Mono<ResponseEntity<List<SpellDTO>>> spellAll() {
        System.out.println("Enter all in controller");
        return apiService.fetchAllSpells()
                .map(spellList -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(spellList));
    }

}
