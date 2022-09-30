package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtAgeRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;

public interface ArtAgeService {
    ResponseEntity<Map<String, Object>> getAllArtAge(int page, int size);
    GetArtAgeResponse getArtAgeById(UUID id);
    UUID createArtAge(CreateArtAgeRequest createArtAgeRequest);
    UUID removeArtAgeById(UUID id);
    UUID updateArtAgeById(UUID id, CreateArtAgeRequest createArtAgeRequest);
}
