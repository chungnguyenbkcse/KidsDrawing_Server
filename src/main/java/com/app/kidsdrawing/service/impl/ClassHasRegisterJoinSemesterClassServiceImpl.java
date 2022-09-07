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

import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.service.ClassHasRegisterJoinSemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassHasRegisterJoinSemesterClassServiceImpl implements ClassHasRegisterJoinSemesterClassService {

    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;
    private final ClassesRepository classRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllClassHasRegisterJoinSemesterClass() {
        List<GetClassHasRegisterJoinSemesterClassResponse> allClassHasRegisterJoinSemesterClassResponses = new ArrayList<>();
        List<ClassHasRegisterJoinSemesterClass> pageClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findAll();
        pageClassHasRegisterJoinSemesterClass.forEach(class_has_register_join_semester_class -> {
            GetClassHasRegisterJoinSemesterClassResponse classHasRegisterJoinSemesterClassResponse = GetClassHasRegisterJoinSemesterClassResponse.builder()
                    .classes_id(class_has_register_join_semester_class.getClasses().getId())
                    .user_register_join_semester_id(class_has_register_join_semester_class.getUserRegisterJoinSemester().getId())
                    .review_star(class_has_register_join_semester_class.getReview_star())
                    .build();
            allClassHasRegisterJoinSemesterClassResponses.add(classHasRegisterJoinSemesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("class_has_register_join_semester_classs", allClassHasRegisterJoinSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetClassHasRegisterJoinSemesterClassResponse getClassHasRegisterJoinSemesterClassById(Long id){
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findById(id);
        ClassHasRegisterJoinSemesterClass classHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });

        return GetClassHasRegisterJoinSemesterClassResponse.builder()
                .classes_id(classHasRegisterJoinSemesterClass.getClasses().getId())
                .user_register_join_semester_id(classHasRegisterJoinSemesterClass.getUserRegisterJoinSemester().getId())
                .review_star(classHasRegisterJoinSemesterClass.getReview_star())
                .build();
    }

    @Override
    public Long createClassHasRegisterJoinSemesterClass(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest) {

        Optional <Classes> classOpt = classRepository.findById(createClassHasRegisterJoinSemesterClassRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(createClassHasRegisterJoinSemesterClassRequest.getUser_register_join_semester_id());
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        ClassHasRegisterJoinSemesterClassKey id = new ClassHasRegisterJoinSemesterClassKey(classes.getId(),userRegisterJoinSemester.getId());

        ClassHasRegisterJoinSemesterClass savedClassHasRegisterJoinSemesterClass = ClassHasRegisterJoinSemesterClass.builder()
                .id(id)
                .classes(classes)
                .userRegisterJoinSemester(userRegisterJoinSemester)
                .review_star(createClassHasRegisterJoinSemesterClassRequest.getReview_star())
                .build();
        classHasRegisterJoinSemesterClassRepository.save(savedClassHasRegisterJoinSemesterClass);

        return savedClassHasRegisterJoinSemesterClass.getClasses().getId();
    }

    @Override
    public Long removeClassHasRegisterJoinSemesterClassById(Long classes_id, Long user_register_join_semester) {

        Optional <Classes> classOpt = classRepository.findById(classes_id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(user_register_join_semester);
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        ClassHasRegisterJoinSemesterClassKey id = new ClassHasRegisterJoinSemesterClassKey(classes.getId(),userRegisterJoinSemester.getId());
        
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findById(id);
        classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });

        classHasRegisterJoinSemesterClassRepository.deleteById(id);
        return (long)1;
    }

    @Override
    public Long updateClassHasRegisterJoinSemesterClassById(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest) {

        Optional <Classes> classOpt = classRepository.findById(createClassHasRegisterJoinSemesterClassRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(createClassHasRegisterJoinSemesterClassRequest.getUser_register_join_semester_id());
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        ClassHasRegisterJoinSemesterClassKey id = new ClassHasRegisterJoinSemesterClassKey(classes.getId(),userRegisterJoinSemester.getId());
        
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findById(id);
        ClassHasRegisterJoinSemesterClass updatedClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });

        updatedClassHasRegisterJoinSemesterClass.setId(id);
        updatedClassHasRegisterJoinSemesterClass.setClasses(classes);
        updatedClassHasRegisterJoinSemesterClass.setUserRegisterJoinSemester(userRegisterJoinSemester);
        updatedClassHasRegisterJoinSemesterClass.setReview_star(createClassHasRegisterJoinSemesterClassRequest.getReview_star());
        classHasRegisterJoinSemesterClassRepository.save(updatedClassHasRegisterJoinSemesterClass);

        return updatedClassHasRegisterJoinSemesterClass.getClasses().getId();
    }
}
