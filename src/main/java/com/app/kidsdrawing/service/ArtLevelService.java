package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtLevelRequest;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import java.util.UUID;

public interface ArtLevelService {
    ResponseEntity<Map<String, Object>> getAllArtLevel(int page, int size);
    GetArtLevelResponse getArtLevelById(UUID id);
    UUID createArtLevel(CreateArtLevelRequest createArtLevelRequest);
    UUID removeArtLevelById(UUID id);
    UUID updateArtLevelById(UUID id, CreateArtLevelRequest createArtLevelRequest);
}
