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

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;
import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
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
    private final UserGradeContestSubmissionRepository userGradeContestSubmissionRepository;


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

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByStudentId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            if (content.getStudent().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByContestId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionGradedResponses = new ArrayList<>();
        List<GetContestSubmissionResponse> allContestSubmissionNotGradedResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository.findByContestId(id);
        listContestSubmissionByContest.forEach(contest_submission -> {
            if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())){
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
                allContestSubmissionGradedResponses.add(contestSubmissionResponse);
            }
            else {
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
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_graded", allContestSubmissionNotGradedResponses);
        response.put("contest_graded", allContestSubmissionGradedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetContestSubmissionResponse getContestSubmissionById(Long id) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById(id);
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

        Optional <User> studentOpt = userRepository.findById(createContestSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById(createContestSubmissionRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });
        
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
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById(id);
        contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        contestSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateContestSubmissionById(Long id, CreateContestSubmissionRequest createContestSubmissionRequest) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findById(id);
        ContestSubmission updatedContestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        Optional <User> studentOpt = userRepository.findById(createContestSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById(createContestSubmissionRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        updatedContestSubmission.setContest(contest);
        updatedContestSubmission.setStudent(student);
        updatedContestSubmission.setImage_url(createContestSubmissionRequest.getImage_url());

        return updatedContestSubmission.getId();
    }
}
