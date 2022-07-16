package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
}