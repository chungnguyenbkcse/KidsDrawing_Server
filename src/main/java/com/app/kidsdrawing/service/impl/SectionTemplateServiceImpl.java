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

import com.app.kidsdrawing.dto.CreateSectionTemplateRequest;
import com.app.kidsdrawing.dto.GetSectionTemplateResponse;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SectionTemplateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SectionTemplateServiceImpl implements SectionTemplateService{
    
    private final SectionTemplateRepository sectionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionTemplate() {
        List<GetSectionTemplateResponse> allSectionTemplateResponses = new ArrayList<>();
        List<SectionTemplate> listSectionTemplate = sectionRepository.findAll();
        listSectionTemplate.forEach(content -> {
            GetSectionTemplateResponse sectionResponse = GetSectionTemplateResponse.builder()
                .id(content.getId())
                .creator_id(content.getUser().getId())
                .course_id(content.getCourse().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .teaching_form(content.getTeaching_form())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allSectionTemplateResponses.add(sectionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("SectionTemplate", allSectionTemplateResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionTemplateByCourseId(Long id) {
        List<GetSectionTemplateResponse> allSectionTemplateResponses = new ArrayList<>();
        List<SectionTemplate> listSectionTemplate = sectionRepository.findAll();
        listSectionTemplate.forEach(content -> {
            if (content.getCourse().getId() == id){
                GetSectionTemplateResponse sectionResponse = GetSectionTemplateResponse.builder()
                    .id(content.getId())
                    .creator_id(content.getUser().getId())
                    .course_id(id)
                    .name(content.getName())
                    .description(content.getDescription())
                    .number(content.getNumber())
                    .teaching_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionTemplateResponses.add(sectionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("SectionTemplate", allSectionTemplateResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSectionTemplateResponse getSectionTemplateById(Long id) {
        Optional<SectionTemplate> sectionOpt = sectionRepository.findById(id);
        SectionTemplate section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SectionTemplate.not_found");
        });

        return GetSectionTemplateResponse.builder()
            .id(section.getId())
            .creator_id(section.getUser().getId())
            .course_id(section.getCourse().getId())
            .name(section.getName())
            .description(section.getDescription())
            .number(section.getNumber())
            .teaching_form(section.getTeaching_form())
            .create_time(section.getCreate_time())
            .update_time(section.getUpdate_time())
            .build();
    }

    @Override
    public Long createSectionTemplate(CreateSectionTemplateRequest createSectionTemplateRequest) {

        Optional <Course> courseOpt = courseRepository.findById(createSectionTemplateRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createSectionTemplateRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        
        SectionTemplate savedSectionTemplate = SectionTemplate.builder()
                .course(course)
                .user(creator)
                .name(createSectionTemplateRequest.getName())
                .description(createSectionTemplateRequest.getDescription())
                .number(createSectionTemplateRequest.getNumber())
                .teaching_form(createSectionTemplateRequest.getTeaching_form())
                .build();
        sectionRepository.save(savedSectionTemplate);

        return savedSectionTemplate.getId();
    }

    @Override
    public Long removeSectionTemplateById(Long id) {
        Optional<SectionTemplate> sectionOpt = sectionRepository.findById(id);
        sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SectionTemplate.not_found");
        });

        sectionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSectionTemplateById(Long id, CreateSectionTemplateRequest createSectionTemplateRequest) {
        Optional<SectionTemplate> sectionOpt = sectionRepository.findById(id);
        SectionTemplate updatedSectionTemplate = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SectionTemplate.not_found");
        });

        Optional <Course> courseOpt = courseRepository.findById(createSectionTemplateRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createSectionTemplateRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedSectionTemplate.setName(createSectionTemplateRequest.getName());
        updatedSectionTemplate.setCourse(course);
        updatedSectionTemplate.setDescription(createSectionTemplateRequest.getDescription());
        updatedSectionTemplate.setUser(creator);
        updatedSectionTemplate.setTeaching_form(createSectionTemplateRequest.getTeaching_form());
        updatedSectionTemplate.setNumber(createSectionTemplateRequest.getNumber());

        return updatedSectionTemplate.getId();
    }
}
