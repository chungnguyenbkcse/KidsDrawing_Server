package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtTypeRequest;
import com.app.kidsdrawing.dto.GetArtTypeResponse;


public interface ArtTypeService {
    ResponseEntity<Map<String, Object>> getAllArtType(int page, int size);
    GetArtTypeResponse getArtTypeById(Long id);
    Long createArtType(CreateArtTypeRequest createArtTypeRequest);
    Long removeArtTypeById(Long id);
    Long updateArtTypeById(Long id, CreateArtTypeRequest createArtTypeRequest);
}
