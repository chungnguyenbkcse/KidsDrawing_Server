package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository <Tutorial, UUID>{
    boolean existsById(UUID id);
    Boolean existsByName(String name);
}