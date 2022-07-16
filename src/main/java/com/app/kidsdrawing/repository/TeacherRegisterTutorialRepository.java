package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.TeacherRegisterTutorial;
@Repository
public interface TeacherRegisterTutorialRepository extends JpaRepository <TeacherRegisterTutorial, Long> {
    boolean existsById(Long id);
    void deleteById(Long id);
}
