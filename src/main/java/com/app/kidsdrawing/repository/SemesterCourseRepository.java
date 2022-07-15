package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SemesterCourse;

@Repository
public interface SemesterCourseRepository extends JpaRepository <SemesterCourse, Long>{
    Page<SemesterCourse> findAll(Pageable pageable);
    boolean existsById(Long id);
    void deleteById(Long id);
}
