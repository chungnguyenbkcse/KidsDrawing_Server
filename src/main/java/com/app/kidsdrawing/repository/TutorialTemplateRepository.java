package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.TutorialTemplate;

@Repository
public interface TutorialTemplateRepository extends JpaRepository <TutorialTemplate, UUID>{
    @Query("SELECT e FROM TutorialTemplate e JOIN FETCH e.sectionTemplate  JOIN FETCH e.creator ORDER BY e.id")
    List<TutorialTemplate> findAll();

    @Query(
		value = "SELECT e FROM TutorialTemplate e JOIN FETCH e.sectionTemplate  JOIN FETCH e.creator ORDER BY e.id",
		countQuery = "SELECT e FROM TutorialTemplate e INNER JOIN e.sectionTemplate  INNER JOIN e.creator ORDER BY e.id"
	)
    Page<TutorialTemplate> findAll(Pageable pageable);

    @Query("FROM TutorialTemplate e JOIN FETCH e.sectionTemplate  JOIN FETCH e.creator WHERE e.id = :id")
    Optional<TutorialTemplate> findById(UUID id);

    @Query("FROM TutorialTemplate e JOIN FETCH e.sectionTemplate  JOIN FETCH e.creator WHERE e.sectionTemplate = :id")
    Optional<TutorialTemplate> findBySectionTemplateId(UUID id);
    
    boolean existsById(UUID id);
    Boolean existsByName(String name);
}