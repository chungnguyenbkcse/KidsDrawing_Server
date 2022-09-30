package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, UUID>{
    boolean existsById(UUID id);
    Boolean existsByName(String name);
}