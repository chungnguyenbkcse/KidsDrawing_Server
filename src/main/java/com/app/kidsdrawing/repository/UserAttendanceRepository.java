package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserAttendance;

@Repository
public interface UserAttendanceRepository extends JpaRepository <UserAttendance, Long>{
    boolean existsById(Long id);
    List<UserAttendance> findBySectionId(Long id);
    List<UserAttendance> findByStudentId(Long id);
    UserAttendance findBySectionIdAndStudentId(Long section_id, Long student_id);
}
