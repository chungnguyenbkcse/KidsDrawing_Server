package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TeacherTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ClassesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassesServiceImpl implements ClassesService{
    
    private final ClassRepository classRepository;
    private final TeacherTeachSemesterRepository teacherTeachSemesterRepository;
    private final UserRepository userRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                .id(content.getId())
                .creator_id(content.getUser().getId())
                .registration_id(content.getTeachSemester().getId())
                .security_code(content.getSecurity_code())
                .name(content.getName())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allClassResponses.add(classResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetClassResponse getClassById(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        return GetClassResponse.builder()
            .id(classes.getId())
            .creator_id(classes.getUser().getId())
            .registration_id(classes.getTeachSemester().getId())
            .security_code(classes.getSecurity_code())
            .name(classes.getName())
            .create_time(classes.getCreate_time())
            .update_time(classes.getUpdate_time())
            .build();
    }

    @Override
    public Long createClass(CreateClassRequest createClassRequest) {
        Optional <UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(createClassRequest.getRegistration_id());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>();
        createClassRequest.getUser_register_join_semester().forEach(user_register_join_semester_id -> {
            userRegisterJoinSemesterRepository.findById(user_register_join_semester_id).<Runnable>map(user_register_join_semester -> () -> validUserRegisterSemesters.add(user_register_join_semester))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.user_register_join_semester.invalid", user_register_join_semester_id));
                    })
                    .run();
        });

        Optional <User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        
        Class savedClass = Class.builder()
                .user(user)
                .teachSemester(teacherTeachSemester)
                .security_code(createClassRequest.getSecurity_code())
                .name(createClassRequest.getName())
                .userRegisterJoinSemesters(new HashSet<>(validUserRegisterSemesters))
                .build();
        classRepository.save(savedClass);

        return savedClass.getId();
    }

    @Override
    public Long removeClassById(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        classRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateClassById(Long id, CreateClassRequest createClassRequest) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class updatedClass = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Optional <UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(createClassRequest.getRegistration_id());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedClass.setName(createClassRequest.getName());
        updatedClass.setSecurity_code(createClassRequest.getSecurity_code());
        updatedClass.setUser(user);
        updatedClass.setTeachSemester(teacherTeachSemester);

        return updatedClass.getId();
    }
}
