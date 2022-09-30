package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.ExerciseLevel;

@Repository
public interface ExerciseLevelRepository extends JpaRepository <ExerciseLevel, UUID>{
    boolean existsById(UUID id);
    Boolean existsByName(String name);
}