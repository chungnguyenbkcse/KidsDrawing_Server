package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.SectionTemplate;

@Repository
public interface SectionTemplateRepository extends JpaRepository <SectionTemplate, UUID>{
    Page<SectionTemplate> findAll(Pageable pageable);
    List<SectionTemplate> findByCourseId(UUID id);
    Optional<SectionTemplate> findByCourseIdAndNumber(UUID course_id, int number);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
