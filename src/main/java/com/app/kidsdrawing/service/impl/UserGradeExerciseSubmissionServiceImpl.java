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

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmissionKey;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserGradeExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeExerciseSubmissionServiceImpl implements UserGradeExerciseSubmissionService{
    
    private final UserGradeExerciseSubmissionRepository userGradeExerciseSubmissionRepository;
    private final UserRepository userRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission() {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                .student_id(content.getStudent().getId())
                .exercise_submission_id(content.getExerciseSubmission().getId())
                .feedback(content.getFeedback())
                .score(content.getScore())
                .time(content.getTime())
                .build();
            allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getStudent().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getTeachSemester().getTeacher().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(Long exercise_id, Long class_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == class_id && content.getExerciseSubmission().getExercise().getId() == exercise_id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
        UserGradeExerciseSubmission userGradeExerciseSubmission = userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        return GetUserGradeExerciseSubmissionResponse.builder()
            .student_id(userGradeExerciseSubmission.getStudent().getId())
            .exercise_submission_id(userGradeExerciseSubmission.getExerciseSubmission().getId())
            .feedback(userGradeExerciseSubmission.getFeedback())
            .score(userGradeExerciseSubmission.getScore())
            .time(userGradeExerciseSubmission.getTime())
            .build();
    }

    @Override
    public Long createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {

        Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getStudent_id());
        User student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        UserGradeExerciseSubmissionKey id = new UserGradeExerciseSubmissionKey(student.getId(),exerciseSubmission.getId());
        
        UserGradeExerciseSubmission savedUserGradeExerciseSubmission = UserGradeExerciseSubmission.builder()
                .id(id)
                .student(student)
                .exerciseSubmission(exerciseSubmission)
                .score(createUserGradeExerciseSubmissionRequest.getScore())
                .feedback(createUserGradeExerciseSubmissionRequest.getFeedback())
                .build();
        userGradeExerciseSubmissionRepository.save(savedUserGradeExerciseSubmission);

        return savedUserGradeExerciseSubmission.getStudent().getId();
    }

    @Override
    public Long removeUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
        userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        userGradeExerciseSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserGradeExerciseSubmissionById(Long student_id, Long submission_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        //UserGradeExerciseSubmissionKey index = new UserGradeExerciseSubmissionKey(student_id, submission_id);

        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content-> {
            if (content.getStudent().getId() == student_id && content.getExerciseSubmission().getId() == submission_id){
                UserGradeExerciseSubmission updatedUserGradeExerciseSubmission = content;
                Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getStudent_id());
                User student = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_student.not_found");
                });
            
                Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
                ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
                });
            
                UserGradeExerciseSubmissionKey idx = new UserGradeExerciseSubmissionKey(student.getId(), exerciseSubmission.getId());
            
                updatedUserGradeExerciseSubmission.setScore(createUserGradeExerciseSubmissionRequest.getScore());
                updatedUserGradeExerciseSubmission.setId(idx);
                updatedUserGradeExerciseSubmission.setFeedback(createUserGradeExerciseSubmissionRequest.getFeedback());
                updatedUserGradeExerciseSubmission.setExerciseSubmission(exerciseSubmission);
                updatedUserGradeExerciseSubmission.setStudent(student);
            }
        });

        return student_id;
    }
}
