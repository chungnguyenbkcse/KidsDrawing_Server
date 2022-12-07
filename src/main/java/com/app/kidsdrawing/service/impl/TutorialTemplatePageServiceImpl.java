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

import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplatePageResponse;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.TutorialTemplatePage;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.TutorialTemplatePageRepository;
import com.app.kidsdrawing.service.TutorialTemplatePageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TutorialTemplatePageServiceImpl implements TutorialTemplatePageService{
    
    private final TutorialTemplatePageRepository tutorialTemplatePageRepository;
    private final SectionTemplateRepository sectionTemplateRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplatePage() {
        List<GetTutorialTemplatePageResponse> allTutorialTemplatePageResponses = new ArrayList<>();
        List<TutorialTemplatePage> listTutorialTemplatePage = tutorialTemplatePageRepository.findAll();
        listTutorialTemplatePage.forEach(content -> {
            GetTutorialTemplatePageResponse tutorialPageResponse = GetTutorialTemplatePageResponse.builder()
                .id(content.getId())
                .section_template_id(content.getSectionTemplate().getId())
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
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageBySectionTemplateId(Long id) {
        List<GetTutorialTemplatePageResponse> allTutorialTemplatePageResponses = new ArrayList<>();
        List<TutorialTemplatePage> listTutorialTemplatePage = tutorialTemplatePageRepository.findBySectionTemplateId(id);
        listTutorialTemplatePage.forEach(content -> {
            if (content.getSectionTemplate().getId().compareTo(id) == 0){
                GetTutorialTemplatePageResponse tutorialPageResponse = GetTutorialTemplatePageResponse.builder()
                    .id(content.getId())
                    .section_template_id(content.getSectionTemplate().getId())
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
    public GetTutorialTemplatePageResponse getTutorialTemplatePageById(Long id) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById2(id);
        TutorialTemplatePage tutorialPage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        return GetTutorialTemplatePageResponse.builder()
            .id(tutorialPage.getId())
            .section_template_id(tutorialPage.getSectionTemplate().getId())
            .description(tutorialPage.getDescription())
            .number(tutorialPage.getNumber())
            .build();
    }

    @Override
    public Long createTutorialTemplatePage(CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {

        Optional <SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findById1(createTutorialTemplatePageRequest.getSection_template_id());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SectionTemplate.not_found");
        });
        
        TutorialTemplatePage savedTutorialTemplatePage = TutorialTemplatePage.builder()
                .sectionTemplate(sectionTemplate)
                .description(createTutorialTemplatePageRequest.getDescription())
                .number(createTutorialTemplatePageRequest.getNumber())
                .build();
        tutorialTemplatePageRepository.save(savedTutorialTemplatePage);

        return savedTutorialTemplatePage.getId();
    }

    @Override
    public Long removeTutorialTemplatePageById(Long id) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById1(id);
        tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        tutorialTemplatePageRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTutorialTemplatePageById(Long id, CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {
        Optional<TutorialTemplatePage> tutorialPageOpt = tutorialTemplatePageRepository.findById1(id);
        TutorialTemplatePage updatedTutorialTemplatePage = tutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplatePage.not_found");
        });

        Optional <SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findById1(createTutorialTemplatePageRequest.getSection_template_id());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SectionTemplate.not_found");
        });

        updatedTutorialTemplatePage.setSectionTemplate(sectionTemplate);
        updatedTutorialTemplatePage.setDescription(createTutorialTemplatePageRequest.getDescription());
        updatedTutorialTemplatePage.setNumber(createTutorialTemplatePageRequest.getNumber());

        return updatedTutorialTemplatePage.getId();
    }
}
