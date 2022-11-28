package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.LessonTime;

@Repository
public interface LessonTimeRepository extends JpaRepository <LessonTime, Long>{

    @Query("SELECT e FROM LessonTime e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<LessonTime> findAll();

    @Query(
		value = "SELECT e FROM LessonTime e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM LessonTime e "
	)
    Page<LessonTime> findAll(Pageable pageable);

    @Query("FROM LessonTime e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<LessonTime> findById(Long id);
    
    boolean existsById(Long id);

    @Modifying
    @Query("UPDATE LessonTime e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
