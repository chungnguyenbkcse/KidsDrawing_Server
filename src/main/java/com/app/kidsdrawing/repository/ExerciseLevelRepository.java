package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ExerciseLevel;

@Repository
public interface ExerciseLevelRepository extends JpaRepository <ExerciseLevel, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE ExerciseLevel e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}