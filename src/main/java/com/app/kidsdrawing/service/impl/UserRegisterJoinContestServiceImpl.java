package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByTeacherId(int page, int size, UUID id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findAll(paging);
        pageUserRegisterJoinContest.getContent().forEach(content -> {
            if (content.getStudent().getId().compareTo(id) == 0){
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
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByContestId(int page, int size, UUID id) {
        List<GetUserRegisterJoinContestResponse> allUserRegisterJoinContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<UserRegisterJoinContest> pageUserRegisterJoinContest = userRegisterJoinContestRepository.findAll(paging);
        pageUserRegisterJoinContest.getContent().forEach(content -> {
            if (content.getContest().getId().compareTo(id) == 0){
                GetUserRegisterJoinContestResponse userRegisterJoinContestResponse = GetUserRegisterJoinContestResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
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
    public GetUserRegisterJoinContestResponse getUserRegisterJoinContestById(UUID id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
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
    public UUID createUserRegisterJoinContest(CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Optional <Contest> contestOpt = contestRepository.findById(createUserRegisterJoinContestRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.contest.not_found");
        });

        List<UserRegisterJoinContest> listUserRegisterJoinContest = userRegisterJoinContestRepository.findByContestId(createUserRegisterJoinContestRequest.getContest_id());
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
    public UUID removeUserRegisterJoinContestById(UUID id) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
        userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        userRegisterJoinContestRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateUserRegisterJoinContestById(UUID id, CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Optional<UserRegisterJoinContest> userRegisterJoinContestOpt = userRegisterJoinContestRepository.findById(id);
        UserRegisterJoinContest updatedUserRegisterJoinContest = userRegisterJoinContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinContest.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById(createUserRegisterJoinContestRequest.getContest_id());
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
