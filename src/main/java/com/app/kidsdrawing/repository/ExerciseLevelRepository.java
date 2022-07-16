package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ExerciseLevel;

@Repository
public interface ExerciseLevelRepository extends JpaRepository <ExerciseLevel, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
}