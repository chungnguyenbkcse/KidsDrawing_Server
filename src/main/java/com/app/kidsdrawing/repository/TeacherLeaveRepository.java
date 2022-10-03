package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.TeacherLeave;

@Repository
public interface TeacherLeaveRepository extends JpaRepository <TeacherLeave, UUID>{
    @Query("SELECT e FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher ORDER BY e.id")
    List<TeacherLeave> findAll();

    @Query(
		value = "SELECT e FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher ORDER BY e.id",
		countQuery = "SELECT e FROM TeacherLeave e INNER JOIN e.classes INNER JOIN e.section INNER JOIN e.teacher INNER JOIN e.reviewer INNER JOIN e.substitute_teacher ORDER BY e.id"
	)
    Page<TeacherLeave> findAll(Pageable pageable);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.id = :id")
    Optional<TeacherLeave> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.teacher = :id")
    List<TeacherLeave> findByTeacherId(UUID id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.classes = :id")
    List<TeacherLeave> findByClassesId(UUID id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.section = :id")
    List<TeacherLeave> findBySectionId(UUID id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.substitute_teacher = :id")
    List<TeacherLeave> findBySubstituteTeacherId(UUID id);
}
