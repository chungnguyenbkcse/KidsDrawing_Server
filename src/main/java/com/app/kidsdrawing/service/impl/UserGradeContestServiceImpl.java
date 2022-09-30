package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestResponse;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.UserGradeContestRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserGradeContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeContestServiceImpl implements UserGradeContestService{
    
    private final UserGradeContestRepository userGradeContestRepository;
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestByTeacherId(UUID id) {
        List<GetUserGradeContestResponse> allUserGradeContestResponses = new ArrayList<>();
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findAll();
        pageUserGradeContest.forEach(content -> {
            if (content.getUser().getId().compareTo(id) == 0){
                GetUserGradeContestResponse userGradeContestResponse = GetUserGradeContestResponse.builder()
                    .id(content.getId())
                    .teacher_id(id)
                    .contest_id(content.getContest().getId())
                    .teacher_name(content.getUser().getFirstName() + " " + content.getUser().getLastName())
                    .contest_name(content.getContest().getName())
                    .build();
                    allUserGradeContestResponses.add(userGradeContestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserGradeContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestByContestId(UUID id) {
        List<GetUserGradeContestResponse> allUserGradeContestResponses = new ArrayList<>();
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findAll();
        pageUserGradeContest.forEach(content -> {
            if (content.getContest().getId().compareTo(id) == 0){
                GetUserGradeContestResponse userGradeContestResponse = GetUserGradeContestResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getUser().getId())
                    .contest_id(id)
                    .teacher_name(content.getUser().getFirstName() + " " + content.getUser().getLastName())
                    .contest_name(content.getContest().getName())
                    .build();
                    allUserGradeContestResponses.add(userGradeContestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserGradeContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserGradeContestResponse getUserGradeContestById(UUID id) {
        Optional<UserGradeContest> userGradeContestOpt = userGradeContestRepository.findById(id);
        UserGradeContest userGradeContest = userGradeContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContest.not_found");
        });

        return GetUserGradeContestResponse.builder()
                .id(userGradeContest.getId())
                .teacher_id(userGradeContest.getUser().getId())
                .contest_id(userGradeContest.getContest().getId())
                .teacher_name(userGradeContest.getUser().getFirstName() + " " + userGradeContest.getUser().getLastName())
                .contest_name(userGradeContest.getContest().getName())
                .build();
    }

    @Override
    public UUID createUserGradeContest(CreateUserGradeContestRequest createUserGradeContestRequest) {
        Contest contest = contestRepository.getById(createUserGradeContestRequest.getContest_id());
        User teacher = userRepository.getById(createUserGradeContestRequest.getTeacher_id());
        UserGradeContest savedUserGradeContest = UserGradeContest.builder()
                .user(teacher)
                .contest(contest)
                .build();
        userGradeContestRepository.save(savedUserGradeContest);

        return savedUserGradeContest.getId();
    }

    @Override
    public UUID removeUserGradeContestById(UUID id) {
        Optional<UserGradeContest> userGradeContestOpt = userGradeContestRepository.findById(id);
        userGradeContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContest.not_found");
        });

        userGradeContestRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateUserGradeContestById(UUID id, CreateUserGradeContestRequest createUserGradeContestRequest) {
        Optional<UserGradeContest> userGradeContestOpt = userGradeContestRepository.findById(id);
        UserGradeContest updatedUserGradeContest = userGradeContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContest.not_found");
        });
        Contest contest = contestRepository.getById(createUserGradeContestRequest.getContest_id());
        User teacher = userRepository.getById(createUserGradeContestRequest.getTeacher_id());
        updatedUserGradeContest.setUser(teacher);
        updatedUserGradeContest.setContest(contest);

        return updatedUserGradeContest.getId();
    }
}
