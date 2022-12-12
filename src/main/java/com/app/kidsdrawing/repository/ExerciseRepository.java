package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, Long>{

    @Query("SELECT e FROM Exercise e JOIN FETCH e.section  WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findAll();

    @Query("SELECT DISTINCT e FROM Exercise e  JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.classHasRegisterJoinSemesterClasses chr JOIN FETCH chr.student st JOIN FETCH st.user WHERE cl.id = ?1 AND st.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findAllExerciseByClassAndStudent(Long class_id, Long student_id);

    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.exerciseSubmissions es  JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.classHasRegisterJoinSemesterClasses chr JOIN FETCH chr.student st JOIN FETCH st.user WHERE cl.id = ?1 AND st.id = ?2 AND es.exercise IS NULL AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findAllExerciseByClassAndStudent1(Long class_id, Long student_id);

    @Query("SELECT DISTINCT e FROM Exercise e  JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE cl.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findAllExerciseByClass(Long class_id);

    @Query("SELECT DISTINCT e FROM Exercise e  JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.classHasRegisterJoinSemesterClasses chr JOIN FETCH chr.student st JOIN FETCH st.user WHERE se.id = ?1 AND st.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findAllExerciseBySectionAndStudent(Long section_id, Long student_id);

    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.exerciseSubmissions es JOIN FETCH e.section se  LEFT JOIN FETCH es.student st JOIN FETCH st.user WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)   ORDER BY e.id")
    List<Exercise> findAllExerciseBySection1(Long section_id);

    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.exerciseSubmissions es JOIN FETCH e.section se  LEFT JOIN FETCH es.student st JOIN FETCH st.user JOIN FETCH st.parent pa  JOIN FETCH pa.user WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)   ORDER BY e.id")
    List<Exercise> findAllExerciseBySection2(Long section_id);

    @Query(
		value = "SELECT e FROM Exercise e JOIN FETCH e.section  WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM Exercise e INNER JOIN e.section WHERE (e.deleted = FALSE OR e.deleted IS NULL)"
	)
    Page<Exercise> findAll(Pageable pageable);

    @Query("FROM Exercise e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<Exercise> findById1(Long id);

    @Query("FROM Exercise e JOIN FETCH e.section  WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<Exercise> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.section s  WHERE s.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findBySectionId1(Long id);

    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.section s WHERE s.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Exercise> findBySectionId2(Long id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.name = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Exercise e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}