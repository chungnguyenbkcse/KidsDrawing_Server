package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtLevelRequest;
import com.app.kidsdrawing.dto.GetArtLevelResponse;


public interface ArtLevelService {
    ResponseEntity<Map<String, Object>> getAllArtLevel(int page, int size);
    GetArtLevelResponse getArtLevelById(Long id);
    Long createArtLevel(CreateArtLevelRequest createArtLevelRequest);
    Long removeArtLevelById(Long id);
    Long updateArtLevelById(Long id, CreateArtLevelRequest createArtLevelRequest);
}
