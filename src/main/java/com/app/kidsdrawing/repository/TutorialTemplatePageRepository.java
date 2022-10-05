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
    
    @Query("SELECT e FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate ")
    List<TutorialTemplatePage> findAll();

    @Query(
		value = "SELECT e FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate ",
		countQuery = "SELECT e FROM TutorialTemplatePage e INNER JOIN e.tutorialTemplate  "
	)
    Page<TutorialTemplatePage> findAll(Pageable pageable);

    @Query("FROM TutorialTemplatePage e WHERE e.id = :id")
    Optional<TutorialTemplatePage> findById1(UUID id);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate WHERE e.id = :id")
    Optional<TutorialTemplatePage> findById2(UUID id);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate tp WHERE tp.id = :id")
    List<TutorialTemplatePage> findByTutorialTemplateId(UUID id);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.tutorialTemplate tp JOIN FETCH tp.sectionTemplate st WHERE st.id = :id")
    List<TutorialTemplatePage> findBySectionTemplateId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}