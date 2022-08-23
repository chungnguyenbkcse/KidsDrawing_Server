package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateTutorialPageRequest;
import com.app.kidsdrawing.dto.GetTutorialPageResponse;
import com.app.kidsdrawing.entity.TutorialPage;
import com.app.kidsdrawing.entity.Tutorial;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TutorialPageRepository;
import com.app.kidsdrawing.repository.TutorialRepository;
import com.app.kidsdrawing.service.TutorialPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TutorialPageServiceImpl implements TutorialPageService{
    
    private final TutorialPageRepository tutorialPageRepository;
    private final TutorialRepository tutorialRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialPage() {
        List<GetTutorialPageResponse> allTutorialPageResponses = new ArrayList<>();
        List<TutorialPage> listTutorialPage = tutorialPageRepository.findAll();
        listTutorialPage.forEach(content -> {
            GetTutorialPageResponse tutorialPageResponse = GetTutorialPageResponse.builder()
                .id(content.getId())
                .tutorial_id(content.getTutorial().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .build();
            allTutorialPageResponses.add(tutorialPageResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialPage", allTutorialPageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialPageByTutorialId(Long id) {
        List<GetTutorialPageResponse> allTutorialPageResponses = new ArrayList<>();
        List<TutorialPage> listTutorialPage = tutorialPageRepository.findAll();
        listTutorialPage.forEach(content -> {
            if (content.getTutorial().getId() == id){
                GetTutorialPageResponse tutorialPageResponse = GetTutorialPageResponse.builder()
                    .id(content.getId())
                    .tutorial_id(content.getTutorial().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .number(content.getNumber())
                    .build();
                allTutorialPageResponses.add(tutorialPageResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialPage", allTutorialPageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialPageBySectionId(Long id) {
        List<GetTutorialPageResponse> allTutorialPageResponses = new ArrayList<>();
        List<TutorialPage> listTutorialPage = tutorialPageRepository.findAll();
        listTutorialPage.forEach(content -> {
            if (content.getTutorial().getSection().getId() == id){
                GetTutorialPageResponse tutorialPageResponse = GetTutorialPageResponse.builder()
                    .id(content.getId())
                    .tutorial_id(content.getTutorial().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .number(content.getNumber())
                    .build();
                allTutorialPageResponses.add(tutorialPageResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialPage", allTutorialPageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTutorialPageResponse getTutorialPageById(Long id) {
        Optional<TutorialPage> tutorialPageOpt = tutorialPageRepository.findById(id);
        TutorialPage tutorialPage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialPage.not_found");
        });

        return GetTutorialPageResponse.builder()
            .id(tutorialPage.getId())
            .tutorial_id(tutorialPage.getTutorial().getId())
            .name(tutorialPage.getName())
            .description(tutorialPage.getDescription())
            .number(tutorialPage.getNumber())
            .build();
    }

    @Override
    public Long createTutorialPage(CreateTutorialPageRequest createTutorialPageRequest) {

        Optional <Tutorial> tutorialOpt = tutorialRepository.findById(createTutorialPageRequest.getTutorial_id());
        Tutorial tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });
        
        TutorialPage savedTutorialPage = TutorialPage.builder()
                .tutorial(tutorial)
                .name(createTutorialPageRequest.getName())
                .description(createTutorialPageRequest.getDescription())
                .number(createTutorialPageRequest.getNumber())
                .build();
        tutorialPageRepository.save(savedTutorialPage);

        return savedTutorialPage.getId();
    }

    @Override
    public Long removeTutorialPageById(Long id) {
        Optional<TutorialPage> tutorialPageOpt = tutorialPageRepository.findById(id);
        tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialPage.not_found");
        });

        tutorialPageRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTutorialPageById(Long id, CreateTutorialPageRequest createTutorialPageRequest) {
        Optional<TutorialPage> tutorialPageOpt = tutorialPageRepository.findById(id);
        TutorialPage updatedTutorialPage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialPage.not_found");
        });

        Optional <Tutorial> tutorialOpt = tutorialRepository.findById(createTutorialPageRequest.getTutorial_id());
        Tutorial tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });

        updatedTutorialPage.setName(createTutorialPageRequest.getName());
        updatedTutorialPage.setTutorial(tutorial);
        updatedTutorialPage.setDescription(createTutorialPageRequest.getDescription());
        updatedTutorialPage.setNumber(createTutorialPageRequest.getNumber());

        return updatedTutorialPage.getId();
    }
}
