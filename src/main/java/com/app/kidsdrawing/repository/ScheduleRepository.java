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
    @Query("SELECT e FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass ")
    List<Schedule> findAll();

    @Query(
		value = "SELECT e FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass ",
		countQuery = "SELECT e FROM Schedule e INNER JOIN e.lessonTime  INNER JOIN e.semesterClass "
	)
    Page<Schedule> findAll(Pageable pageable);

    @Query("FROM Schedule e WHERE e.id = :id")
    Optional<Schedule> findById1(UUID id);

    @Query("FROM Schedule e JOIN FETCH e.lessonTime  JOIN FETCH e.semesterClass WHERE e.id = :id")
    Optional<Schedule> findById2(UUID id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.lessonTime lt WHERE lt.id = :id")
    List<Schedule> findByLessonTime1(UUID id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.lessonTime lt JOIN FETCH e.semesterClass WHERE lt.id = :id")
    List<Schedule> findByLessonTime2(UUID id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.semesterClass sc WHERE sc.id = :id")
    List<Schedule> findBySemesterClassId1(UUID id);

    @Query("SELECT DISTINCT e FROM Schedule e JOIN FETCH e.semesterClass sc JOIN FETCH e.lessonTime WHERE sc.id = :id")
    List<Schedule> findBySemesterClassId2(UUID id);
    
    boolean existsById(UUID id);

    void deleteById(UUID id);
}