package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateSemesterCourseRequest;
import com.app.kidsdrawing.dto.GetSemesterCourseResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterCourse;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterCourseRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.service.SemesterCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterCourseServiceImpl implements SemesterCourseService {

    private final SemesterCourseRepository semesterCourseRepository;
    private final SemesterRepository semesterRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterCourse(int page, int size) {
        List<GetSemesterCourseResponse> allSemesterCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterCourse> pageSemesterCourse = semesterCourseRepository.findAll(paging);
        pageSemesterCourse.getContent().forEach(semesterCourse -> {
            GetSemesterCourseResponse semesterCourseResponse = GetSemesterCourseResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .schedule_id(semesterCourse.getSchedule().getId())
                    .build();
            allSemesterCourseResponses.add(semesterCourseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterCourseResponses);
        response.put("currentPage", pageSemesterCourse.getNumber());
        response.put("totalItems", pageSemesterCourse.getTotalElements());
        response.put("totalPages", pageSemesterCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterCourseResponse getSemesterCourseById(Long id){
        Optional<SemesterCourse> semesterCourseOpt = semesterCourseRepository.findById(id);
        SemesterCourse semesterCourse = semesterCourseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterCourse.not_found");
        });

        return GetSemesterCourseResponse.builder()
                .id(semesterCourse.getId())
                .creation_id(semesterCourse.getSemester().getId())
                .course_id(semesterCourse.getCourse().getId())
                .schedule_id(semesterCourse.getSchedule().getId())
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterCourseBySemester(int page, int size, Long id) {
        List<GetSemesterCourseResponse> allSemesterCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterCourse> pageSemesterCourse = semesterCourseRepository.findAll(paging);
        pageSemesterCourse.getContent().forEach(semesterCourse -> {
            if (semesterCourse.getSemester().getId() == id){
                GetSemesterCourseResponse semesterCourseResponse = GetSemesterCourseResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .schedule_id(semesterCourse.getSchedule().getId())
                    .build();
                allSemesterCourseResponses.add(semesterCourseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterCourseResponses);
        response.put("currentPage", pageSemesterCourse.getNumber());
        response.put("totalItems", pageSemesterCourse.getTotalElements());
        response.put("totalPages", pageSemesterCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterCourseByCourse(int page, int size, Long id){
        List<GetSemesterCourseResponse> allSemesterCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterCourse> pageSemesterCourse = semesterCourseRepository.findAll(paging);
        pageSemesterCourse.getContent().forEach(semesterCourse -> {
            if (semesterCourse.getCourse().getId() == id){
                GetSemesterCourseResponse semesterCourseResponse = GetSemesterCourseResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .schedule_id(semesterCourse.getSchedule().getId())
                    .build();
                allSemesterCourseResponses.add(semesterCourseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterCourseResponses);
        response.put("currentPage", pageSemesterCourse.getNumber());
        response.put("totalItems", pageSemesterCourse.getTotalElements());
        response.put("totalPages", pageSemesterCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long createSemesterCourse(CreateSemesterCourseRequest createSemesterCourseRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterCourseRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Schedule> scheduleOpt = scheduleRepository.findById(createSemesterCourseRequest.getSchedule_id());
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.schedule.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterCourseRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        SemesterCourse savedSemesterCourse = SemesterCourse.builder()
                .semester(semester)
                .course(course)
                .schedule(schedule)
                .build();
        semesterCourseRepository.save(savedSemesterCourse);

        return savedSemesterCourse.getId();
    }

    @Override
    public Long removeSemesterCourseById(Long id) {
        Optional<SemesterCourse> SemesterCourseOpt = semesterCourseRepository.findById(id);
        SemesterCourseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterCourse.not_found");
        });

        semesterCourseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterCourseById(Long id, CreateSemesterCourseRequest createSemesterCourseRequest) {
        Optional<SemesterCourse> SemesterCourseOpt = semesterCourseRepository.findById(id);
        SemesterCourse updatedSemesterCourse = SemesterCourseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterCourse.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterCourseRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Schedule> scheduleOpt = scheduleRepository.findById(createSemesterCourseRequest.getSchedule_id());
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.schedule.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterCourseRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        updatedSemesterCourse.setSemester(semester);
        updatedSemesterCourse.setCourse(course);
        updatedSemesterCourse.setSchedule(schedule);
        semesterCourseRepository.save(updatedSemesterCourse);

        return updatedSemesterCourse.getId();
    }
}
