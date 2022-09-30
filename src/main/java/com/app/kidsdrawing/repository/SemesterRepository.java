package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository <Semester, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
