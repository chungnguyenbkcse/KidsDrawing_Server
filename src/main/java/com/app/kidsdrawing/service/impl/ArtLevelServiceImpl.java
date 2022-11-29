package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateArtLevelRequest;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import com.app.kidsdrawing.entity.ArtLevel;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.ArtLevelAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtLevelRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.service.ArtLevelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArtLevelServiceImpl implements ArtLevelService {

    private final ArtLevelRepository artLevelRepository;
    private final ClassesRepository classRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllArtLevel(int page, int size) {
        List<GetArtLevelResponse> allArtLevelResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<ArtLevel> pageArtLevel = artLevelRepository.findAll(paging);
        pageArtLevel.getContent().forEach(art_level -> {
            GetArtLevelResponse artLevelResponse = GetArtLevelResponse.builder()
                    .id(art_level.getId())
                    .name(art_level.getName())
                    .description(art_level.getDescription())
                    .build();
            allArtLevelResponses.add(artLevelResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_level", allArtLevelResponses);
        response.put("currentPage", pageArtLevel.getNumber());
        response.put("totalItems", pageArtLevel.getTotalElements());
        response.put("totalPages", pageArtLevel.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetArtLevelResponse getArtLevelById(Long id){
        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(id);
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });

        return GetArtLevelResponse.builder()
                .id(artLevel.getId())
                .name(artLevel.getName())
                .description(artLevel.getDescription())
                .build();
    }

    @Override
    public Long createArtLevel(CreateArtLevelRequest createArtLevelRequest) {
        if (artLevelRepository.existsByName(createArtLevelRequest.getName())) {
            throw new ArtLevelAlreadyCreateException("exception.art_level.art_level_taken");
        }

        ArtLevel savedArtLevel = ArtLevel.builder()
                .name(createArtLevelRequest.getName())
                .description(createArtLevelRequest.getDescription())
                .build();
        artLevelRepository.save(savedArtLevel);

        return savedArtLevel.getId();
    }

    @Override
    public Long removeArtLevelById(Long id) {
        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(id);
        artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });

        List<Classes> listClass = classRepository.findAllByArtLevel(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getUserRegisterTeachSemester().getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.ArtLevel_Classes.not_delete");
            }
        }

        artLevelRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateArtLevelById(Long id, CreateArtLevelRequest createArtLevelRequest) {
        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(id);
        ArtLevel updatedArtLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });
        updatedArtLevel.setName(createArtLevelRequest.getName());
        updatedArtLevel.setDescription(createArtLevelRequest.getDescription());
        artLevelRepository.save(updatedArtLevel);

        return updatedArtLevel.getId();
    }
}
