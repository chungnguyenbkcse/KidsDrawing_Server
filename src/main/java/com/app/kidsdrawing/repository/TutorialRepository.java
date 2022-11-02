package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository <Tutorial, Long>{

    @Query("SELECT e FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator ORDER BY e.id")
    List<Tutorial> findAll();

    @Query(
		value = "SELECT e FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator ORDER BY e.id",
		countQuery = "SELECT e FROM Tutorial e INNER JOIN e.section  INNER JOIN e.creator "
	)
    Page<Tutorial> findAll(Pageable pageable);

    @Query("FROM Tutorial e WHERE e.id = ?1")
    Optional<Tutorial> findById1(Long id);

    @Query("FROM Tutorial e JOIN FETCH e.section  JOIN FETCH e.creator WHERE e.id = ?1")
    Optional<Tutorial> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Tutorial e JOIN FETCH e.section  se WHERE se.id = ?1")
    Optional<Tutorial> findBySectionId1(Long id);

    @Query("SELECT DISTINCT e  FROM Tutorial e JOIN FETCH e.section  se JOIN FETCH e.creator WHERE se.id = ?1")
    Optional<Tutorial> findBySectionId2(Long id);

    @Query("SELECT DISTINCT e FROM Tutorial e JOIN FETCH e.creator cr WHERE cr.id = ?1")
    Optional<Tutorial> findByCreatorId1(Long id);

    @Query("SELECT DISTINCT e  FROM Tutorial e JOIN FETCH e.creator cr JOIN FETCH e.section  se WHERE cr.id = ?1 ORDER BY e.id")
    List<Tutorial> findByCreatorId2(Long id);

    @Query("SELECT DISTINCT e  FROM Tutorial e JOIN FETCH e.creator cr JOIN FETCH e.section  se WHERE cr.id = ?1 AND se.id = ?2 ORDER BY e.id")
    List<Tutorial> findByCreatorAndSection(Long creator_id, Long section_id);
    
    boolean existsById(Long id);
    Boolean existsByName(String name);
}