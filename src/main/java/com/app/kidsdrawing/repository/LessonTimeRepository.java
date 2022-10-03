package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.LessonTime;

@Repository
public interface LessonTimeRepository extends JpaRepository <LessonTime, UUID>{

    @Query("SELECT e FROM LessonTime e ORDER BY e.start_time")
    List<LessonTime> findAll();

    @Query(
		value = "SELECT e FROM LessonTime e ORDER BY e.start_time",
		countQuery = "SELECT e FROM LessonTime e ORDER BY e.start_time"
	)
    Page<LessonTime> findAll(Pageable pageable);

    @Query("FROM LessonTime e WHERE e.id = :id")
    Optional<LessonTime> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
