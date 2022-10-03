package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.TutorialPage;

@Repository
public interface TutorialPageRepository extends JpaRepository <TutorialPage, UUID>{
    @Query("SELECT e FROM TutorialPage e JOIN FETCH e.tutorial ORDER BY e.id")
    List<TutorialPage> findAll();

    @Query(
		value = "SELECT e FROM TutorialPage e JOIN FETCH e.tutorial ORDER BY e.id",
		countQuery = "SELECT e FROM TutorialPage e INNER JOIN e.tutorial ORDER BY e.id"
	)
    Page<TutorialPage> findAll(Pageable pageable);

    @Query("FROM TutorialPage e JOIN FETCH e.tutorial WHERE e.id = :id")
    Optional<TutorialPage> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM TutorialPage e JOIN FETCH e.tutorial WHERE e.tutorial = :id")
    List<TutorialPage> findByTutorialId(UUID id);
}