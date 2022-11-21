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

import com.app.kidsdrawing.dto.CreateUserRegisterJoinContestRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinContestResponse;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.entity.UserRegisterJoinContest;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinContestRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserRegisterJoinContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterJoinContestServiceImpl implements UserRegisterJoinContestService{
    
    private final UserRegisterJoinContestRepository userRegisterJoinContestRepository;
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByStudentId(Long id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        List<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findByStudentId2(id);
        pageUserRegisterJoinContest.forEach(content -> {
                GetUserRegisterJoinContestResponse userRegisterJoinContestResponse = GetUserRegisterJoinContestResponse.builder()
                    .id(content.getId())
                    .student_id(id)
                    .contest_id(content.getContest().getId())
                    .build();
                    allUserRegisterJoinContestResponses.add(userRegisterJoinContestResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserRegisterJoinContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByContestId(Long id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        List<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findByContestId2(id);
        pageUserRegisterJoinContest.forEach(content -> {
                GetUserRegisterJoinContestResponse userRegisterJoinContestResponse = GetUserRegisterJoinContestResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .contest_id(id)
                    .build();
                    allUserRegisterJoinContestResponses.add(userRegisterJoinContestResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserRegisterJoinContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterJoinContestResponse getUserRegisterJoinContestById(Long id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById2(id);
        UserRegisterJoinContest userRegisterJoinContest = userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        return GetUserRegisterJoinContestResponse.builder()
                .id(userRegisterJoinContest.getId())
                .student_id(userRegisterJoinContest.getStudent().getId())
                .contest_id(userRegisterJoinContest.getContest().getId())
                .build();
    }

    @Override
    public Long createUserRegisterJoinContest(CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Optional <Contest> contestOpt = contestRepository.findById1(createUserRegisterJoinContestRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.contest.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((contest.getStart_time())) && time_now.isBefore(contest.getRegistration_time())) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }

        List<UserRegisterJoinContest> listUserRegisterJoinContest = userRegisterJoinContestRepository.findByContestId1(createUserRegisterJoinContestRequest.getContest_id());
        if (listUserRegisterJoinContest.size() >= contest.getMax_participant()) {
            throw new EntityNotFoundException("exception.max_participant.not_register");
        }

        Optional <User> teacherOpt = userRepository.findById1(createUserRegisterJoinContestRequest.getStudent_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        UserRegisterJoinContest savedUserRegisterJoinContest = UserRegisterJoinContest.builder()
                .student(teacher)
                .contest(contest)
                .build();
        userRegisterJoinContestRepository.save(savedUserRegisterJoinContest);

        return savedUserRegisterJoinContest.getId();
    }

    @Override
    public Long removeUserRegisterJoinContestById(Long id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById1(id);
        userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        userRegisterJoinContestRepository.deleteById(id);
        return id;
    }

    @Override
    public Long removeUserRegisterJoinContestByContestAndStudent(Long contest_id, Long student_id) {
        List<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findByContestAndStudent(contest_id, student_id);
        
        userRegisterJoinContestOpt.forEach(ele -> {
            userRegisterJoinContestRepository.deleteById(ele.getId());
        });
        return student_id;
    }

    @Override
    public Long updateUserRegisterJoinContestById(Long id, CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById1(id);
        UserRegisterJoinContest updatedUserRegisterJoinContest = userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById1(createUserRegisterJoinContestRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.contest.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById1(createUserRegisterJoinContestRequest.getStudent_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        
        updatedUserRegisterJoinContest.setStudent(teacher);
        updatedUserRegisterJoinContest.setContest(contest);

        return updatedUserRegisterJoinContest.getId();
    }
}
