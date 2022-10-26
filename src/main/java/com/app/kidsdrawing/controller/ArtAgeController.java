package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateArtAgeRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;
import com.app.kidsdrawing.service.ArtAgeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/art-age")
public class ArtAgeController {
    private final ArtAgeService artAgeService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createArtAge(@RequestBody CreateArtAgeRequest createArtAgeRequest) {
        Long artAgeId = artAgeService.createArtAge(createArtAgeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{artAgeId}")
                .buildAndExpand(artAgeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateArtAge(@PathVariable Long id, @RequestBody CreateArtAgeRequest createArtAgeRequest) {
        Long artAgeId = artAgeService.updateArtAgeById(id,createArtAgeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artAgeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllArtAges(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(artAgeService.getAllArtAge(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetArtAgeResponse> getArtAgeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(artAgeService.getArtAgeById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteArtAgeById(@PathVariable Long id) {
        Long artAgeId = artAgeService.removeArtAgeById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artAgeId).toUri();
        return ResponseEntity.created(location).build();
    }
}
