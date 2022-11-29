package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.StudentLeave;

@Repository
public interface StudentLeaveRepository extends JpaRepository <StudentLeave, Long>{
    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findAll();

    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher te JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE te.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findAllStudentLeaveByTeacher(Long teacher_id);

    @Query(
		value = "SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM StudentLeave e INNER JOIN e.classes INNER JOIN e.section INNER JOIN e.student INNER JOIN e.reviewer WHERE (e.deleted = FALSE OR e.deleted IS NULL)"
	)
    Page<StudentLeave> findAll(Pageable pageable);

    @Query("FROM StudentLeave e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<StudentLeave> findById1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<StudentLeave> findById2(Long id);
    
    boolean existsById(Long id);
    
    @Modifying
    @Query("UPDATE StudentLeave e SET e.deleted = true WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    void deleteById(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByStudentId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st JOIN FETCH e.reviewer JOIN FETCH e.classes JOIN FETCH e.section WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByStudentId2(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes cl WHERE cl.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByClassesId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE cl.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByClassesId2(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findBySectionId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH e.classes JOIN FETCH e.student JOIN FETCH e.reviewer WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findBySectionId2(Long id);

    @Query("SELECT DISTINCT e FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH e.section JOIN FETCH e.student st JOIN FETCH e.reviewer WHERE cl.id = ?1 AND st.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByClassesAndStudent(Long class_id ,Long student_id);

    @Query("SELECT DISTINCT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH e.student st WHERE se.id = ?1 AND st.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.update_time  desc")
    List<StudentLeave> findBySectionAndStudent(Long section_id ,Long student_id);
}
