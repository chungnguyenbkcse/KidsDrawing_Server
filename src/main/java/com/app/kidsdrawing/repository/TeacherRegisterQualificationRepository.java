package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kidsdrawing.entity.TeacherRegisterQualification;

public interface TeacherRegisterQualificationRepository extends JpaRepository <TeacherRegisterQualification, Long> {
    Page<TeacherRegisterQualification> findAll(Pageable pageable);
    Optional<TeacherRegisterQualification> findByArtTypeId(Long id);
    Optional<TeacherRegisterQualification> findByArtAgeId(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}
