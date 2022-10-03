package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.StudentLeave;

@Repository
public interface StudentLeaveRepository extends JpaRepository <StudentLeave, UUID>{
    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer ORDER BY e.id")
    List<StudentLeave> findAll();

    @Query(
		value = "SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer ORDER BY e.id",
		countQuery = "SELECT e FROM StudentLeave e INNER JOIN e.classes INNER JOIN e.section INNER JOIN e.student INNER JOIN e.reviewer ORDER BY e.id"
	)
    Page<StudentLeave> findAll(Pageable pageable);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.id = :id")
    Optional<StudentLeave> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.student = :id")
    List<StudentLeave> findByStudentId(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.classes = :id")
    List<StudentLeave> findByClassesId(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.section = :id")
    List<StudentLeave> findBySectionId(UUID id);
}
