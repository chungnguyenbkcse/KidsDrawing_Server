package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtTypeRequest;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import java.util.UUID;

public interface ArtTypeService {
    ResponseEntity<Map<String, Object>> getAllArtType(int page, int size);
    GetArtTypeResponse getArtTypeById(UUID id);
    UUID createArtType(CreateArtTypeRequest createArtTypeRequest);
    UUID removeArtTypeById(UUID id);
    UUID updateArtTypeById(UUID id, CreateArtTypeRequest createArtTypeRequest);
}
