package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.TutorialTemplate;

@Repository
public interface TutorialTemplateRepository extends JpaRepository <TutorialTemplate, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
}