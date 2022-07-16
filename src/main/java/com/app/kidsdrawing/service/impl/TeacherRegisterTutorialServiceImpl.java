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

import com.app.kidsdrawing.dto.CreateStatusTeacherRegisterTutorialRequest;
import com.app.kidsdrawing.dto.CreateTeacherRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterTutorialResponse;
import com.app.kidsdrawing.entity.TeacherRegisterTutorial;
import com.app.kidsdrawing.entity.Tutorial;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TeacherRegisterTutorialRepository;
import com.app.kidsdrawing.repository.TutorialRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherRegisterTutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherRegisterTutorialServiceImpl implements TeacherRegisterTutorialService{
    
    private final TeacherRegisterTutorialRepository teacherRegisterTutorialRepository;
    private final TutorialRepository tutorialRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorial() {
        List<GetTeacherRegisterTutorialResponse> allTeacherRegisterTutorialResponses = new ArrayList<>();
        List<TeacherRegisterTutorial> listTeacherRegisterTutorial = teacherRegisterTutorialRepository.findAll();
        listTeacherRegisterTutorial.forEach(content -> {
            GetTeacherRegisterTutorialResponse teacherRegisterTutorialResponse = GetTeacherRegisterTutorialResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .reviewer_id(content.getReviewer().getId())
                .tutorial_id(content.getTutorial().getId())
                .status(content.getStatus())
                .time(content.getTime())
                .build();
            allTeacherRegisterTutorialResponses.add(teacherRegisterTutorialResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_tutorial", allTeacherRegisterTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorialByTeacherId(Long id) {
        List<GetTeacherRegisterTutorialResponse> allTeacherRegisterTutorialResponses = new ArrayList<>();
        List<TeacherRegisterTutorial> listTeacherRegisterTutorial = teacherRegisterTutorialRepository.findAll();
        listTeacherRegisterTutorial.forEach(content -> {
            if (content.getTeacher().getId() == id){
                GetTeacherRegisterTutorialResponse teacherRegisterTutorialResponse = GetTeacherRegisterTutorialResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .reviewer_id(content.getReviewer().getId())
                    .tutorial_id(content.getTutorial().getId())
                    .status(content.getStatus())
                    .time(content.getTime())
                    .build();
                allTeacherRegisterTutorialResponses.add(teacherRegisterTutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_tutorial", allTeacherRegisterTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorialByTutorialId(Long id) {
        List<GetTeacherRegisterTutorialResponse> allTeacherRegisterTutorialResponses = new ArrayList<>();
        List<TeacherRegisterTutorial> listTeacherRegisterTutorial = teacherRegisterTutorialRepository.findAll();
        listTeacherRegisterTutorial.forEach(content -> {
            if (content.getTutorial().getId() == id){
                GetTeacherRegisterTutorialResponse teacherRegisterTutorialResponse = GetTeacherRegisterTutorialResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .reviewer_id(content.getReviewer().getId())
                    .tutorial_id(content.getTutorial().getId())
                    .status(content.getStatus())
                    .time(content.getTime())
                    .build();
                allTeacherRegisterTutorialResponses.add(teacherRegisterTutorialResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_tutorial", allTeacherRegisterTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherRegisterTutorialResponse getTeacherRegisterTutorialById(Long id) {
        Optional<TeacherRegisterTutorial> teacherRegisterTutorialOpt = teacherRegisterTutorialRepository.findById(id);
        TeacherRegisterTutorial teacherRegisterTutorial = teacherRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterTutorial.not_found");
        });

        return GetTeacherRegisterTutorialResponse.builder()
            .id(teacherRegisterTutorial.getId())
            .teacher_id(teacherRegisterTutorial.getTeacher().getId())
            .reviewer_id(teacherRegisterTutorial.getReviewer().getId())
            .tutorial_id(teacherRegisterTutorial.getTutorial().getId())
            .status(teacherRegisterTutorial.getStatus())
            .time(teacherRegisterTutorial.getTime())
            .build();
    }

    @Override
    public Long createTeacherRegisterTutorial(CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest) {

        Optional <User> teacherOpt = userRepository.findById(createTeacherRegisterTutorialRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <Tutorial> tutorialOpt = tutorialRepository.findById(createTeacherRegisterTutorialRequest.getTutorial_id());
        Tutorial tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });

        Optional <User> reviewerOpt = userRepository.findById(createTeacherRegisterTutorialRequest.getReviewer_id());
        User reviewer = reviewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_reviewer.not_found");
        });

        
        TeacherRegisterTutorial savedTeacherRegisterTutorial = TeacherRegisterTutorial.builder()
                .teacher(teacher)
                .reviewer(reviewer)
                .tutorial(tutorial)
                .status(createTeacherRegisterTutorialRequest.getStatus())
                .build();
        teacherRegisterTutorialRepository.save(savedTeacherRegisterTutorial);

        return savedTeacherRegisterTutorial.getId();
    }

    @Override
    public Long removeTeacherRegisterTutorialById(Long id) {
        Optional<TeacherRegisterTutorial> teacherRegisterTutorialOpt = teacherRegisterTutorialRepository.findById(id);
        teacherRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterTutorial.not_found");
        });

        teacherRegisterTutorialRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherRegisterTutorialById(Long id, CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest) {
        Optional<TeacherRegisterTutorial> teacherRegisterTutorialOpt = teacherRegisterTutorialRepository.findById(id);
        TeacherRegisterTutorial updatedTeacherRegisterTutorial = teacherRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterTutorial.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById(createTeacherRegisterTutorialRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <Tutorial> tutorialOpt = tutorialRepository.findById(createTeacherRegisterTutorialRequest.getTutorial_id());
        Tutorial tutorial = tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.tutorial.not_found");
        });

        updatedTeacherRegisterTutorial.setTeacher(teacher);
        updatedTeacherRegisterTutorial.setTutorial(tutorial);
        updatedTeacherRegisterTutorial.setStatus(createTeacherRegisterTutorialRequest.getStatus());

        return updatedTeacherRegisterTutorial.getId();
    }

    @Override
    public Long updateStatusTeacherRegisterTutorialById(Long id, CreateStatusTeacherRegisterTutorialRequest createReviewTeacherRegisterTutorialRequest) {
        Optional<TeacherRegisterTutorial> teacherRegisterTutorialOpt = teacherRegisterTutorialRepository.findById(id);
        TeacherRegisterTutorial updatedTeacherRegisterTutorial = teacherRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterTutorial.not_found");
        });

        Optional <User> reviewerOpt = userRepository.findById(createReviewTeacherRegisterTutorialRequest.getReviewer_id());
        User reviewer = reviewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_reviewer.not_found");
        });

        updatedTeacherRegisterTutorial.setStatus(createReviewTeacherRegisterTutorialRequest.getStatus());
        updatedTeacherRegisterTutorial.setReviewer(reviewer);

        return updatedTeacherRegisterTutorial.getId();
    }
}
