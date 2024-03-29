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

import com.app.kidsdrawing.dto.CreateScheduleRequest;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.LessonTimeRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final SemesterClassRepository semesterClassRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSchedule() {
        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        List<Schedule> pageSchedule = scheduleRepository.findAll();
        pageSchedule.forEach(schedule -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule.getId())
                    .lesson_time("Thứ " + schedule.getDate_of_week().toString() + " (" + schedule.getLessonTime().getStart_time().toString() + " - " + schedule.getLessonTime().getEnd_time().toString() + ")")
                    .lesson_time_id(schedule.getLessonTime().getId())
                    .semester_classes_id(schedule.getSemesterClass().getId())
                    .date_of_week(schedule.getDate_of_week())
                    .build();
            allScheduleResponses.add(scheduleResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allScheduleResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllScheduleBySemesterClassId(Long id) {
        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        List<Schedule> pageSchedule = scheduleRepository.findBySemesterClassId2(id);
        pageSchedule.forEach(schedule -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                .id(schedule.getId())
                .lesson_time("Thứ " + schedule.getDate_of_week().toString() + " (" + schedule.getLessonTime().getStart_time().toString() + " - " + schedule.getLessonTime().getEnd_time().toString() + ")")
                .lesson_time_id(schedule.getLessonTime().getId())
                .semester_classes_id(schedule.getSemesterClass().getId())
                .date_of_week(schedule.getDate_of_week())
                .build();
            allScheduleResponses.add(scheduleResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allScheduleResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetScheduleResponse getScheduleById(Long id){
        Optional<Schedule> scheduleOpt = scheduleRepository.findById2(id);
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        return GetScheduleResponse.builder()
            .id(schedule.getId())
            .lesson_time("Thứ " + schedule.getDate_of_week().toString() + " (" + schedule.getLessonTime().getStart_time().toString() + " - " + schedule.getLessonTime().getEnd_time().toString() + ")")
            .lesson_time_id(schedule.getLessonTime().getId())
            .semester_classes_id(schedule.getSemesterClass().getId())
            .date_of_week(schedule.getDate_of_week())
            .build();
    }

    @Override
    public Long createSchedule(CreateScheduleRequest createScheduleRequest) {
        Optional<LessonTime> lessonTimeOpt =   lessonTimeRepository.findById(createScheduleRequest.getLesson_time());
        LessonTime lesson_time = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.lesson_time.not_found");
        });

        Optional<SemesterClass> semesterClassOpt =   semesterClassRepository.findById1(createScheduleRequest.getSemester_classes_id());
        SemesterClass semester_class = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        Schedule savedSchedule = Schedule.builder()
                .lessonTime(lesson_time)
                .date_of_week(createScheduleRequest.getDate_of_week())
                .semesterClass(semester_class)
                .build();
        scheduleRepository.save(savedSchedule);

        return savedSchedule.getId();
    }

    @Override
    public Long removeScheduleById(Long id) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findById1(id);
        scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        scheduleRepository.deleteById(id);
        return id;
    }

    @Override
    public Long removeScheduleBySemesterClass(Long id) {
        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById1(id);
        semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });
        scheduleRepository.deleteBySemesterClassesId(id);
        return id;
    }

    @Override
    public Long updateScheduleById(Long id, CreateScheduleRequest createScheduleRequest) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findById1(id);
        Schedule updatedSchedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        Optional<LessonTime> lessonTimeOpt =   lessonTimeRepository.findById(createScheduleRequest.getLesson_time());
        LessonTime lesson_time = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.lesson_time.not_found");
        });

        Optional<SemesterClass> semesterClassOpt =   semesterClassRepository.findById1(createScheduleRequest.getSemester_classes_id());
        SemesterClass semester_class = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        updatedSchedule.setLessonTime(lesson_time);
        updatedSchedule.setDate_of_week(createScheduleRequest.getDate_of_week());
        updatedSchedule.setSemesterClass(semester_class);
        scheduleRepository.save(updatedSchedule);

        return updatedSchedule.getId();
    }
}
