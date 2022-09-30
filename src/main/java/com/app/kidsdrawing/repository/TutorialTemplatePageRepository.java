package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.TutorialTemplatePage;

@Repository
public interface TutorialTemplatePageRepository extends JpaRepository <TutorialTemplatePage, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
}