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

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmissionKey;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserGradeExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeExerciseSubmissionServiceImpl implements UserGradeExerciseSubmissionService{
    
    private final UserGradeExerciseSubmissionRepository userGradeExerciseSubmissionRepository;
    private final UserRepository userRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final ClassRepository classRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission() {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                .exercise_name(content.getExerciseSubmission().getExercise().getName())
                .time_submit(content.getExerciseSubmission().getUpdate_time())
                .image_url(content.getExerciseSubmission().getImage_url())
                .description(content.getExerciseSubmission().getExercise().getDescription())
                .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                .exercise_submission_id(content.getExerciseSubmission().getId())
                .feedback(content.getFeedback())
                .score(content.getScore())
                .time(content.getTime())
                .build();
            allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getStudent().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getTeachSemester().getTeacher().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getId() == id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(Long exercise_id, Long class_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        Optional<Class> classOpt = classRepository.findById(class_id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        List<User> student_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == class_id && content.getExerciseSubmission().getExercise().getId() == exercise_id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
                student_graded.add(content.getStudent());
            }
        });

        classes.getUserRegisterJoinSemesters().forEach(student -> {
            LocalDateTime time_now = LocalDateTime.now();
            if (student_graded.contains(student.getStudent()) == false){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(student.getStudent().getId())
                    .student_name(student.getStudent().getFirstName() + student.getStudent().getLastName())
                    .exercise_submission_id((long) 0)
                    .image_url("")
                    .feedback("")
                    .description("")
                    .score((float) 0)
                    .time(time_now)
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndClass(Long class_id, Long student_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        
        List<User> student_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == class_id && content.getExerciseSubmission().getStudent().getId() == student_id){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + content.getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
                student_graded.add(content.getStudent());
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
        UserGradeExerciseSubmission userGradeExerciseSubmission = userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        return GetUserGradeExerciseSubmissionResponse.builder()
            .student_id(userGradeExerciseSubmission.getStudent().getId())
            .student_name(userGradeExerciseSubmission.getStudent().getFirstName() + userGradeExerciseSubmission.getStudent().getLastName())
            .exercise_submission_id(userGradeExerciseSubmission.getExerciseSubmission().getId())
            .feedback(userGradeExerciseSubmission.getFeedback())
            .score(userGradeExerciseSubmission.getScore())
            .description(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getDescription())
            .image_url(userGradeExerciseSubmission.getExerciseSubmission().getImage_url())
            .exercise_name(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getName())
            .time_submit(userGradeExerciseSubmission.getExerciseSubmission().getUpdate_time())
            .deadline(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getDeadline())
            .time(userGradeExerciseSubmission.getTime())
            .build();
    }

    @Override
    public Long createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {

        Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getStudent_id());
        User student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        UserGradeExerciseSubmissionKey id = new UserGradeExerciseSubmissionKey(student.getId(),exerciseSubmission.getId());
        
        UserGradeExerciseSubmission savedUserGradeExerciseSubmission = UserGradeExerciseSubmission.builder()
                .id(id)
                .student(student)
                .exerciseSubmission(exerciseSubmission)
                .score(createUserGradeExerciseSubmissionRequest.getScore())
                .feedback(createUserGradeExerciseSubmissionRequest.getFeedback())
                .build();
        userGradeExerciseSubmissionRepository.save(savedUserGradeExerciseSubmission);

        return savedUserGradeExerciseSubmission.getStudent().getId();
    }

    @Override
    public Long removeUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
        userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        userGradeExerciseSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserGradeExerciseSubmissionById(Long student_id, Long submission_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        //UserGradeExerciseSubmissionKey index = new UserGradeExerciseSubmissionKey(student_id, submission_id);

        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content-> {
            if (content.getStudent().getId() == student_id && content.getExerciseSubmission().getId() == submission_id){
                UserGradeExerciseSubmission updatedUserGradeExerciseSubmission = content;
                Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getStudent_id());
                User student = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_student.not_found");
                });
            
                Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
                ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
                });
            
                UserGradeExerciseSubmissionKey idx = new UserGradeExerciseSubmissionKey(student.getId(), exerciseSubmission.getId());
            
                updatedUserGradeExerciseSubmission.setScore(createUserGradeExerciseSubmissionRequest.getScore());
                updatedUserGradeExerciseSubmission.setId(idx);
                updatedUserGradeExerciseSubmission.setFeedback(createUserGradeExerciseSubmissionRequest.getFeedback());
                updatedUserGradeExerciseSubmission.setExerciseSubmission(exerciseSubmission);
                updatedUserGradeExerciseSubmission.setStudent(student);

                userGradeExerciseSubmissionRepository.save(updatedUserGradeExerciseSubmission);
            }
        });

        return student_id;
    }
}
