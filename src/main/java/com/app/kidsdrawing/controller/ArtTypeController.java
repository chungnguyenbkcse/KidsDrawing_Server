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

import com.app.kidsdrawing.dto.CreateArtTypeRequest;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import com.app.kidsdrawing.service.ArtTypeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/art-type")
public class ArtTypeController {
    private final ArtTypeService artTypeService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createArtType(@RequestBody CreateArtTypeRequest createArtTypeRequest) {
        Long artTypeId = artTypeService.createArtType(createArtTypeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{artTypeId}")
                .buildAndExpand(artTypeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateArtType(@PathVariable Long id, @RequestBody CreateArtTypeRequest createArtTypeRequest) {
        Long artTypeId = artTypeService.updateArtTypeById(id,createArtTypeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artTypeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllArtTypes(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(artTypeService.getAllArtType(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetArtTypeResponse> getArtTypeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(artTypeService.getArtTypeById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteArtTypeById(@PathVariable Long id) {
        Long artTypeId = artTypeService.removeArtTypeById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artTypeId).toUri();
        return ResponseEntity.created(location).build();
    }
}
