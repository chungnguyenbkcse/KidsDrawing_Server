package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.StudentLeave;
import com.app.kidsdrawing.entity.StudentLeaveKey;

@Repository
public interface StudentLeaveRepository extends JpaRepository <StudentLeave, Long>{
    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student st JOIN FETCH st.user stu WHERE (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findAll();

    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user JOIN FETCH e.section JOIN FETCH e.student st JOIN FETCH st.user stu  WHERE te.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findAllStudentLeaveByTeacher(Long teacher_id);

    @Query(
		value = "SELECT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student st JOIN FETCH st.user stu WHERE (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM StudentLeave e INNER JOIN e.section se INNER JOIN se.classes cl INNER JOIN e.student st INNER JOIN st.user stu WHERE (cl.deleted = FALSE OR cl.deleted IS NULL)"
	)
    Page<StudentLeave> findAll(Pageable pageable);

    @Query("FROM StudentLeave e WHERE e.id = ?1 ")
    Optional<StudentLeave> findById1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student st JOIN FETCH st.user stu  WHERE e.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    Optional<StudentLeave> findById2(Long id);
    
    boolean existsById(Long id);
    
    void deleteById(StudentLeaveKey id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st JOIN FETCH st.user stu WHERE st.id = ?1  ORDER BY e.id")
    List<StudentLeave> findByStudentId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st JOIN FETCH st.user stu  JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE st.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByStudentId2(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByClassesId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student st JOIN FETCH st.user stu WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findByClassesId2(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se WHERE se.id = ?1  ORDER BY e.id")
    List<StudentLeave> findBySectionId1(Long id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student  st JOIN FETCH st.user stu WHERE se.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<StudentLeave> findBySectionId2(Long id);

    @Query("SELECT DISTINCT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.student st JOIN FETCH st.user stu  WHERE cl.id = ?1 AND st.id = ?2 AND (cl.deleted = FALSE OR cl.deleted IS NULL)  ORDER BY e.id")
    List<StudentLeave> findByClassesAndStudent(Long class_id ,Long student_id);

    @Query("SELECT DISTINCT e FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH e.student st JOIN FETCH st.user stu WHERE se.id = ?1 AND st.id = ?2  ORDER BY e.update_time  desc")
    Optional<StudentLeave> findBySectionAndStudent(Long section_id ,Long student_id);
}
