package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.TutorialPage;

@Repository
public interface TutorialPageRepository extends JpaRepository <TutorialPage, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
}