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

import com.app.kidsdrawing.dto.CreateArtTypeRequest;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.ArtTypeAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.service.ArtTypeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArtTypeServiceImpl implements ArtTypeService {

    private final ArtTypeRepository artTypeRepository;
    private final ClassesRepository classRepository;
    private final ContestRepository contestRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllArtType(int page, int size) {
        List<GetArtTypeResponse> allArtTypeResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<ArtType> pageArtType = artTypeRepository.findAll(paging);
        pageArtType.getContent().forEach(art_type -> {
            GetArtTypeResponse artTypeResponse = GetArtTypeResponse.builder()
                    .id(art_type.getId())
                    .name(art_type.getName())
                    .description(art_type.getDescription())
                    .build();
            allArtTypeResponses.add(artTypeResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_type", allArtTypeResponses);
        response.put("currentPage", pageArtType.getNumber());
        response.put("totalItems", pageArtType.getTotalElements());
        response.put("totalPages", pageArtType.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetArtTypeResponse getArtTypeById(Long id){
        Optional<ArtType> artTypeOpt = artTypeRepository.findById(id);
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        return GetArtTypeResponse.builder()
                .id(artType.getId())
                .name(artType.getName())
                .description(artType.getDescription())
                .build();
    }

    @Override
    public Long createArtType(CreateArtTypeRequest createArtTypeRequest) {
        if (artTypeRepository.existsByName(createArtTypeRequest.getName())) {
            throw new ArtTypeAlreadyCreateException("exception.art_type.art_type_taken");
        }

        ArtType savedArtType = ArtType.builder()
                .name(createArtTypeRequest.getName())
                .description(createArtTypeRequest.getDescription())
                .build();
        artTypeRepository.save(savedArtType);

        return savedArtType.getId();
    }

    @Override
    public Long removeArtTypeById(Long id) {
        Optional<ArtType> artTypeOpt = artTypeRepository.findById(id);
        artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        List<Classes> listClass = classRepository.findAllByArtType(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getUserRegisterTeachSemester().getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.ArtType_Classes.not_delete");
            }
        }

        List<Contest> listContest = contestRepository.findByArtTypeId1(id);
        for (int index = 0; index < listContest.size(); index++) {
            if (time_now.isAfter(listContest.get(index).getStart_time()) && time_now.isBefore(listContest.get(index).getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.ArtType_Contest.not_delete");
            }
        }


        artTypeRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateArtTypeById(Long id, CreateArtTypeRequest createArtTypeRequest) {
        Optional<ArtType> artTypeOpt = artTypeRepository.findById(id);
        ArtType updatedArtType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });
        updatedArtType.setName(createArtTypeRequest.getName());
        updatedArtType.setDescription(createArtTypeRequest.getDescription());
        artTypeRepository.save(updatedArtType);

        return updatedArtType.getId();
    }
}
