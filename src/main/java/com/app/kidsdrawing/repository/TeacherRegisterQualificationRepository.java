package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kidsdrawing.entity.TeacherRegisterQualification;

public interface TeacherRegisterQualificationRepository extends JpaRepository <TeacherRegisterQualification, Long> {
    Page<TeacherRegisterQualification> findAll(Pageable pageable);
    boolean existsById(Long id);
    void deleteById(Long id);
}
