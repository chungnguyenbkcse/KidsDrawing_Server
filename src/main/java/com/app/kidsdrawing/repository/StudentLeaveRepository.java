package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.StudentLeave;

@Repository
public interface StudentLeaveRepository extends JpaRepository <StudentLeave, UUID>{
    Page<StudentLeave> findAll(Pageable pageable);
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<StudentLeave> findByStudentId(UUID id);
}
