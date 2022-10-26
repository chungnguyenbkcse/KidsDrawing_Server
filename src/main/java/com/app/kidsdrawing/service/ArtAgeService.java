package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateArtAgeRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;

public interface ArtAgeService {
    ResponseEntity<Map<String, Object>> getAllArtAge(int page, int size);
    GetArtAgeResponse getArtAgeById(Long id);
    Long createArtAge(CreateArtAgeRequest createArtAgeRequest);
    Long removeArtAgeById(Long id);
    Long updateArtAgeById(Long id, CreateArtAgeRequest createArtAgeRequest);
}
