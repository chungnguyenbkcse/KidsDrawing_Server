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

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionResponse;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.SectionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SectionServiceImpl implements SectionService{
    
    private final SectionRepository sectionRepository;
    private final ClassRepository classRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSection() {
        List<GetSectionResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findAll();
        listSection.forEach(content -> {
            GetSectionResponse sectionResponse = GetSectionResponse.builder()
                .id(content.getId())
                .class_id(content.getClass1().getId())
                .name(content.getName())
                .number(content.getNumber())
                .teach_form(content.getTeaching_form())
                .recording(content.getRecording())
                .message(content.getMessage())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allSectionResponses.add(sectionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionByClassId(Long id) {
        List<GetSectionResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findAll();
        listSection.forEach(content -> {
            if (content.getClass1().getId() == id){
                GetSectionResponse sectionResponse = GetSectionResponse.builder()
                    .id(content.getId())
                    .class_id(content.getClass1().getId())
                    .name(content.getName())
                    .number(content.getNumber())
                    .recording(content.getRecording())
                    .message(content.getMessage())
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionResponses.add(sectionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSectionResponse getSectionById(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        return GetSectionResponse.builder()
            .id(section.getId())
            .class_id(section.getClass1().getId())
            .name(section.getName())
            .number(section.getNumber())
            .recording(section.getRecording())
            .message(section.getMessage())
            .teach_form(section.getTeaching_form())
            .create_time(section.getCreate_time())
            .update_time(section.getUpdate_time())
            .build();
    }

    @Override
    public Long createSection(CreateSectionRequest createSectionRequest) {

        Optional <Class> classOpt = classRepository.findById(createSectionRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        Section savedSection = Section.builder()
                .class1(classes)
                .name(createSectionRequest.getName())
                .number(createSectionRequest.getNumber())
                .recording(createSectionRequest.getRecording())
                .message(createSectionRequest.getMessage())
                .build();
        sectionRepository.save(savedSection);

        return savedSection.getId();
    }

    @Override
    public Long removeSectionById(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);
        sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        sectionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSectionById(Long id, CreateSectionRequest createSectionRequest) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);
        Section updatedSection = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        Optional <Class> classOpt = classRepository.findById(createSectionRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedSection.setName(createSectionRequest.getName());
        updatedSection.setClass1(classes);
        updatedSection.setRecording(createSectionRequest.getRecording());
        updatedSection.setMessage(createSectionRequest.getMessage());
        updatedSection.setNumber(createSectionRequest.getNumber());

        return updatedSection.getId();
    }
}
