package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplatePageResponse;
import com.app.kidsdrawing.entity.TutorialTemplatePage;
import com.app.kidsdrawing.entity.TutorialTemplate;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TutorialTemplatePageRepository;
import com.app.kidsdrawing.repository.TutorialTemplateRepository;
import com.app.kidsdrawing.service.TutorialTemplatePageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TutorialTemplatePageServiceImpl implements TutorialTemplatePageService{
    
    private final TutorialTemplatePageRepository tutorialTemplatePageRepository;
    private final TutorialTemplateRepository tutorialTemplateRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplatePage() {
        List<GetTutorialTemplatePageResponse> allTutorialTemplatePageResponses = new ArrayList<>();
        List<TutorialTemplatePage> listTutorialTemplatePage = tutorialTemplatePageRepository.findAll();
        listTutorialTemplatePage.forEach(content -> {
            GetTutorialTemplatePageResponse tutorialPageResponse = GetTutorialTemplatePageResponse.builder()
                .id(content.getId())
                .tutorial_template_id(content.getTutorialTemplate().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .build();
            allTutorialTemplatePageResponses.add(tutorialPageResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialTemplatePage", allTutorialTemplatePageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageByTutorialTemplateId(UUID id) {
        List<GetTutorialTemplatePageResponse> allTutorialTemplatePageResponses = new ArrayList<>();
        List<TutorialTemplatePage> listTutorialTemplatePage = tutorialTemplatePageRepository.findByTutorialTemplateId(id);
        listTutorialTemplatePage.forEach(content -> {
            GetTutorialTemplatePageResponse tutorialPageResponse = GetTutorialTemplatePageResponse.builder()
                .id(content.getId())
                .tutorial_template_id(content.getTutorialTemplate().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .build();
            allTutorialTemplatePageResponses.add(tutorialPageResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialTemplatePage", allTutorialTemplatePageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageBySectionTemplateId(UUID id) {
        List<GetTutorialTemplatePageResponse> allTutorialTemplatePageResponses = new ArrayList<>();
        List<TutorialTemplatePage> listTutorialTemplatePage = tutorialTemplatePageRepository.findBySectionTemplateId(id);
        listTutorialTemplatePage.forEach(content -> {
            if (content.getTutorialTemplate().getSectionTemplate().getId().compareTo(id) == 0){
                GetTutorialTemplatePageResponse tutorialPageResponse = GetTutorialTemplatePageResponse.builder()
                    .id(content.getId())
                    .tutorial_template_id(content.getTutorialTemplate().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .number(content.getNumber())
                    .build();
                allTutorialTemplatePageResponses.add(tutorialPageResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialTemplatePage", allTutorialTemplatePageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTutorialTemplatePageResponse getTutorialTemplatePageById(UUID id) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById2(id);
        TutorialTemplatePage tutorialPage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        return GetTutorialTemplatePageResponse.builder()
            .id(tutorialPage.getId())
            .tutorial_template_id(tutorialPage.getTutorialTemplate().getId())
            .name(tutorialPage.getName())
            .description(tutorialPage.getDescription())
            .number(tutorialPage.getNumber())
            .build();
    }

    @Override
    public UUID createTutorialTemplatePage(CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {

        Optional <TutorialTemplate> tutorialOpt = tutorialTemplateRepository.findById1(createTutorialTemplatePageRequest.getTutorial_template_id());
        TutorialTemplate tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });
        
        TutorialTemplatePage savedTutorialTemplatePage = TutorialTemplatePage.builder()
                .tutorialTemplate(tutorial)
                .name(createTutorialTemplatePageRequest.getName())
                .description(createTutorialTemplatePageRequest.getDescription())
                .number(createTutorialTemplatePageRequest.getNumber())
                .build();
        tutorialTemplatePageRepository.save(savedTutorialTemplatePage);

        return savedTutorialTemplatePage.getId();
    }

    @Override
    public UUID removeTutorialTemplatePageById(UUID id) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById1(id);
        tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        tutorialTemplatePageRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateTutorialTemplatePageById(UUID id, CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById1(id);
        TutorialTemplatePage updatedTutorialTemplatePage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        Optional <TutorialTemplate> tutorialOpt = tutorialTemplateRepository.findById1(createTutorialTemplatePageRequest.getTutorial_template_id());
        TutorialTemplate tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });

        updatedTutorialTemplatePage.setName(createTutorialTemplatePageRequest.getName());
        updatedTutorialTemplatePage.setTutorialTemplate(tutorial);
        updatedTutorialTemplatePage.setDescription(createTutorialTemplatePageRequest.getDescription());
        updatedTutorialTemplatePage.setNumber(createTutorialTemplatePageRequest.getNumber());

        return updatedTutorialTemplatePage.getId();
    }
}
