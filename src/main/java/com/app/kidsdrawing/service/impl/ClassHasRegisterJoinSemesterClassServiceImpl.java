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
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassStudentRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassTeacherRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;
import com.app.kidsdrawing.dto.GetReviewStarForClassResponse;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ClassHasRegisterJoinSemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassHasRegisterJoinSemesterClassServiceImpl implements ClassHasRegisterJoinSemesterClassService {

    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;
    private final ClassesRepository classRepository;
    private final UserRepository userRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private static int total = 0;
    
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
    public GetReviewStarForClassResponse getReviewStarForClass(Long class_id) {
        List<ClassHasRegisterJoinSemesterClass> pageClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId1(class_id);
        total = 0;
        pageClassHasRegisterJoinSemesterClass.forEach(ele -> {
            if (ele.getReview_star() != -1) {
                total = total + ele.getReview_star();
            }
        });
        return GetReviewStarForClassResponse.builder()
        .review_star((float) (total/pageClassHasRegisterJoinSemesterClass.size()))
        .build();

    }

    @Override
    public GetClassHasRegisterJoinSemesterClassResponse getClassHasRegisterJoinSemesterClassByClassesAndStudent(Long class_id, Long student_id) {
        
        
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findByClassesIdAndStudentId(class_id, student_id);
        ClassHasRegisterJoinSemesterClass updatedClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });
        

        return GetClassHasRegisterJoinSemesterClassResponse.builder()
        .classes_id(updatedClassHasRegisterJoinSemesterClass.getClasses().getId())
        .user_register_join_semester_id(updatedClassHasRegisterJoinSemesterClass.getUserRegisterJoinSemester().getId())
        .review_star(updatedClassHasRegisterJoinSemesterClass.getReview_star())
        .student_feedback(updatedClassHasRegisterJoinSemesterClass.getStudent_feedback())
        .teacher_feedback(updatedClassHasRegisterJoinSemesterClass.getTeacher_feedback())
        .build();
    }


    @Override
    public Long updateClassHasRegisterJoinSemesterClassForStudent(CreateClassHasRegisterJoinSemesterClassStudentRequest createClassHasRegisterJoinSemesterClassStudentRequest) {
        List<User> allChildForParent = userRepository
                .findByParentId(createClassHasRegisterJoinSemesterClassStudentRequest.getParent_id());
        allChildForParent.forEach(ele -> {
            Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findByClassesIdAndStudentId(createClassHasRegisterJoinSemesterClassStudentRequest.getClasses_id(),ele.getId());
            if (classHasRegisterJoinSemesterClassOpt.isPresent()) {
                classHasRegisterJoinSemesterClassOpt.get().setStudent_feedback(createClassHasRegisterJoinSemesterClassStudentRequest.getStudent_feedback());
                classHasRegisterJoinSemesterClassOpt.get().setReview_star(createClassHasRegisterJoinSemesterClassStudentRequest.getReview_star());
                classHasRegisterJoinSemesterClassRepository.save(classHasRegisterJoinSemesterClassOpt.get());
            }
        });

        return createClassHasRegisterJoinSemesterClassStudentRequest.getParent_id();
    }

    @Override
    public Long updateClassHasRegisterJoinSemesterClassForTeacher(CreateClassHasRegisterJoinSemesterClassTeacherRequest createClassHasRegisterJoinSemesterClassTeacherRequest) {
        
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findByClassesIdAndStudentId(createClassHasRegisterJoinSemesterClassTeacherRequest.getClasses_id(),createClassHasRegisterJoinSemesterClassTeacherRequest.getStudent_id());
        ClassHasRegisterJoinSemesterClass updatedClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });

        updatedClassHasRegisterJoinSemesterClass.setTeacher_feedback(createClassHasRegisterJoinSemesterClassTeacherRequest.getTeacher_feedback());
        classHasRegisterJoinSemesterClassRepository.save(updatedClassHasRegisterJoinSemesterClass);

        return updatedClassHasRegisterJoinSemesterClass.getClasses().getId();
    }


    @Override
    public Long createClassHasRegisterJoinSemesterClass(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest) {

        Optional <Classes> classOpt = classRepository.findById1(createClassHasRegisterJoinSemesterClassRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(createClassHasRegisterJoinSemesterClassRequest.getUser_register_join_semester_id());
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

        Optional <Classes> classOpt = classRepository.findById1(classes_id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(user_register_join_semester);
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        ClassHasRegisterJoinSemesterClassKey id = new ClassHasRegisterJoinSemesterClassKey(classes.getId(),userRegisterJoinSemester.getId());
        
        Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findByClassIdAndUserRegisterJoinSemester(classes.getId(),userRegisterJoinSemester.getId());
        classHasRegisterJoinSemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ClassHasRegisterJoinSemesterClass.not_found");
        });

        classHasRegisterJoinSemesterClassRepository.deleteById(id);
        return  (long)1;
    }

    @Override
    public Long updateClassHasRegisterJoinSemesterClassById(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest) {

        Optional <Classes> classOpt = classRepository.findById1(createClassHasRegisterJoinSemesterClassRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.classes.not_found");
        });

        Optional <UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(createClassHasRegisterJoinSemesterClassRequest.getUser_register_join_semester_id());
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
