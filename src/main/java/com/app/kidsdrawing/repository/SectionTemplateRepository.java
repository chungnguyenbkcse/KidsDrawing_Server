package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SectionTemplate;

@Repository
public interface SectionTemplateRepository extends JpaRepository <SectionTemplate, Long>{
    Page<SectionTemplate> findAll(Pageable pageable);
    List<SectionTemplate> findByCourseId(Long id);
    Optional<SectionTemplate> findByCourseIdAndNumber(Long course_id, int number);
    boolean existsById(Long id);
    void deleteById(Long id);
}
