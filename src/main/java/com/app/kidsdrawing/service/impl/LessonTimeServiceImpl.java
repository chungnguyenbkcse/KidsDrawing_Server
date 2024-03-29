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

import com.app.kidsdrawing.dto.CreateLessonTimeRequest;
import com.app.kidsdrawing.dto.GetLessonTimeResponse;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.LessonTimeRepository;
import com.app.kidsdrawing.service.LessonTimeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class LessonTimeServiceImpl implements LessonTimeService {

    private final LessonTimeRepository lessonTimeRepository;
    private final ClassesRepository classRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllLessonTime() {
        List<GetLessonTimeResponse> allLessonTimeResponses = new ArrayList<>();
        List<LessonTime> pageLessonTime = lessonTimeRepository.findAll();
        pageLessonTime.forEach(lesson_time -> {
            GetLessonTimeResponse lessonTimeResponse = GetLessonTimeResponse.builder()
                    .id(lesson_time.getId())
                    .start_time(lesson_time.getStart_time())
                    .end_time(lesson_time.getEnd_time())
                    .build();
            allLessonTimeResponses.add(lessonTimeResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("lesson_times", allLessonTimeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetLessonTimeResponse getLessonTimeById(Long id){
        Optional<LessonTime> lessonTimeOpt = lessonTimeRepository.findById(id);
        LessonTime lessonTime = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.LessonTime.not_found");
        });

        return GetLessonTimeResponse.builder()
                .id(lessonTime.getId())
                .start_time(lessonTime.getStart_time())
                .end_time(lessonTime.getEnd_time())
                .build();
    }

    @Override
    public Long createLessonTime(CreateLessonTimeRequest createLessonTimeRequest) {

        LessonTime savedLessonTime = LessonTime.builder()
                .start_time(createLessonTimeRequest.getStart_time())
                .end_time(createLessonTimeRequest.getStart_time().plusMinutes(45))
                .build();
        lessonTimeRepository.save(savedLessonTime);

        return savedLessonTime.getId();
    }

    @Override
    public Long removeLessonTimeById(Long id) {
        Optional<LessonTime> lessonTimeOpt = lessonTimeRepository.findById(id);
        lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.LessonTime.not_found");
        });

        List<Classes> listClass = classRepository.findAllByLessonTime(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.LessonTime_Classes.not_delete");
            }
        }

        lessonTimeRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateLessonTimeById(Long id, CreateLessonTimeRequest createLessonTimeRequest) {
        Optional<LessonTime> lessonTimeOpt = lessonTimeRepository.findById(id);
        LessonTime updatedLessonTime = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.LessonTime.not_found");
        });
        updatedLessonTime.setStart_time(createLessonTimeRequest.getStart_time());
        updatedLessonTime.setEnd_time(createLessonTimeRequest.getStart_time().plusMinutes(45));
        lessonTimeRepository.save(updatedLessonTime);

        return updatedLessonTime.getId();
    }
}
