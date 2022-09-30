package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.TeacherRegisterQualification;
@Repository
public interface TeacherRegisterQualificationRepository extends JpaRepository <TeacherRegisterQualification, UUID> {
    Page<TeacherRegisterQualification> findAll(Pageable pageable);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
