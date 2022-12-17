package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.TeacherLeave;

@Repository
public interface TeacherLeaveRepository extends JpaRepository <TeacherLeave, Long>{
    @Query("SELECT e FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findAll();

    @Query(
		value = "SELECT e FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl  JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM TeacherLeave e INNER JOIN e.section se INNER JOIN se.classes cl INNER JOIN e.substitute_teacher ste INNER JOIN ste.user WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id"
	)
    Page<TeacherLeave> findAll(Pageable pageable);

    @Query("FROM TeacherLeave e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<TeacherLeave> findById1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    Optional<TeacherLeave> findById2(Long id);
    
    boolean existsById(Long id);
    
    @Modifying
    @Query("UPDATE TeacherLeave e SET e.deleted = true WHERE e.id = ?1  AND (e.deleted = FALSE OR e.deleted IS NULL)")
    void deleteById(Long id);

    @Query("FROM TeacherLeave e  JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user WHERE te.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findByTeacherId1(Long id);

    @Query("FROM TeacherLeave e  JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user  JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE te.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findByTeacherId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE cl.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findByClassesId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl  JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE cl.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findByClassesId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user  JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE cl.id = ?1 AND te.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findByClassesAndTeacher(Long class_id, Long teacher_id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findBySectionId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.section se JOIN FETCH se.classes cl  JOIN FETCH e.substitute_teacher ste JOIN FETCH ste.user WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findBySectionId2(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.substitute_teacher st JOIN FETCH st.user stu WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findBySubstituteTeacherId1(Long id);

    @Query("FROM TeacherLeave e JOIN FETCH e.substitute_teacher st JOIN FETCH st.user stu JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<TeacherLeave> findBySubstituteTeacherId2(Long id);
}
