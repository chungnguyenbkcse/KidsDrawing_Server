package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.TeacherTeachSemester;
@Repository
public interface TeacherTeachSemesterRepository extends JpaRepository <TeacherTeachSemester, Long> {
    boolean existsById(Long id);
    void deleteById(Long id);
}