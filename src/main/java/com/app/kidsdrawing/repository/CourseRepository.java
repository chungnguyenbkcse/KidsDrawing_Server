package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository <Course, UUID>{
    Page<Course> findAll(Pageable pageable);
    Optional<Course> findByName(String name);
    boolean existsById(UUID id);
    Boolean existsByName(String name);
    void deleteById(UUID id);
}