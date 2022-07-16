package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository <Tutorial, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
}