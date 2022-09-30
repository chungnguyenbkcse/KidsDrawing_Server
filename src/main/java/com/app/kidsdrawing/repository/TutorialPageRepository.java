package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.TutorialPage;

@Repository
public interface TutorialPageRepository extends JpaRepository <TutorialPage, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<TutorialPage> findByTutorialId(UUID id);
}