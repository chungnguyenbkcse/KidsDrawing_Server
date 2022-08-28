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

import com.app.kidsdrawing.dto.CreateUserGradeContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestSubmissionResponse;
import com.app.kidsdrawing.entity.UserGradeContestSubmission;
import com.app.kidsdrawing.entity.UserGradeContestSubmissionKey;
import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserGradeContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeContestSubmissionServiceImpl implements UserGradeContestSubmissionService{
    
    private final UserGradeContestSubmissionRepository userGradeContestSubmissionRepository;
    private final UserRepository userRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmission() {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        listUserGradeContestSubmission.forEach(content -> {
            GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                .student_id(content.getContestSubmission().getStudent().getId())
                .student_name(content.getContestSubmission().getStudent().getFirstName() + " " + content.getContestSubmission().getStudent().getLastName())
                .contest_id(content.getContestSubmission().getContest().getId())
                .contest_name(content.getContestSubmission().getContest().getName())
                .contest_submission_id(content.getContestSubmission().getId())
                .feedback(content.getFeedback())
                .score(content.getScore())
                .time(content.getTime())
                .build();
            allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeContestSubmission", allUserGradeContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByTeacherId(Long id) {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findByTeacherId(id);
        listUserGradeContestSubmission.forEach(content -> {
            GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .student_id(content.getContestSubmission().getStudent().getId())
                .student_name(content.getContestSubmission().getStudent().getFirstName() + " " + content.getContestSubmission().getStudent().getLastName())
                .contest_id(content.getContestSubmission().getContest().getId())
                .contest_name(content.getContestSubmission().getContest().getName())
                .contest_submission_id(content.getContestSubmission().getId())
                .feedback(content.getFeedback())
                .score(content.getScore())
                .time(content.getTime())
                .build();
            allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeContestSubmission", allUserGradeContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByStudentId(Long id) {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        listUserGradeContestSubmission.forEach(content -> {
            if (content.getContestSubmission().getStudent().getId() == id){
                GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .student_id(content.getContestSubmission().getStudent().getId())
                    .student_name(content.getContestSubmission().getStudent().getFirstName() + " " + content.getContestSubmission().getStudent().getLastName())
                    .contest_id(content.getContestSubmission().getContest().getId())
                    .contest_name(content.getContestSubmission().getContest().getName())
                    .contest_submission_id(content.getContestSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeContestSubmission", allUserGradeContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByContestId(Long id) {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        List<ContestSubmission> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            if (content.getContest().getId() == id){
                allContestSubmissionResponses.add(content);
            }
        });
        listUserGradeContestSubmission.forEach(content -> {
            if (allContestSubmissionResponses.contains(content.getContestSubmission())){
                GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .student_id(content.getContestSubmission().getStudent().getId())
                    .student_name(content.getContestSubmission().getStudent().getFirstName() + " " + content.getContestSubmission().getStudent().getLastName())
                    .contest_id(content.getContestSubmission().getContest().getId())
                    .contest_name(content.getContestSubmission().getContest().getName())
                    .contest_submission_id(content.getContestSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeContestSubmission", allUserGradeContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserGradeContestSubmissionResponse getUserGradeContestSubmissionById(Long id) {
        Optional<UserGradeContestSubmission> userGradeContestSubmissionOpt = userGradeContestSubmissionRepository.findById(id);
        UserGradeContestSubmission userGradeContestSubmission = userGradeContestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContestSubmission.not_found");
        });

        return GetUserGradeContestSubmissionResponse.builder()
            .teacher_id(userGradeContestSubmission.getTeacher().getId())
            .student_id(userGradeContestSubmission.getContestSubmission().getStudent().getId())
            .student_name(userGradeContestSubmission.getContestSubmission().getStudent().getFirstName() + " " + userGradeContestSubmission.getContestSubmission().getStudent().getLastName())
            .contest_id(userGradeContestSubmission.getContestSubmission().getContest().getId())
            .contest_name(userGradeContestSubmission.getContestSubmission().getContest().getName())
            .contest_submission_id(userGradeContestSubmission.getContestSubmission().getId())
            .feedback(userGradeContestSubmission.getFeedback())
            .score(userGradeContestSubmission.getScore())
            .time(userGradeContestSubmission.getTime())
            .build();
    }

    @Override
    public Long createUserGradeContestSubmission(CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {

        Optional <User> userOpt = userRepository.findById(createUserGradeContestSubmissionRequest.getTeacher_id());
        User teacher = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById(createUserGradeContestSubmissionRequest.getContest_submission_id());
        ContestSubmission contestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        UserGradeContestSubmissionKey id = new UserGradeContestSubmissionKey(teacher.getId(),contestSubmission.getId());
        
        UserGradeContestSubmission savedUserGradeContestSubmission = UserGradeContestSubmission.builder()
                .id(id)
                .teacher(teacher)
                .contestSubmission(contestSubmission)
                .score(createUserGradeContestSubmissionRequest.getScore())
                .feedback(createUserGradeContestSubmissionRequest.getFeedback())
                .build();
        userGradeContestSubmissionRepository.save(savedUserGradeContestSubmission);

        return savedUserGradeContestSubmission.getTeacher().getId();
    }

    @Override
    public Long removeUserGradeContestSubmissionById(Long id) {
        Optional<UserGradeContestSubmission> userGradeContestSubmissionOpt = userGradeContestSubmissionRepository.findById(id);
        userGradeContestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContestSubmission.not_found");
        });

        userGradeContestSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserGradeContestSubmissionById(Long teacher_id, Long submission_id, CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {
        //UserGradeContestSubmissionKey index = new UserGradeContestSubmissionKey(teacher_id, submission_id);

        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        listUserGradeContestSubmission.forEach(content-> {
            if (content.getTeacher().getId() == teacher_id && content.getContestSubmission().getId() == submission_id){
                UserGradeContestSubmission updatedUserGradeContestSubmission = content;
                Optional <User> userOpt = userRepository.findById(createUserGradeContestSubmissionRequest.getTeacher_id());
                User teacher = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_teacher.not_found");
                });
            
                Optional <ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById(createUserGradeContestSubmissionRequest.getContest_submission_id());
                ContestSubmission contestSubmission = contestSubmissionOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.ContestSubmission.not_found");
                });
            
                UserGradeContestSubmissionKey idx = new UserGradeContestSubmissionKey(teacher.getId(), contestSubmission.getId());
            
                updatedUserGradeContestSubmission.setScore(createUserGradeContestSubmissionRequest.getScore());
                updatedUserGradeContestSubmission.setId(idx);
                updatedUserGradeContestSubmission.setFeedback(createUserGradeContestSubmissionRequest.getFeedback());
                updatedUserGradeContestSubmission.setContestSubmission(contestSubmission);
                updatedUserGradeContestSubmission.setTeacher(teacher);
            }
        });

        return teacher_id;
    }
}
