package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserAttendance;

@Repository
public interface UserAttendanceRepository extends JpaRepository <UserAttendance, UUID>{
    @Query("SELECT e FROM UserAttendance e JOIN FETCH e.student  JOIN FETCH e.section ORDER BY e.id")
    List<UserAttendance> findAll();

    @Query(
		value = "SELECT e FROM UserAttendance e JOIN FETCH e.student  JOIN FETCH e.section ORDER BY e.id",
		countQuery = "SELECT e FROM UserAttendance e INNER JOIN e.student  INNER JOIN e.section ORDER BY e.id"
	)
    Page<UserAttendance> findAll(Pageable pageable);

    @Query("FROM UserAttendance e WHERE e.id = :id")
    Optional<UserAttendance> findById1(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.student  JOIN FETCH e.section WHERE e.id = :id")
    Optional<UserAttendance> findById2(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.section se WHERE se.id = :id")
    List<UserAttendance> findBySectionId1(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.section se JOIN FETCH e.student  WHERE se.id = :id")
    List<UserAttendance> findBySectionId2(UUID id);
    
    boolean existsById(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.student st WHERE st.id = :id")
    List<UserAttendance> findByStudentId1(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH e.section WHERE st.id = :id")
    List<UserAttendance> findByStudentId2(UUID id);

    @Query("FROM UserAttendance e JOIN FETCH e.student st JOIN FETCH e.section se WHERE se.id = ?1 AND st.id = ?2")
    List<UserAttendance> findBySectionIdAndStudentId(UUID section_id, UUID student_id);
}
