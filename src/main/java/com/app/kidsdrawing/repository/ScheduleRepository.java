package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long>{
    @Query("SELECT e FROM Schedule e JOIN FETCH e.lessonTime lt JOIN FETCH e.semesterClass sc WHERE (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<Schedule> findAll();

    @Query(
		value = "SELECT e FROM Schedule e JOIN FETCH e.lessonTime lt  JOIN FETCH e.semesterClass sc WHERE (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM Schedule e INNER JOIN e.lessonTime lt  INNER JOIN e.semesterClass sc WHERE (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL)"
	)
    Page<Schedule> findAll(Pageable pageable);

    @Query("FROM Schedule e WHERE e.id = ?1")
    Optional<Schedule> findById1(Long id);

    @Query("FROM Schedule e JOIN FETCH e.lessonTime lt JOIN FETCH e.semesterClass sc WHERE e.id = ?1 AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL)")
    Optional<Schedule> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.lessonTime lt WHERE lt.id = ?1 AND (lt.deleted = FALSE OR lt.deleted IS NULL) ORDER BY e.id")
    List<Schedule> findByLessonTime1(Long id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.lessonTime lt JOIN FETCH e.semesterClass sc WHERE lt.id = ?1 AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<Schedule> findByLessonTime2(Long id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<Schedule> findBySemesterClassId1(Long id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.semesterClass sc JOIN FETCH e.lessonTime lt  WHERE sc.id = ?1 AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<Schedule> findBySemesterClassId2(Long id);
    
    boolean existsById(Long id);

    void deleteById(Long id);

    @Modifying
    @Query(value = "DELETE FROM schedule e WHERE e.semester_classes_id =?1", nativeQuery=true)
    void deleteBySemesterClassesId(Long id);
}