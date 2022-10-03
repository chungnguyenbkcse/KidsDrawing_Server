package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.SectionTemplate;

@Repository
public interface SectionTemplateRepository extends JpaRepository <SectionTemplate, UUID>{
    @Query("SELECT e FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course ORDER BY e.id")
    List<SectionTemplate> findAll();

    @Query(
		value = "SELECT e FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course ORDER BY e.id",
		countQuery = "SELECT e FROM SectionTemplate e INNER JOIN e.user  INNER JOIN e.course ORDER BY e.id"
	)
    Page<SectionTemplate> findAll(Pageable pageable);

    @Query("FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course WHERE e.id = :id")
    Optional<SectionTemplate> findById(UUID id);
    
    @Query("FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course WHERE e.course = :id")
    List<SectionTemplate> findByCourseId(UUID id);

    @Query("FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course WHERE e.course = ?1 AND e.number = ?2")
    Optional<SectionTemplate> findByCourseIdAndNumber(UUID course_id, int number);

    @Query("FROM SectionTemplate e JOIN FETCH e.user  JOIN FETCH e.course WHERE e.user = :id")
    List<SectionTemplate> findByCreatorId(UUID id);

    boolean existsById(UUID id);
    void deleteById(UUID id);
}
