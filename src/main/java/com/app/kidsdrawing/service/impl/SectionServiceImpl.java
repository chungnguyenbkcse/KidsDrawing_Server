package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionStudentResponse;
import com.app.kidsdrawing.dto.GetSectionTeacherResponse;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.SectionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SectionServiceImpl implements SectionService{
    
    private final SectionRepository sectionRepository;
    private final ClassesRepository classRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final ExerciseRepository exerciseRepository;
    private static Integer total = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSection() {
        List<GetSectionStudentResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findAll();
        listSection.forEach(content -> {
            GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                .id(content.getId())
                .classes_id(content.getClasses().getId())
                .name(content.getName())
                .teacher_name(content.getClasses().getUserRegisterTeachSemester().getTeacher().getUsername() + " - " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                .number(content.getNumber())
                .teach_form(content.getTeaching_form())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allSectionResponses.add(sectionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionTeacherByClassId(Long id) {
        List<GetSectionTeacherResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findByClassesId(id);
        listSection.forEach(content -> {
            List<ExerciseSubmission> allExerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionBySection(content.getId());
            List<ExerciseSubmission> exerciseSubmissionGrade = allExerciseSubmissions.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());
            if (content.getClasses().getId().compareTo(id) == 0){
                GetSectionTeacherResponse sectionResponse = GetSectionTeacherResponse.builder()
                    .id(content.getId())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .total_exercise_submission(allExerciseSubmissions.size())
                    .total_user_grade_exercise_submission(exerciseSubmissionGrade.size())
                    .teacher_name(content.getClasses().getUserRegisterTeachSemester().getTeacher().getUsername() + " - " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                    .number(content.getNumber())
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionResponses.add(sectionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionParentByClassId(Long class_id, Long parent_id, int total_child_in_class) {
        List<GetSectionStudentResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findByClassesId(class_id);
        listSection.forEach(content -> {
            if (content.getClasses().getId().compareTo(class_id) == 0){
                List<Exercise> allExercises = exerciseRepository.findAllExerciseBySection2(content.getId());
                System.out.print("Buổi số: " + content.getNumber());
                System.out.print(allExercises.size());
                total = 0;
                allExercises.forEach(ele -> {
                    for (int i = 0; i < new ArrayList<>(ele.getExerciseSubmissions()).size(); i++) {
                        if (new ArrayList<>(ele.getExerciseSubmissions()).get(i).getStudent().getParent().getId() == parent_id) {
                            total += 1;
                            break;
                        }
                    }
                });
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .teacher_name(content.getClasses().getUserRegisterTeachSemester().getTeacher().getUsername() + " - " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                    .number(content.getNumber())
                    .total_exercise_not_submit(allExercises.size() * total_child_in_class - total)
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionResponses.add(sectionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSectionStudentByClassId(Long class_id, Long student_id) {
        List<GetSectionStudentResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findByClassesId(class_id);
        listSection.forEach(content -> {
            if (content.getClasses().getId().compareTo(class_id) == 0){
                List<Exercise> allExercises = exerciseRepository.findAllExerciseBySection1(content.getId());
                System.out.print("Buổi số: " + content.getNumber());
                System.out.print(allExercises.size());
                total = 0;
                allExercises.forEach(ele -> {
                    for (int i = 0; i < new ArrayList<>(ele.getExerciseSubmissions()).size(); i++) {
                        if (new ArrayList<>(ele.getExerciseSubmissions()).get(i).getStudent().getId() == student_id) {
                            total += 1;
                            break;
                        }
                    }
                });
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .teacher_name(content.getClasses().getUserRegisterTeachSemester().getTeacher().getUsername() + " - " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + content.getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                    .number(content.getNumber())
                    .total_exercise_not_submit(allExercises.size() - total)
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionResponses.add(sectionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Section", allSectionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSectionStudentResponse getSectionById(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById2(id);
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        return GetSectionStudentResponse.builder()
            .id(section.getId())
            .classes_id(section.getClasses().getId())
            .name(section.getName())
            .teacher_name(section.getClasses().getUserRegisterTeachSemester().getTeacher().getUsername() + " - " + section.getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + section.getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
            .number(section.getNumber())
            .teach_form(section.getTeaching_form())
            .create_time(section.getCreate_time())
            .update_time(section.getUpdate_time())
            .build();
    }

    @Override
    public Long createSection(CreateSectionRequest createSectionRequest) {

        Optional <Classes> classOpt = classRepository.findById1(createSectionRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        Section savedSection = Section.builder()
                .classes(classes)
                .name(createSectionRequest.getName())
                .number(createSectionRequest.getNumber())
                .build();
        sectionRepository.save(savedSection);

        return savedSection.getId();
    }

    @Override
    public Long removeSectionById(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById1(id);
        sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        sectionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSectionById(Long id, CreateSectionRequest createSectionRequest) {
        Optional<Section> sectionOpt = sectionRepository.findById1(id);
        Section updatedSection = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        Optional <Classes> classOpt = classRepository.findById1(createSectionRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedSection.setName(createSectionRequest.getName());
        updatedSection.setClasses(classes);
        updatedSection.setNumber(createSectionRequest.getNumber());

        return updatedSection.getId();
    }
}
