package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, Long>{

    @Query("SELECT e FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel ORDER BY e.id")
    List<Exercise> findAll();

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.exerciseLevel LEFT JOIN FETCH e.exerciseSubmissions JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.classHasRegisterJoinSemesterClasses chr JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH urj.student st WHERE cl.id = ?1 AND st.id = ?2 ORDER BY e.id")
    List<Exercise> findAllExerciseByClassAndStudent(Long class_id, Long student_id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.exerciseLevel LEFT JOIN FETCH e.exerciseSubmissions JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.classHasRegisterJoinSemesterClasses chr JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH urj.student st WHERE se.id = ?1 AND st.id = ?2 ORDER BY e.id")
    List<Exercise> findAllExerciseBySectionAndStudent(Long section_id, Long student_id);

    @Query(
		value = "SELECT e FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel ORDER BY e.id",
		countQuery = "SELECT e FROM Exercise e INNER JOIN e.section INNER JOIN e.exerciseLevel "
	)
    Page<Exercise> findAll(Pageable pageable);

    @Query("FROM Exercise e WHERE e.id = ?1")
    Optional<Exercise> findById1(Long id);

    @Query("FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel WHERE e.id = ?1")
    Optional<Exercise> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.section s JOIN FETCH e.exerciseLevel WHERE s.id = ?1 ORDER BY e.id")
    List<Exercise> findBySectionId1(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.section s WHERE s.id = ?1 ORDER BY e.id")
    List<Exercise> findBySectionId2(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.exerciseLevel el WHERE el.id = ?1 ORDER BY e.id")
    List<Exercise> findByLevelId1(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.exerciseLevel el JOIN FETCH e.section WHERE el.id = ?1 ORDER BY e.id")
    List<Exercise> findByLevelId2(Long id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.id = ?1")
    boolean existsById(Long id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.name = ?1")
    Boolean existsByName(String name);
}