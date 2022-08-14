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

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterClassServiceImpl implements SemesterClassService {

    private final SemesterClassRepository semesterCourseRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClass(int page, int size) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterClass> pageSemesterClass = semesterCourseRepository.findAll(paging);
        pageSemesterClass.getContent().forEach(semesterCourse -> {
            GetSemesterClassResponse semesterCourseResponse = GetSemesterClassResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .build();
            allSemesterClassResponses.add(semesterCourseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterClassResponses);
        response.put("currentPage", pageSemesterClass.getNumber());
        response.put("totalItems", pageSemesterClass.getTotalElements());
        response.put("totalPages", pageSemesterClass.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse getSemesterClassById(Long id){
        Optional<SemesterClass> semesterCourseOpt = semesterCourseRepository.findById(id);
        SemesterClass semesterCourse = semesterCourseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        return GetSemesterClassResponse.builder()
                .id(semesterCourse.getId())
                .creation_id(semesterCourse.getSemester().getId())
                .course_id(semesterCourse.getCourse().getId())
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(int page, int size, Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterClass> pageSemesterClass = semesterCourseRepository.findAll(paging);
        pageSemesterClass.getContent().forEach(semesterCourse -> {
            if (semesterCourse.getSemester().getId() == id){
                GetSemesterClassResponse semesterCourseResponse = GetSemesterClassResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .build();
                allSemesterClassResponses.add(semesterCourseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterClassResponses);
        response.put("currentPage", pageSemesterClass.getNumber());
        response.put("totalItems", pageSemesterClass.getTotalElements());
        response.put("totalPages", pageSemesterClass.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(int page, int size, Long id){
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<SemesterClass> pageSemesterClass = semesterCourseRepository.findAll(paging);
        pageSemesterClass.getContent().forEach(semesterCourse -> {
            if (semesterCourse.getCourse().getId() == id){
                GetSemesterClassResponse semesterCourseResponse = GetSemesterClassResponse.builder()
                    .id(semesterCourse.getId())
                    .creation_id(semesterCourse.getSemester().getId())
                    .course_id(semesterCourse.getCourse().getId())
                    .build();
                allSemesterClassResponses.add(semesterCourseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_courses", allSemesterClassResponses);
        response.put("currentPage", pageSemesterClass.getNumber());
        response.put("totalItems", pageSemesterClass.getTotalElements());
        response.put("totalPages", pageSemesterClass.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        SemesterClass savedSemesterClass = SemesterClass.builder()
                .semester(semester)
                .course(course)
                .build();
        semesterCourseRepository.save(savedSemesterClass);

        return savedSemesterClass.getId();
    }

    @Override
    public Long removeSemesterClassById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterCourseRepository.findById(id);
        SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        semesterCourseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterClassById(Long id, CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<SemesterClass> SemesterClassOpt = semesterCourseRepository.findById(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        updatedSemesterClass.setSemester(semester);
        updatedSemesterClass.setCourse(course);
        semesterCourseRepository.save(updatedSemesterClass);

        return updatedSemesterClass.getId();
    }
}
