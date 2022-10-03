package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.TutorialTemplatePage;

@Repository
public interface TutorialTemplatePageRepository extends JpaRepository <TutorialTemplatePage, UUID>{
    
    @Query("SELECT e FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate ORDER BY e.id")
    List<TutorialTemplatePage> findAll();

    @Query(
		value = "SELECT e FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate ORDER BY e.id",
		countQuery = "SELECT e FROM TutorialTemplatePage e INNER JOIN e.tutorialTemplate  ORDER BY e.id"
	)
    Page<TutorialTemplatePage> findAll(Pageable pageable);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate WHERE e.id = :id")
    Optional<TutorialTemplatePage> findById(UUID id);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate WHERE e.tutorialTemplate = :id")
    Optional<TutorialTemplatePage> findByTutorialTemplateId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}