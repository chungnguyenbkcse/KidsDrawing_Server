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

import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemester() {
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<Semester> pageSemester = semesterRepository.findAll();
        pageSemester.forEach(semester -> {
            GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(semester.getId())
                    .name(semester.getName())
                    .description(semester.getDescription())
                    .start_time(semester.getStart_time())
                    .number(semester.getNumber())
                    .year(semester.getYear())
                    .create_time(semester.getCreate_time())
                    .update_time(semester.getUpdate_time())
                    .creator_id(semester.getUser().getId())
                    .build();
            allSemesterResponses.add(semesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semesters", allSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterResponse getSemesterById(Long id){
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        return GetSemesterResponse.builder()
                .id(semester.getId())
                .name(semester.getName())
                .description(semester.getDescription())
                .start_time(semester.getStart_time())
                .number(semester.getNumber())
                .year(semester.getYear())
                .create_time(semester.getCreate_time())
                .update_time(semester.getUpdate_time())
                .creator_id(semester.getUser().getId())
                .build();
    }

    @Override
    public Long createSemester(CreateSemesterRequest createSemesterRequest) {
        Optional<User> userOpt = userRepository.findById(createSemesterRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Semester savedSemester = Semester.builder()
                .name(createSemesterRequest.getName())
                .description(createSemesterRequest.getDescription())
                .number(createSemesterRequest.getNumber())
                .year(createSemesterRequest.getNumber())
                .start_time(createSemesterRequest.getStart_time())
                .user(user)
                .build();
        semesterRepository.save(savedSemester);

        return savedSemester.getId();
    }

    @Override
    public Long removeSemesterById(Long id) {
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        semesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester updatedSemester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createSemesterRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedSemester.setName(createSemesterRequest.getName());
        updatedSemester.setUser(user);
        semesterRepository.save(updatedSemester);

        return updatedSemester.getId();
    }
}
