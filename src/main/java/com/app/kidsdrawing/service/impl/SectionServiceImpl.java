package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
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
import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetSectionStudentResponse;
import com.app.kidsdrawing.dto.GetSectionTeacherResponse;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.TutorialPage;
import com.app.kidsdrawing.entity.TutorialTemplatePage;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.TutorialPageRepository;
import com.app.kidsdrawing.repository.TutorialTemplatePageRepository;
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
    private final TutorialTemplatePageRepository tutorialTemplatePageRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final TutorialPageRepository tutorialPageRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSection() {
        List<GetSectionStudentResponse> allSectionResponses = new ArrayList<>();
        List<Section> listSection = sectionRepository.findAll();
        listSection.forEach(content -> {
            GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                .id(content.getId())
                .time_approved(content.getTime_approved())
                .classes_id(content.getClasses().getId())
                .name(content.getName())
                .status(content.getStatus())
                .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
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
    public ResponseEntity<Map<String, Object>> getAllSectionByAdmin() {
        List<GetSectionStudentResponse> allSectionApprovedResponses = new ArrayList<>();
        List<GetSectionStudentResponse> allSectionNotApprovedResponses = new ArrayList<>();
        List<GetSectionStudentResponse> allSectionNotApproveNowResponses = new ArrayList<>();

        List<Section> listSection = sectionRepository.findAll();
        listSection.forEach(content -> {
            if (content.getStatus().equals("Not approve now")) {
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
                    .number(content.getNumber())
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionNotApproveNowResponses.add(sectionResponse);
            }

            else if (content.getStatus().equals("Not approved")) {
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
                    .number(content.getNumber())
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionNotApprovedResponses.add(sectionResponse);
            }

            else if (content.getStatus().equals("Approved")) {
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
                    .number(content.getNumber())
                    .teach_form(content.getTeaching_form())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allSectionApprovedResponses.add(sectionResponse);
            }
            
        });

        Map<String, Object> response = new HashMap<>();
        response.put("section_approved", allSectionApprovedResponses);
        response.put("section_not_approved", allSectionNotApprovedResponses);
        response.put("section_not_approve_now", allSectionNotApproveNowResponses);
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
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .time_approved(content.getTime_approved())
                    .status(content.getStatus())
                    .total_exercise_submission(allExerciseSubmissions.size())
                    .total_user_grade_exercise_submission(exerciseSubmissionGrade.size())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
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
                List<Exercise> allExercises = exerciseRepository.findAllExerciseBySection1(content.getId());
                List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionBySectionAndParent(content.getId(), parent_id);
                System.out.print("Buổi số: " + content.getNumber());
                System.out.print(allExercises.size());
        
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .status(content.getStatus())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
                    .number(content.getNumber())
                    .total_exercise_not_submit(allExercises.size() * total_child_in_class - exerciseSubmissions.size())
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
                List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionBySectionAndStudent1(content.getId(), student_id);
                System.out.print("Section number: " + content.getNumber() + "\n");
                System.out.print("Total exercise: " + allExercises.size() + "\n");
                System.out.print("Total exercise: " + exerciseSubmissions.size() + "\n");
                
                GetSectionStudentResponse sectionResponse = GetSectionStudentResponse.builder()
                    .id(content.getId())
                    .time_approved(content.getTime_approved())
                    .classes_id(content.getClasses().getId())
                    .name(content.getName())
                    .status(content.getStatus())
                    .teacher_name(content.getClasses().getTeacher().getUser().getUsername() + " - " + content.getClasses().getTeacher().getUser().getFirstName() + " " + content.getClasses().getTeacher().getUser().getLastName())
                    .number(content.getNumber())
                    .total_exercise_not_submit(allExercises.size() - exerciseSubmissions.size())
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
            .time_approved(section.getTime_approved())
            .status(section.getStatus())
            .teacher_name(section.getClasses().getTeacher().getUser().getUsername() + " - " + section.getClasses().getTeacher().getUser().getFirstName() + " " + section.getClasses().getTeacher().getUser().getLastName())
            .number(section.getNumber())
            .teach_form(section.getTeaching_form())
            .create_time(section.getCreate_time())
            .update_time(section.getUpdate_time())
            .build();
    }

    @Override 
    public Long deleteTutorialTemplatePageBySection(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById7(id);
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        List<TutorialPage> allTutorialPages = tutorialPageRepository.findBySection(id);

        List<TutorialTemplatePage> allTutorialTemplatePages = tutorialTemplatePageRepository.findByCourseIdAndNumber(section.getClasses().getSemesterClass().getCourse().getId(), section.getNumber());
        tutorialTemplatePageRepository.deleteAll(allTutorialTemplatePages);

        Optional<SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findByCourseIdAndNumber1(section.getClasses().getSemesterClass().getCourse().getId(), section.getNumber());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.sectionTemplateOpt.not_found");
        });

        allTutorialPages.forEach(ele -> {
            TutorialTemplatePage tutorialTemplatePage = TutorialTemplatePage.builder()
                .number(ele.getNumber())
                .sectionTemplate(sectionTemplate)
                .description(ele.getDescription())
                .build();
            tutorialTemplatePageRepository.save(tutorialTemplatePage);
        });
        
        return id;
    }

    @Override
    public Long createTutorialPageBySection(Long id, CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {
        Optional<Section> sectionOpt = sectionRepository.findById7(id);
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        Optional<SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findByCourseIdAndNumber1(section.getClasses().getSemesterClass().getCourse().getId(), section.getNumber());
        SectionTemplate sectionTemplate = sectionTemplateOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.sectionTemplateOpt.not_found");
        });

        TutorialTemplatePage tutorialTemplatePage = TutorialTemplatePage.builder()
            .number(createTutorialTemplatePageRequest.getNumber())
            .sectionTemplate(sectionTemplate)
            .description(createTutorialTemplatePageRequest.getDescription())
            .build();
        tutorialTemplatePageRepository.save(tutorialTemplatePage);
        return id;
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
                .status("Not approve now")
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
        updatedSection.setStatus(createSectionRequest.getStatus());
        updatedSection.setNumber(createSectionRequest.getNumber());

        return updatedSection.getId();
    }

    @Override
    public Long updateSectionByAdmin(Long id, CreateSectionRequest createSectionRequest) {
        Optional<Section> sectionOpt = sectionRepository.findById1(id);
        Section updatedSection = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        updatedSection.setStatus(createSectionRequest.getStatus());
        updatedSection.setTime_approved(LocalDateTime.now());

        return updatedSection.getId();
    }
}
