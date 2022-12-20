package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserAttendance;

@Repository
public interface UserAttendanceRepository extends JpaRepository <UserAttendance, Long>{
    @Query("SELECT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu  JOIN FETCH e.section ORDER BY e.id")
    List<UserAttendance> findAll();

    @Query(
		value = "SELECT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu  JOIN FETCH e.section ORDER BY e.id",
		countQuery = "SELECT e FROM UserAttendance e INNER JOIN e.student st INNER JOIN st.user stu  INNER JOIN e.section "
	)
    Page<UserAttendance> findAll(Pageable pageable);

    @Query("FROM UserAttendance e WHERE e.id = ?1")
    Optional<UserAttendance> findById1(Long id);

    @Query("FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu  JOIN FETCH e.section WHERE e.id = ?1")
    Optional<UserAttendance> findById2(Long id);

    @Query("SELECT DISTINCT e  FROM UserAttendance e JOIN FETCH e.section se WHERE se.id = ?1 ORDER BY e.id")
    List<UserAttendance> findBySectionId1(Long id);

    @Query("SELECT DISTINCT e FROM UserAttendance e JOIN FETCH e.section se JOIN FETCH e.student st JOIN FETCH st.user stu  WHERE se.id = ?1 ORDER BY e.id")
    List<UserAttendance> findBySectionId2(Long id);
    
    boolean existsById(Long id);

    @Query("SELECT DISTINCT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu WHERE st.id = ?1 ORDER BY e.id")
    List<UserAttendance> findByStudentId1(Long id);

    @Query("SELECT DISTINCT e  FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu JOIN FETCH e.section WHERE st.id = ?1 ORDER BY e.id")
    List<UserAttendance> findByStudentId2(Long id);

    @Query("FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH e.section se WHERE se.id = ?1 AND st.id = ?2")
    Optional<UserAttendance> findBySectionIdAndStudentId(Long section_id, Long student_id);

    @Query("SELECT DISTINCT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa  JOIN FETCH pa.user JOIN FETCH e.section se WHERE se.id = ?1 AND pa.id = ?2 ORDER BY e.id")
    List<UserAttendance> findBySectionIdAndParentId(Long section_id, Long parent);

    @Query("SELECT DISTINCT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE cl.id = ?1 AND st.id = ?2 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<UserAttendance> findByClassIdAndStudentId(Long class_id, Long student_id);

    @Query("SELECT DISTINCT e FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH st.user stu JOIN FETCH e.section se JOIN FETCH se.classes cl WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<UserAttendance> findByClassId(Long class_id);
}
