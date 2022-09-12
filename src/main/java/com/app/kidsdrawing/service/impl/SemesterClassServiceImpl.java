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

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.exception.SemesterClassAlreadyCreateException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterClassServiceImpl implements SemesterClassService {

    private final SemesterClassRepository semesterClassRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClass() {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .build();
            allSemesterClassResponses.add(semesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassHistoryOfStudent(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository.findByStudentId(id);
        LocalDateTime time_now = LocalDateTime.now();
        userRegisterJoinSemester.forEach( user_register_join_semester -> {
            if (time_now.isAfter(user_register_join_semester.getSemesterClass().getSemester().getEnd_time())){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(user_register_join_semester.getSemesterClass().getId())
                    .name(user_register_join_semester.getSemesterClass().getName())
                    .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                    .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                    .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                    .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                    .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                    .registration_time(user_register_join_semester.getSemesterClass().getRegistration_time())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassPresentOfStudent(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository.findByStudentId(id);
        LocalDateTime time_now = LocalDateTime.now();
        userRegisterJoinSemester.forEach( user_register_join_semester -> {
            if (time_now.isBefore(user_register_join_semester.getSemesterClass().getSemester().getEnd_time())){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(user_register_join_semester.getSemesterClass().getId())
                    .name(user_register_join_semester.getSemesterClass().getName())
                    .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                    .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                    .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                    .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                    .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                    .registration_time(user_register_join_semester.getSemesterClass().getRegistration_time())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse getSemesterClassById(Long id){
        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass semesterClass = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        return GetSemesterClassResponse.builder()
            .id(semesterClass.getId())
            .name(semesterClass.getName())
            .semester_id(semesterClass.getSemester().getId())
            .semester_name(semesterClass.getSemester().getName())
            .course_id(semesterClass.getCourse().getId())
            .course_name(semesterClass.getCourse().getName())
            .max_participant(semesterClass.getMax_participant())
            .registration_time(semesterClass.getRegistration_time())
            .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getSemester().getId() == id){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(Long id){
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getCourse().getId() == id){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse  createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
        }

        SemesterClass savedSemesterClass = SemesterClass.builder()
                .semester(semester)
                .course(course)
                .name(createSemesterClassRequest.getName())
                .registration_time(createSemesterClassRequest.getRegistration_time())
                .build();
        semesterClassRepository.save(savedSemesterClass);

        GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
            .id(savedSemesterClass.getId())
            .name(savedSemesterClass.getName())
            .semester_id(savedSemesterClass.getSemester().getId())
            .semester_name(savedSemesterClass.getSemester().getName())
            .course_id(savedSemesterClass.getCourse().getId())
            .course_name(savedSemesterClass.getCourse().getName())
            .max_participant(savedSemesterClass.getMax_participant())
            .registration_time(savedSemesterClass.getRegistration_time())
        .build();

        return semesterClassResponse;
    }

    @Override
    public Long removeSemesterClassById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        semesterClassRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterClassById(Long id, CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (createSemesterClassRequest.getName().equals(updatedSemesterClass.getName()) == false){
            if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
                throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
            }
        }

        updatedSemesterClass.setSemester(semester);
        updatedSemesterClass.setCourse(course);
        updatedSemesterClass.setName(createSemesterClassRequest.getName());
        updatedSemesterClass.setRegistration_time(createSemesterClassRequest.getRegistration_time());
        semesterClassRepository.save(updatedSemesterClass);

        return updatedSemesterClass.getId();
    }

    @Override
    public String updateSemesterClassMaxParticipantById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        List<UserRegisterTeachSemester> pageUserRegisterTeachSemesters = userRegisterTeachSemesterRepository.findAll();
        List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = new ArrayList<>();
        pageUserRegisterTeachSemesters.forEach(user_register_teach_semester -> {
            if (user_register_teach_semester.getSemesterClass().getId() == id){
                allUserRegisterTeachSemesters.add(user_register_teach_semester);
            }
        });
        
        updatedSemesterClass.setMax_participant((allUserRegisterTeachSemesters.size() + 1)*6 + 4);
        semesterClassRepository.save(updatedSemesterClass);
        return "Max participant successfull!";
    }
}
