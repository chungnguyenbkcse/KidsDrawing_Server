package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserAttendance;

@Repository
public interface UserAttendanceRepository extends JpaRepository <UserAttendance, UUID>{
    boolean existsById(UUID id);
    List<UserAttendance> findBySectionId(UUID id);
    List<UserAttendance> findByStudentId(UUID id);
    UserAttendance findBySectionIdAndStudentId(UUID section_id, UUID student_id);
}
