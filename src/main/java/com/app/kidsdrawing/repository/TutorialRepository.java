package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository <Tutorial, UUID>{

    @Query("SELECT e FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator ORDER BY e.id")
    List<Tutorial> findAll();

    @Query(
		value = "SELECT e FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator ORDER BY e.id",
		countQuery = "SELECT e FROM Tutorial e INNER JOIN e.section  INNER JOIN e.creator ORDER BY e.id"
	)
    Page<Tutorial> findAll(Pageable pageable);

    @Query("FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator WHERE e.id = :id")
    Optional<Tutorial> findById(UUID id);

    @Query("FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator WHERE e.section = :id")
    Optional<Tutorial> findBySectionId(UUID id);
    
    boolean existsById(UUID id);
    Boolean existsByName(String name);
}