package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;

public interface ExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllExerciseSubmission();
    ResponseEntity<Map<String, Object>> getAllFinalGradeAForStudent(Long student_id);
    ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(Long student_id, Long classes_id);
    ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForParentAndClasses(Long parent_id, Long classes_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(Long id);
    GetExerciseSubmissionResponse getAllExerciseSubmissionByExerciseAndStudent(Long exercise_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionBySectionAndStudent(Long section_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionBySectionAndParent(Long section_id, Long parent_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(Long class_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndParent(Long class_id, Long parent_id);
    Long createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
    Long removeExerciseSubmissionById(Long exercise_id, Long student_id);
    Long updateExerciseSubmissionByStudent(CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
    Long updateExerciseSubmissionByTeacher(CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
}
