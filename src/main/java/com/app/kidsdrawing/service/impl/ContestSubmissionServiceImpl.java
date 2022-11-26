package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;
import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.UserGradeContestSubmission;
import com.app.kidsdrawing.entity.UserGradeContestSubmissionKey;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeContestRepository;
import com.app.kidsdrawing.repository.UserGradeContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ContestSubmissionServiceImpl implements ContestSubmissionService {
    
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final UserGradeContestRepository userGradeContestRepository;
    private final UserGradeContestSubmissionRepository userGradeContestSubmissionRepository;
    private static int count = 0;


    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmission() {
        List<GetContestSubmissionResponse> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                .id(content.getId())
                .contest_id(content.getContest().getId())
                .student_id(content.getStudent().getId())
                .image_url(content.getImage_url())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allContestSubmissionResponses.add(contestSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ContestSubmission", allContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Long createUserGradeContestSubmission(User teacher, ContestSubmission contest_submission) {


        UserGradeContestSubmissionKey id = new UserGradeContestSubmissionKey(teacher.getId(),contest_submission.getId());
        
        UserGradeContestSubmission savedUserGradeContestSubmission = UserGradeContestSubmission.builder()
                .id(id)
                .teacher(teacher)
                .contestSubmission(contest_submission)
                .build();
        userGradeContestSubmissionRepository.save(savedUserGradeContestSubmission);

        return savedUserGradeContestSubmission.getTeacher().getId();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByTeacherAndContest(Long teacher_id, Long contest_id) {
        List<GetContestSubmissionResponse> allContestSubmissionNotGradeResponses = new ArrayList<>();
        List<GetContestSubmissionResponse> allContestSubmissionGradeResponses = new ArrayList<>();
        
        List<UserGradeContestSubmission> userGradeContestSubmissions = userGradeContestSubmissionRepository.findByContestAndTeacher(contest_id, teacher_id);
        userGradeContestSubmissions.forEach(ele -> {
            if (ele.getScore() == null) {
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    .id(ele.getContestSubmission().getId())
                    .contest_id(ele.getContestSubmission().getContest().getId())
                    .student_id(ele.getContestSubmission().getStudent().getId())
                    .image_url(ele.getContestSubmission().getImage_url())
                    .create_time(ele.getContestSubmission().getCreate_time())
                    .update_time(ele.getContestSubmission().getUpdate_time())
                    .build();
                allContestSubmissionNotGradeResponses.add(contestSubmissionResponse);
            }
            else {
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    .id(ele.getContestSubmission().getId())
                    .contest_id(ele.getContestSubmission().getContest().getId())
                    .student_id(ele.getContestSubmission().getStudent().getId())
                    .image_url(ele.getContestSubmission().getImage_url())
                    .create_time(ele.getContestSubmission().getCreate_time())
                    .update_time(ele.getContestSubmission().getUpdate_time())
                    .build();
                    allContestSubmissionGradeResponses.add(contestSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_submission_not_grade", allContestSubmissionNotGradeResponses);
        response.put("contest_submission_grade", allContestSubmissionGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override 
    public Long generationContestSubmissionForTeacher(Long contest_id){
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findByContestId1(contest_id);

        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findByContestId2(contest_id);
        
        int total_teacher = pageUserGradeContest.size();
        int total_contest_submission = listContestSubmission.size();

        int total = total_contest_submission / total_teacher;

        count = 0;
        for (count = 0; count < total_teacher; count++) {
            if (count == total_teacher - 1) {
                List<ContestSubmission> contestSubmissions = listContestSubmission.subList(count*total, total_contest_submission);
                contestSubmissions.forEach(ele -> {
                    createUserGradeContestSubmission(pageUserGradeContest.get(count).getUser(), ele);
                });
            }
            else {
                List<ContestSubmission> contestSubmissions = listContestSubmission.subList(count*total, (count+1)*total);
                contestSubmissions.forEach(ele -> {
                    createUserGradeContestSubmission(pageUserGradeContest.get(count).getUser(), ele);
                });
            } 
        }

        return contest_id;

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByStudentId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            if (content.getStudent().getId().compareTo(id) == 0){
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    .id(content.getId())
                    .contest_id(content.getContest().getId())
                    .student_id(content.getStudent().getId())
                    .contest_name(content.getContest().getName())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allContestSubmissionResponses.add(contestSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ContestSubmission", allContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public Long checkGenerationContestSubmissionForTeacher(Long contest_id)  {
        List<UserGradeContestSubmission> userGradeContestSubmissions = userGradeContestSubmissionRepository.findTotalByContest(contest_id);

        if (userGradeContestSubmissions.size() > 0 ) {
            return (long) 1;
        }
        else {
            throw new EntityNotFoundException("exception.check_generation.not_found");
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByContestId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionGradedResponses = new ArrayList<>();
        List<GetContestSubmissionResponse> allContestSubmissionNotGradedResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository.findByContestId2(id);
        listContestSubmissionByContest.forEach(contest_submission -> {
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    .id(contest_submission.getId())
                    .contest_id(contest_submission.getContest().getId())
                    .student_id(contest_submission.getStudent().getId())
                    .contest_name(contest_submission.getContest().getName())
                    .student_name(contest_submission.getStudent().getFirstName() + " " + contest_submission.getStudent().getLastName())
                    .image_url(contest_submission.getImage_url())
                    .create_time(contest_submission.getCreate_time())
                    .update_time(contest_submission.getUpdate_time())
                    .build();
                allContestSubmissionNotGradedResponses.add(contestSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_graded", allContestSubmissionNotGradedResponses);
        response.put("contest_graded", allContestSubmissionGradedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetContestSubmissionResponse getContestSubmissionByConetestAndStudent(Long contest_id, Long student_id) {
        List<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findByContestAndStudent(contest_id, student_id);
        if (contestSubmissionOpt.size() <= 0) {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        }

        return GetContestSubmissionResponse.builder()
            .id(contestSubmissionOpt.get(0).getId())
            .contest_id(contestSubmissionOpt.get(0).getContest().getId())
            .student_id(contestSubmissionOpt.get(0).getStudent().getId())
            .contest_name(contestSubmissionOpt.get(0).getContest().getName())
            .student_name(contestSubmissionOpt.get(0).getStudent().getFirstName() + " " + contestSubmissionOpt.get(0).getStudent().getLastName())
            .image_url(contestSubmissionOpt.get(0).getImage_url())
            .create_time(contestSubmissionOpt.get(0).getCreate_time())
            .update_time(contestSubmissionOpt.get(0).getUpdate_time())
            .build();
    }

    @Override
    public GetContestSubmissionResponse getContestSubmissionById(Long id) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById2(id);
        ContestSubmission contestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        return GetContestSubmissionResponse.builder()
            .id(contestSubmission.getId())
            .contest_id(contestSubmission.getContest().getId())
            .student_id(contestSubmission.getStudent().getId())
            .contest_name(contestSubmission.getContest().getName())
            .student_name(contestSubmission.getStudent().getFirstName() + " " + contestSubmission.getStudent().getLastName())
            .image_url(contestSubmission.getImage_url())
            .create_time(contestSubmission.getCreate_time())
            .update_time(contestSubmission.getUpdate_time())
            .build();
    }

    @Override
    public Long createContestSubmission(CreateContestSubmissionRequest createContestSubmissionRequest) {

        Optional <User> studentOpt = userRepository.findById1(createContestSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById1(createContestSubmissionRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((contest.getEnd_time()))) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }
        
        ContestSubmission savedContestSubmission = ContestSubmission.builder()
                .student(student)
                .contest(contest)
                .image_url(createContestSubmissionRequest.getImage_url())
                .build();
        contestSubmissionRepository.save(savedContestSubmission);

        return savedContestSubmission.getId();
    }

    @Override
    public Long removeContestSubmissionById(Long id) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById1(id);
        contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        contestSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateContestSubmissionById(Long id, CreateContestSubmissionRequest createContestSubmissionRequest) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById1(id);
        ContestSubmission updatedContestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        Optional <User> studentOpt = userRepository.findById1(createContestSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById1(createContestSubmissionRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        updatedContestSubmission.setContest(contest);
        updatedContestSubmission.setStudent(student);
        updatedContestSubmission.setImage_url(createContestSubmissionRequest.getImage_url());

        return updatedContestSubmission.getId();
    }
}
