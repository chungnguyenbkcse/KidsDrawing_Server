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

import com.app.kidsdrawing.dto.CreateArtAgeRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.exception.ArtAgeAlreadyCreateException;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.service.ArtAgeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArtAgeServiceImpl implements ArtAgeService {

    private final ArtAgeRepository artAgeRepository;
    private final ClassesRepository classRepository;
    private final ContestRepository contestRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllArtAge(int page, int size) {
        List<GetArtAgeResponse> allArtAgeResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<ArtAge> pageArtAge = artAgeRepository.findAll(paging);
        pageArtAge.getContent().forEach(art_age -> {
            GetArtAgeResponse artAgeResponse = GetArtAgeResponse.builder()
                    .id(art_age.getId())
                    .name(art_age.getName())
                    .description(art_age.getDescription())
                    .build();
            allArtAgeResponses.add(artAgeResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_age", allArtAgeResponses);
        response.put("currentPage", pageArtAge.getNumber());
        response.put("totalItems", pageArtAge.getTotalElements());
        response.put("totalPages", pageArtAge.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetArtAgeResponse getArtAgeById(Long id){
        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(id);
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        return GetArtAgeResponse.builder()
                .id(artAge.getId())
                .name(artAge.getName())
                .description(artAge.getDescription())
                .build();
    }

    @Override
    public Long createArtAge(CreateArtAgeRequest createArtAgeRequest) {
        if (artAgeRepository.existsByName(createArtAgeRequest.getName())) {
            throw new ArtAgeAlreadyCreateException("exception.art_age.art_age_taken");
        }

        ArtAge savedArtAge = ArtAge.builder()
                .name(createArtAgeRequest.getName())
                .description(createArtAgeRequest.getDescription())
                .build();
        artAgeRepository.save(savedArtAge);

        return savedArtAge.getId();
    }

    @Override
    public Long removeArtAgeById(Long id) {
        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(id);
        artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        List<Classes> listClass = classRepository.findAllByArtAge(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getUserRegisterTeachSemester().getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.ArtAge_Classes.not_delete");
            }
        }

        List<Contest> listContest = contestRepository.findByArtAgeId1(id);
        for (int index = 0; index < listContest.size(); index++) {
            if (time_now.isAfter(listContest.get(index).getStart_time()) && time_now.isBefore(listContest.get(index).getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.ArtAge_Contest.not_delete");
            }
        }

        artAgeRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateArtAgeById(Long id, CreateArtAgeRequest createArtAgeRequest) {
        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(id);
        ArtAge updatedArtAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });
        
        updatedArtAge.setName(createArtAgeRequest.getName());
        updatedArtAge.setDescription(createArtAgeRequest.getDescription());
        artAgeRepository.save(updatedArtAge);

        return updatedArtAge.getId();
    }
}
