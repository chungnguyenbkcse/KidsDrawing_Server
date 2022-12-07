package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.TutorialTemplatePage;

@Repository
public interface TutorialTemplatePageRepository extends JpaRepository <TutorialTemplatePage, Long>{
    
    @Query("SELECT e FROM TutorialTemplatePage e JOIN FETCH e.sectionTemplate ORDER BY e.id")
    List<TutorialTemplatePage> findAll();

    @Query(
		value = "SELECT e FROM TutorialTemplatePage e JOIN FETCH e.sectionTemplate ORDER BY e.id",
		countQuery = "SELECT e FROM TutorialTemplatePage e INNER JOIN e.sectionTemplate  "
	)
    Page<TutorialTemplatePage> findAll(Pageable pageable);

    @Query("FROM TutorialTemplatePage e WHERE e.id = ?1")
    Optional<TutorialTemplatePage> findById1(Long id);

    @Query("FROM TutorialTemplatePage e JOIN FETCH e.sectionTemplate WHERE e.id = ?1")
    Optional<TutorialTemplatePage> findById2(Long id);

    @Query("SELECT DISTINCT e FROM TutorialTemplatePage e JOIN FETCH e.sectionTemplate tp WHERE tp.id = ?1 ORDER BY e.id")
    List<TutorialTemplatePage> findBySectionTemplateId(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);
}