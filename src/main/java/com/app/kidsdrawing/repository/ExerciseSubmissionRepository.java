package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ExerciseSubmission;

@Repository
public interface ExerciseSubmissionRepository extends JpaRepository <ExerciseSubmission, Long>{
    

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ORDER BY e.id")
    List<ExerciseSubmission> findAll();

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH ex.exerciseLevel JOIN FETCH e.student st JOIN FETCH ex.section se JOIN FETCH se.classes cl WHERE cl.id =?1 AND st.id =?2 ORDER BY e.id")
    List<ExerciseSubmission> findAllExerciseSubmissionByClassAndStudent(Long class_id, Long student_id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student st JOIN FETCH ex.section se JOIN FETCH se.classes cl WHERE se.id =?1 AND st.id =?2 ORDER BY e.id")
    List<ExerciseSubmission> findAllExerciseSubmissionBySectionAndStudent(Long section_id, Long student_id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student st JOIN FETCH ex.section se JOIN FETCH se.classes cl WHERE cl.id =?1 ORDER BY e.id")
    List<ExerciseSubmission> findAllExerciseSubmissionByClass(Long class_id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH ex.section se WHERE se.id =?1  ORDER BY e.id")
    List<ExerciseSubmission> findAllExerciseSubmissionBySection(Long section_id);

    @Query(
		value = "SELECT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ORDER BY e.id",
		countQuery = "SELECT e FROM ExerciseSubmission e INNER JOIN e.exercise INNER JOIN e.student "
	)
    Page<ExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM ExerciseSubmission e WHERE e.id = ?1")
    Optional<ExerciseSubmission> findById1(Long id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student WHERE e.id = ?1")
    Optional<ExerciseSubmission> findById2(Long id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM ExerciseSubmission e WHERE e.id = ?1")
    boolean existsById(Long id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.student s WHERE s.id = ?1 ORDER BY e.id")
    List<ExerciseSubmission> findByStudentId1(Long id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.student s JOIN FETCH e.exercise WHERE s.id = ?1 ORDER BY e.id")
    List<ExerciseSubmission> findByStudentId2(Long id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex WHERE ex.id = ?1 ORDER BY e.id")
    List<ExerciseSubmission> findByExerciseId1(Long id);

    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student WHERE ex.id = ?1 ORDER BY e.id")
    List<ExerciseSubmission> findByExerciseId2(Long id);

    
    @Query("SELECT DISTINCT e FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student s WHERE ex.id = ?1 AND s.id = ?2 ORDER BY e.id")
    List<ExerciseSubmission> findByExerciseIdAndStudentId(Long exercise_id, Long student_id);
}