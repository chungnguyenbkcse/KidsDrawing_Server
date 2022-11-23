package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.TeacherLeave;

@Repository
public interface TeacherLeaveRepository extends JpaRepository <TeacherLeave, Long>{
    @Query("SELECT e FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher ORDER BY e.id")
    List<TeacherLeave> findAll();

    @Query(
		value = "SELECT e FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher ORDER BY e.id",
		countQuery = "SELECT e FROM TeacherLeave e INNER JOIN e.classes INNER JOIN e.section INNER JOIN e.teacher INNER JOIN e.reviewer INNER JOIN e.substitute_teacher ORDER BY e.id"
	)
    Page<TeacherLeave> findAll(Pageable pageable);

    @Query("FROM TeacherLeave e WHERE e.id = ?1")
    Optional<TeacherLeave> findById1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE e.id = ?1")
    Optional<TeacherLeave> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.teacher te WHERE te.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findByTeacherId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.teacher te JOIN FETCH e.classes JOIN FETCH e.section  JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE te.id = ?1  ORDER BY e.id")
    List<TeacherLeave> findByTeacherId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes cl WHERE cl.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findByClassesId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.classes cl JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE cl.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findByClassesId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se WHERE se.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findBySectionId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH e.classes JOIN FETCH e.teacher JOIN FETCH e.reviewer JOIN FETCH e.substitute_teacher WHERE se.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findBySectionId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.substitute_teacher st WHERE st.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findBySubstituteTeacherId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.substitute_teacher st JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.teacher JOIN FETCH e.reviewer WHERE st.id = ?1 ORDER BY e.id")
    List<TeacherLeave> findBySubstituteTeacherId2(Long id);
}
