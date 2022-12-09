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

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestResponse;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.entity.Teacher;
import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.UserGradeContestKey;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.UserGradeContestRepository;
import com.app.kidsdrawing.repository.TeacherRepository;
import com.app.kidsdrawing.service.UserGradeContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeContestServiceImpl implements UserGradeContestService{
    
    private final UserGradeContestRepository userGradeContestRepository;
    private final ContestRepository contestRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestByTeacherId(Long id) {
        List<GetUserGradeContestResponse> allUserGradeContestResponses = new ArrayList<>();
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findByTeacherId2(id);
        pageUserGradeContest.forEach(content -> {
            GetUserGradeContestResponse userGradeContestResponse = GetUserGradeContestResponse.builder()
                .number(content.getNumber())
                .teacher_id(id)
                .contest_id(content.getContest().getId())
                .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                .contest_name(content.getContest().getName())
                .build();
            allUserGradeContestResponses.add(userGradeContestResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserGradeContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeContestByContestId(Long id) {
        List<GetUserGradeContestResponse> allUserGradeContestResponses = new ArrayList<>();
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findByContestId2(id);
        pageUserGradeContest.forEach(content -> {
            GetUserGradeContestResponse userGradeContestResponse = GetUserGradeContestResponse.builder()
                .number(content.getNumber())
                .teacher_id(content.getTeacher().getUser().getId())
                .contest_id(id)
                .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                .contest_name(content.getContest().getName())
                .build();
                allUserGradeContestResponses.add(userGradeContestResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_grade_contest", allUserGradeContestResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long createUserGradeContest(CreateUserGradeContestRequest createUserGradeContestRequest) {
        Optional<Contest> contestOpt = contestRepository.findById1(createUserGradeContestRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        Optional<Teacher> teacherOpt = teacherRepository.findById1(createUserGradeContestRequest.getTeacher_id());
        Teacher teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher.not_found");
        });

        UserGradeContestKey id = new UserGradeContestKey(teacher.getId(), contest.getId());

        UserGradeContest savedUserGradeContest = UserGradeContest.builder()
                .id(id)
                .number(createUserGradeContestRequest.getNumber())
                .teacher(teacher)
                .contest(contest)
                .build();
        userGradeContestRepository.save(savedUserGradeContest);

        return teacher.getId();
    }

    @Override
    public Long removeUserGradeContestById(Long contest_id, Long teacher_id) {
        UserGradeContestKey id = new UserGradeContestKey(teacher_id, contest_id);
        Optional<UserGradeContest> userGradeContestOpt = userGradeContestRepository.findById1(id);
        userGradeContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContest.not_found");
        });

        userGradeContestRepository.deleteById(id);
        return teacher_id;
    }

    @Override 
    public Long removeUserGradeContestByContest(Long id) {
        
        List<UserGradeContest> userGradeContests = userGradeContestRepository.findByContestId1(id);

        userGradeContests.forEach(ele -> {
            userGradeContestRepository.deleteById(ele.getId());
        });
        return id;
    }

    @Override
    public Long updateUserGradeContestById(CreateUserGradeContestRequest createUserGradeContestRequest) {
        
        UserGradeContestKey id = new UserGradeContestKey(createUserGradeContestRequest.getTeacher_id(), createUserGradeContestRequest.getContest_id());
        
        Optional<UserGradeContest> userGradeContestOpt = userGradeContestRepository.findById1(id);
        UserGradeContest updatedUserGradeContest = userGradeContestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeContest.not_found");
        });
        Contest contest = contestRepository.getById(createUserGradeContestRequest.getContest_id());
        Teacher teacher = teacherRepository.getById(createUserGradeContestRequest.getTeacher_id());
        updatedUserGradeContest.setTeacher(teacher);
        updatedUserGradeContest.setContest(contest);

        return createUserGradeContestRequest.getTeacher_id();
    }
}
