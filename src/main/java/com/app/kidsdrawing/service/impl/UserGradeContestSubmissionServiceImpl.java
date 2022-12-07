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
import com.app.kidsdrawing.entity.Teacher;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeContestSubmissionRepository;
import com.app.kidsdrawing.repository.TeacherRepository;
import com.app.kidsdrawing.service.UserGradeContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeContestSubmissionServiceImpl implements UserGradeContestSubmissionService{
    
    private final UserGradeContestSubmissionRepository userGradeContestSubmissionRepository;
    private final TeacherRepository teacherRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmission() {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        listUserGradeContestSubmission.forEach(content -> {
            GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                .student_id(content.getContestSubmission().getStudent().getId())
                .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                .contest_id(content.getContestSubmission().getContest().getId())
                .contest_name(content.getContestSubmission().getContest().getName())
                .contest_submission_id(content.getContestSubmission().getId())
                .feedback(content.getFeedback())
                .url_conest_submission(content.getContestSubmission().getImage_url())
                .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                .start_time(content.getContestSubmission().getContest().getStart_time())
                .end_time(content.getContestSubmission().getContest().getEnd_time())
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByContestIdAndTeacherId(Long contest_id, Long teacher_id) {
        List<GetUserGradeContestSubmissionResponse> allUserGradeContestSubmissionResponses = new ArrayList<>();
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findByContestAndTeacher(contest_id, teacher_id);
        listUserGradeContestSubmission.forEach(content -> {
            GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                .student_id(content.getContestSubmission().getStudent().getId())
                .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                .contest_id(content.getContestSubmission().getContest().getId())
                .contest_name(content.getContestSubmission().getContest().getName())
                .contest_submission_id(content.getContestSubmission().getId())
                .feedback(content.getFeedback())
                .url_conest_submission(content.getContestSubmission().getImage_url())
                .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                .start_time(content.getContestSubmission().getContest().getStart_time())
                .end_time(content.getContestSubmission().getContest().getEnd_time())
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
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findByTeacherId2(id);
        listUserGradeContestSubmission.forEach(content -> {
            GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .student_id(content.getContestSubmission().getStudent().getId())
                .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                .contest_id(content.getContestSubmission().getContest().getId())
                .contest_name(content.getContestSubmission().getContest().getName())
                .contest_submission_id(content.getContestSubmission().getId())
                .feedback(content.getFeedback())
                .url_conest_submission(content.getContestSubmission().getImage_url())
                .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                .start_time(content.getContestSubmission().getContest().getStart_time())
                .end_time(content.getContestSubmission().getContest().getEnd_time())
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
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findByStudent(id);
        listUserGradeContestSubmission.forEach(content -> {
            if (content.getScore() == null) {
                GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                    .student_id(content.getContestSubmission().getStudent().getId())
                    .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                    .contest_id(content.getContestSubmission().getContest().getId())
                    .contest_name(content.getContestSubmission().getContest().getName())
                    .contest_submission_id(content.getContestSubmission().getId())
                    .feedback(content.getFeedback())
                    .url_conest_submission(content.getContestSubmission().getImage_url())
                    .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                    .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                    .start_time(content.getContestSubmission().getContest().getStart_time())
                    .end_time(content.getContestSubmission().getContest().getEnd_time())
                    .score((float) 0)
                    .time(content.getTime())
                    .build();
                    allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
                }
              
                else {
                    GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                        .teacher_id(content.getTeacher().getId())
                        .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                        .student_id(content.getContestSubmission().getStudent().getId())
                        .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                        .contest_id(content.getContestSubmission().getContest().getId())
                        .contest_name(content.getContestSubmission().getContest().getName())
                        .contest_submission_id(content.getContestSubmission().getId())
                        .feedback(content.getFeedback())
                        .url_conest_submission(content.getContestSubmission().getImage_url())
                        .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                        .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                        .start_time(content.getContestSubmission().getContest().getStart_time())
                        .end_time(content.getContestSubmission().getContest().getEnd_time())
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
        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findByContest(id);

        listUserGradeContestSubmission.forEach(content -> {
            if (content.getScore() == null) {
                GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                    .student_id(content.getContestSubmission().getStudent().getId())
                    .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                    .contest_id(content.getContestSubmission().getContest().getId())
                    .contest_name(content.getContestSubmission().getContest().getName())
                    .contest_submission_id(content.getContestSubmission().getId())
                    .url_conest_submission(content.getContestSubmission().getImage_url())
                    .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                    .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                    .start_time(content.getContestSubmission().getContest().getStart_time())
                    .end_time(content.getContestSubmission().getContest().getEnd_time())
                    .feedback(content.getFeedback())
                    .score((float) 0)
                    .time(content.getTime())
                    .build();
                allUserGradeContestSubmissionResponses.add(userGradeContestSubmissionResponse);
            }
            else {
                GetUserGradeContestSubmissionResponse userGradeContestSubmissionResponse = GetUserGradeContestSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                    .student_id(content.getContestSubmission().getStudent().getId())
                    .student_name(content.getContestSubmission().getStudent().getUser().getUsername() + " - " + content.getContestSubmission().getStudent().getUser().getFirstName() + " " + content.getContestSubmission().getStudent().getUser().getLastName())
                    .contest_id(content.getContestSubmission().getContest().getId())
                    .contest_name(content.getContestSubmission().getContest().getName())
                    .contest_submission_id(content.getContestSubmission().getId())
                    .url_conest_submission(content.getContestSubmission().getImage_url())
                    .art_age_name(content.getContestSubmission().getContest().getArtAges().getName())
                    .art_type_name(content.getContestSubmission().getContest().getArtTypes().getName())
                    .start_time(content.getContestSubmission().getContest().getStart_time())
                    .end_time(content.getContestSubmission().getContest().getEnd_time())
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
    public GetUserGradeContestSubmissionResponse getUserGradeContestSubmissionById(Long teacher_id, Long contest_submission_id) {
        Optional<UserGradeContestSubmission> userGradeContestSubmissionOpt = userGradeContestSubmissionRepository.findByTeacherIdAndContestSubmissionId(teacher_id, contest_submission_id);
        UserGradeContestSubmission userGradeContestSubmission = userGradeContestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContestSubmission.not_found");
        });

        return GetUserGradeContestSubmissionResponse.builder()
            .teacher_id(userGradeContestSubmission.getTeacher().getId())
            .teacher_name(userGradeContestSubmission.getTeacher().getUser().getUsername() + " - " + userGradeContestSubmission.getTeacher().getUser().getFirstName() + " " + userGradeContestSubmission.getTeacher().getUser().getLastName())
            .student_id(userGradeContestSubmission.getContestSubmission().getStudent().getId())
            .student_name(userGradeContestSubmission.getContestSubmission().getStudent().getUser().getUsername() + " - " + userGradeContestSubmission.getContestSubmission().getStudent().getUser().getFirstName() + " " + userGradeContestSubmission.getContestSubmission().getStudent().getUser().getLastName())
            .contest_id(userGradeContestSubmission.getContestSubmission().getContest().getId())
            .contest_name(userGradeContestSubmission.getContestSubmission().getContest().getName())
            .contest_submission_id(userGradeContestSubmission.getContestSubmission().getId())
            .feedback(userGradeContestSubmission.getFeedback())
            .url_conest_submission(userGradeContestSubmission.getContestSubmission().getImage_url())
            .art_age_name(userGradeContestSubmission.getContestSubmission().getContest().getArtAges().getName())
            .art_type_name(userGradeContestSubmission.getContestSubmission().getContest().getArtTypes().getName())
            .start_time(userGradeContestSubmission.getContestSubmission().getContest().getStart_time())
            .end_time(userGradeContestSubmission.getContestSubmission().getContest().getEnd_time())
            .score(userGradeContestSubmission.getScore())
            .time(userGradeContestSubmission.getTime())
            .build();
    }

    @Override
    public Long createUserGradeContestSubmission(CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {

        Optional <Teacher> userOpt = teacherRepository.findById1(createUserGradeContestSubmissionRequest.getTeacher_id());
        Teacher teacher = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById1(createUserGradeContestSubmissionRequest.getContest_submission_id());
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
    public Long removeUserGradeContestSubmissionById(Long teacher_id, Long contest_submission_id) {
        Optional<UserGradeContestSubmission> userGradeContestSubmissionOpt = userGradeContestSubmissionRepository.findByTeacherIdAndContestSubmissionId(teacher_id, contest_submission_id);
        userGradeContestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContestSubmission.not_found");
        });

        userGradeContestSubmissionRepository.deleteById(teacher_id, contest_submission_id);
        return teacher_id;
    }

    @Override
    public Long updateUserGradeContestSubmissionById(Long teacher_id, Long submission_id, CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {
        //UserGradeContestSubmissionKey index = new UserGradeContestSubmissionKey(teacher_id, submission_id);

        List<UserGradeContestSubmission> listUserGradeContestSubmission = userGradeContestSubmissionRepository.findAll();
        listUserGradeContestSubmission.forEach(content-> {
            if (content.getTeacher().getId().compareTo(teacher_id) == 0  && content.getContestSubmission().getId().compareTo(submission_id) == 0){
                UserGradeContestSubmission updatedUserGradeContestSubmission = content;
                Optional <Teacher> userOpt = teacherRepository.findById1(createUserGradeContestSubmissionRequest.getTeacher_id());
                Teacher teacher = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_teacher.not_found");
                });;
            
                Optional <ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById1(createUserGradeContestSubmissionRequest.getContest_submission_id());
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
