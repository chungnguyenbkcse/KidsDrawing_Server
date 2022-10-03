package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, UUID>{
    @Query("SELECT e FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass ORDER BY e.id")
    List<Schedule> findAll();

    @Query(
		value = "SELECT e FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass ORDER BY e.id",
		countQuery = "SELECT e FROM Schedule e INNER JOIN e.lessonTime  INNER JOIN e.semesterClass ORDER BY e.id"
	)
    Page<Schedule> findAll(Pageable pageable);

    @Query("FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass WHERE e.id = :id")
    Optional<Schedule> findById(UUID id);

    @Query("FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass WHERE e.lessonTime = :id")
    List<Schedule> findByLessonTime(UUID id);

    @Query("FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass WHERE e.semesterClass = :id")
    List<Schedule> findBySemesterClassId(UUID id);
    
    boolean existsById(UUID id);

    void deleteById(UUID id);
}