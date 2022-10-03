package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, UUID>{

    @Query("SELECT e FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel ORDER BY e.create_time")
    List<Exercise> findAll();

    @Query(
		value = "SELECT e FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel ORDER BY e.create_time",
		countQuery = "SELECT e FROM Exercise e INNER JOIN e.section INNER JOIN e.exerciseLevel ORDER BY e.create_time"
	)
    Page<Exercise> findAll(Pageable pageable);

    @Query("FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel WHERE e.id = :id")
    Optional<Exercise> findById(UUID id);

    @Query("FROM Exercise e JOIN FETCH e.section JOIN FETCH e.exerciseLevel WHERE e.section = :id")
    List<Exercise> findBySectionId(UUID id);

    @Query("FROM Exercise e JOIN FETCH e.exerciseLevel JOIN FETCH e.section WHERE e.exerciseLevel = :id")
    List<Exercise> findByLevelId(UUID id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.id = :id")
    boolean existsById(UUID id);

    @Query("SELECT count(e.id) = 1 FROM Exercise e WHERE e.name = :name")
    Boolean existsByName(String name);
}