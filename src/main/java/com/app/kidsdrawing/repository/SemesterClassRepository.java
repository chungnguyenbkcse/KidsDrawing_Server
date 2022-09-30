package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.SemesterClass;

@Repository
public interface SemesterClassRepository extends JpaRepository <SemesterClass, UUID>{
    Page<SemesterClass> findAll(Pageable pageable);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    void deleteById(UUID id);
    List<SemesterClass> findBySemesterId(UUID id);
    List<SemesterClass> findByCourseId(UUID id);
}
