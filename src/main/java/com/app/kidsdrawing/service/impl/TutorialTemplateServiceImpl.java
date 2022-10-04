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

import com.app.kidsdrawing.dto.CreateTutorialTemplateRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplateResponse;
import com.app.kidsdrawing.entity.TutorialTemplate;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TutorialTemplateRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.service.TutorialTemplateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TutorialTemplateServiceImpl implements TutorialTemplateService{
    
    private final TutorialTemplateRepository tutorialTemplateRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplate() {
        List<GetTutorialTemplateResponse> allTutorialTemplateResponses = new ArrayList<>();
        List<TutorialTemplate> listTutorialTemplate = tutorialTemplateRepository.findAll();
        listTutorialTemplate.forEach(content -> {
            GetTutorialTemplateResponse tutorialTemplateResponse = GetTutorialTemplateResponse.builder()
                .id(content.getId())
                .section_template_id(content.getSectionTemplate().getId())
                .name(content.getName())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allTutorialTemplateResponses.add(tutorialTemplateResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialTemplate", allTutorialTemplateResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialTemplateBySectionTemplate(UUID id) {
        List<GetTutorialTemplateResponse> allTutorialTemplateResponses = new ArrayList<>();
        List<TutorialTemplate> listTutorialTemplate = tutorialTemplateRepository.findAll();
        listTutorialTemplate.forEach(content -> {
            if (content.getSectionTemplate().getId().compareTo(id) == 0){
                GetTutorialTemplateResponse tutorialTemplateResponse = GetTutorialTemplateResponse.builder()
                    .id(content.getId())
                    .section_template_id(id)
                    .name(content.getName())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTutorialTemplateResponses.add(tutorialTemplateResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("TutorialTemplate", allTutorialTemplateResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTutorialTemplateResponse getTutorialTemplateById(UUID id) {
        Optional<TutorialTemplate> tutorialTemplateOpt = tutorialTemplateRepository.findById2(id);
        TutorialTemplate tutorialTemplate = tutorialTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplate.not_found");
        });

        return GetTutorialTemplateResponse.builder()
            .id(tutorialTemplate.getId())
            .section_template_id(tutorialTemplate.getSectionTemplate().getId())
            .name(tutorialTemplate.getName())
            .create_time(tutorialTemplate.getCreate_time())
            .update_time(tutorialTemplate.getUpdate_time())
            .build();
    }

    @Override
    public UUID createTutorialTemplate(CreateTutorialTemplateRequest createTutorialTemplateRequest) {

        Optional <SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findById1(createTutorialTemplateRequest.getSection_template_id());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.sectionTemplate.not_found");
        });

        Optional <User> userOpt = userRepository.findById1(createTutorialTemplateRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });
        
        TutorialTemplate savedTutorialTemplate = TutorialTemplate.builder()
                .sectionTemplate(sectionTemplate)
                .name(createTutorialTemplateRequest.getName())
                .creator(creator)
                .build();
        tutorialTemplateRepository.save(savedTutorialTemplate);

        return savedTutorialTemplate.getId();
    }

    
    @Override
    public UUID removeTutorialTemplateById(UUID id) {
        Optional<TutorialTemplate> tutorialTemplateOpt = tutorialTemplateRepository.findById1(id);
        tutorialTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplate.not_found");
        });

        tutorialTemplateRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateTutorialTemplateById(UUID id, CreateTutorialTemplateRequest createTutorialTemplateRequest) {
        Optional<TutorialTemplate> tutorialTemplateOpt = tutorialTemplateRepository.findById1(id);
        TutorialTemplate updatedTutorialTemplate = tutorialTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TutorialTemplate.not_found");
        });

        Optional <SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findById1(createTutorialTemplateRequest.getSection_template_id());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.sectionTemplate.not_found");
        });

        updatedTutorialTemplate.setName(createTutorialTemplateRequest.getName());
        updatedTutorialTemplate.setSectionTemplate(sectionTemplate);

        return updatedTutorialTemplate.getId();
    }
}
