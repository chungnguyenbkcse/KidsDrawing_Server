package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByTeacherId(int page, int size, Long id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findAll(paging);
        pageUserRegisterJoinContest.getContent().forEach(content -> {
            if (content.getUser().getId() == id){
                GetUserRegisterJoinContestResponse userRegisterJoinContestResponse = GetUserRegisterJoinContestResponse.builder()
                    .id(content.getId())
                    .student_id(id)
                    .contest_id(content.getContest().getId())
                    .build();
                    allUserRegisterJoinContestResponses.add(userRegisterJoinContestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserRegisterJoinContestResponses);
        response.put("currentPage", pageUserRegisterJoinContest.getNumber());
        response.put("totalItems", pageUserRegisterJoinContest.getTotalElements());
        response.put("totalPages", pageUserRegisterJoinContest.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByContestId(int page, int size, Long id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findAll(paging);
        pageUserRegisterJoinContest.getContent().forEach(content -> {
            if (content.getContest().getId() == id){
                GetUserRegisterJoinContestResponse userRegisterJoinContestResponse = GetUserRegisterJoinContestResponse.builder()
                    .id(content.getId())
                    .student_id(content.getUser().getId())
                    .contest_id(id)
                    .build();
                    allUserRegisterJoinContestResponses.add(userRegisterJoinContestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserRegisterJoinContestResponses);
        response.put("currentPage", pageUserRegisterJoinContest.getNumber());
        response.put("totalItems", pageUserRegisterJoinContest.getTotalElements());
        response.put("totalPages", pageUserRegisterJoinContest.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterJoinContestResponse getUserRegisterJoinContestById(Long id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
        UserRegisterJoinContest userRegisterJoinContest = userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        return GetUserRegisterJoinContestResponse.builder()
                .id(userRegisterJoinContest.getId())
                .student_id(userRegisterJoinContest.getUser().getId())
                .contest_id(userRegisterJoinContest.getContest().getId())
                .build();
    }

    @Override
    public Long createUserRegisterJoinContest(CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Contest contest = contestRepository.getById(createUserRegisterJoinContestRequest.getContest_id());
        User teacher = userRepository.getById(createUserRegisterJoinContestRequest.getStudent_id());
        UserRegisterJoinContest savedUserRegisterJoinContest = UserRegisterJoinContest.builder()
                .user(teacher)
                .contest(contest)
                .build();
        userRegisterJoinContestRepository.save(savedUserRegisterJoinContest);

        return savedUserRegisterJoinContest.getId();
    }

    @Override
    public Long removeUserRegisterJoinContestById(Long id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
        userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        userRegisterJoinContestRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserRegisterJoinContestById(Long id, CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
        UserRegisterJoinContest updatedUserRegisterJoinContest = userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });
        Contest contest = contestRepository.getById(createUserRegisterJoinContestRequest.getContest_id());
        User teacher = userRepository.getById(createUserRegisterJoinContestRequest.getStudent_id());
        updatedUserRegisterJoinContest.setUser(teacher);
        updatedUserRegisterJoinContest.setContest(contest);

        return updatedUserRegisterJoinContest.getId();
    }
}
