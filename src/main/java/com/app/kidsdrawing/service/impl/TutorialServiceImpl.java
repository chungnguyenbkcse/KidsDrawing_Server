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

import com.app.kidsdrawing.dto.CreateTutorialAdminRequest;
import com.app.kidsdrawing.dto.CreateTutorialRequest;
import com.app.kidsdrawing.dto.GetTutorialResponse;
import com.app.kidsdrawing.entity.Tutorial;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TutorialRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.TutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TutorialServiceImpl implements TutorialService{
    
    private final TutorialRepository tutorialRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorial() {
        List<GetTutorialResponse> allTutorialResponses = new ArrayList<>();
        List<Tutorial> listTutorial = tutorialRepository.findAll();
        listTutorial.forEach(content -> {
            GetTutorialResponse tutorialResponse = GetTutorialResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
                .creator_id(content.getCreator().getId())
                .name(content.getName())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allTutorialResponses.add(tutorialResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Tutorial", allTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialBySection(Long id) {
        List<GetTutorialResponse> allTutorialResponses = new ArrayList<>();
        List<Tutorial> listTutorial = tutorialRepository.findAll();
        listTutorial.forEach(content -> {
            if (content.getSection().getId() == id){
                GetTutorialResponse tutorialResponse = GetTutorialResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
                    .creator_id(content.getCreator().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTutorialResponses.add(tutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Tutorial", allTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialByAdminSection(Long id) {
        List<GetTutorialResponse> allTutorialResponses = new ArrayList<>();
        List<Tutorial> listTutorial = tutorialRepository.findAll();
        listTutorial.forEach(content -> {
            if (content.getSection().getId() == id){
                GetTutorialResponse tutorialResponse = GetTutorialResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
                    .creator_id(content.getCreator().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTutorialResponses.add(tutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Tutorial", allTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialByTeacherSection(Long id) {
        List<GetTutorialResponse> allTutorialResponses = new ArrayList<>();
        List<Tutorial> listTutorial = tutorialRepository.findAll();
        listTutorial.forEach(content -> {
            if (content.getSection().getId() == id){
                GetTutorialResponse tutorialResponse = GetTutorialResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
                    .creator_id(content.getCreator().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTutorialResponses.add(tutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Tutorial", allTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTutorialByCreatorSection(Long creator_id, Long section_id) {
        List<GetTutorialResponse> allTutorialResponses = new ArrayList<>();
        List<Tutorial> listTutorial = tutorialRepository.findAll();
        listTutorial.forEach(content -> {
            if (content.getSection().getId() == section_id && content.getCreator().getId() == creator_id){
                GetTutorialResponse tutorialResponse = GetTutorialResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
                    .creator_id(content.getCreator().getId())
                    .name(content.getName())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTutorialResponses.add(tutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Tutorial", allTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTutorialResponse getTutorialById(Long id) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);
        Tutorial tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Tutorial.not_found");
        });

        return GetTutorialResponse.builder()
            .id(tutorial.getId())
            .section_id(tutorial.getSection().getId())
            .creator_id(tutorial.getCreator().getId())
            .name(tutorial.getName())
            .description(tutorial.getDescription())
            .create_time(tutorial.getCreate_time())
            .update_time(tutorial.getUpdate_time())
            .build();
    }

    @Override
    public Long createTutorial(CreateTutorialRequest createTutorialRequest) {

        Optional <Section> sectionOpt = sectionRepository.findById(createTutorialRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createTutorialRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });
        
        Tutorial savedTutorial = Tutorial.builder()
                .section(section)
                .creator(creator)
                .name(createTutorialRequest.getName())
                .description(createTutorialRequest.getDescription())
                .build();
        tutorialRepository.save(savedTutorial);

        return savedTutorial.getId();
    }

    @Override 
    public Long createTutorial(CreateTutorialAdminRequest createTutorialAdminRequest) {
        Optional <Section> sectionOpt = sectionRepository.findById(createTutorialAdminRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createTutorialAdminRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });
        
        Tutorial savedTutorial = Tutorial.builder()
                .section(section)
                .creator(creator)
                .name(createTutorialAdminRequest.getName())
                .description(createTutorialAdminRequest.getDescription())
                .build();
        tutorialRepository.save(savedTutorial);

        return savedTutorial.getId();
    }

    @Override
    public Long removeTutorialById(Long id) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);
        tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Tutorial.not_found");
        });

        tutorialRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTutorialById(Long id, CreateTutorialRequest createTutorialRequest) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);
        Tutorial updatedTutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Tutorial.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createTutorialRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createTutorialRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });

        updatedTutorial.setName(createTutorialRequest.getName());
        updatedTutorial.setDescription(createTutorialRequest.getDescription());
        updatedTutorial.setSection(section);
        updatedTutorial.setCreator(creator);

        return updatedTutorial.getId();
    }
}
