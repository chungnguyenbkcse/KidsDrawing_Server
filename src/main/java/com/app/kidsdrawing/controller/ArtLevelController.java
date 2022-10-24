package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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

import com.app.kidsdrawing.dto.CreateArtLevelRequest;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import com.app.kidsdrawing.service.ArtLevelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/art-level")
public class ArtLevelController {
    private final ArtLevelService artLevelService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createArtLevel(@RequestBody CreateArtLevelRequest createArtLevelRequest) {
        UUID artLevelId = artLevelService.createArtLevel(createArtLevelRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{artLevelId}")
                .buildAndExpand(artLevelId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateArtLevel(@PathVariable UUID id, @RequestBody CreateArtLevelRequest createArtLevelRequest) {
        UUID artLevelId = artLevelService.updateArtLevelById(id,createArtLevelRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artLevelId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllArtLevels(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(artLevelService.getAllArtLevel(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetArtLevelResponse> getArtLevelById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(artLevelService.getArtLevelById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteArtLevelById(@PathVariable UUID id) {
        UUID artLevelId = artLevelService.removeArtLevelById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artLevelId).toUri();
        return ResponseEntity.created(location).build();
    }
}
