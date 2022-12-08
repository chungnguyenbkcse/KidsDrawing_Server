package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long>{
    @Query("FROM Student e JOIN FETCH e.parent p JOIN FETCH e.user u WHERE p.id = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findByParentId(Long id);

    @Query("SELECT DISTINCT e FROM Student e JOIN FETCH e.user u JOIN FETCH e.parent p JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.semesterClass sc JOIN FETCH sc.semester  s JOIN FETCH s.holidays JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime  WHERE p.id = ?1 AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findByParentId1(Long id);

    @Query("SELECT DISTINCT x FROM Student x JOIN FETCH x.user u  JOIN FETCH x.parent p JOIN FETCH x.userRegisterJoinContests e JOIN FETCH e.student JOIN FETCH e.contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH c.artAges JOIN FETCH c.artTypes WHERE u.id = ?1 AND u.deleted != TRUE ORDER BY x.id")
    List<Student> findByParentId2(Long id);

    @Query("SELECT DISTINCT e FROM Student e JOIN FETCH e.user u JOIN FETCH e.parent p JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.semesterClass sc JOIN FETCH sc.semester  s JOIN FETCH sc.course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE p.id = ?1 AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findByParentId3(Long id);

    @Query("SELECT DISTINCT e FROM Student e JOIN FETCH e.user u JOIN FETCH e.parent p WHERE p.id = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findByParentId4(Long id);

    @Query("SELECT DISTINCT e FROM Student e JOIN FETCH e.user u JOIN FETCH e.parent p JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.semesterClass sc JOIN FETCH sc.semester  s JOIN FETCH s.holidays JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime  WHERE p.id = ?1 AND urj.status = 'Completed' AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findByParentId5(Long id);

    @Query("FROM Student e JOIN FETCH e.user u JOIN FETCH e.parent WHERE u.email = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL) ")
    Optional<Student> findByEmail2(String email);

    @Query("SELECT e FROM Student e JOIN FETCH e.user u WHERE e.id = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL) ")
    Optional<Student> findById1(Long id);

    @Query("SELECT e FROM Student e JOIN FETCH e.user u WHERE u.username = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL) ")
    Optional<Student> findByUsername(String username);

    @Query("SELECT e FROM Student e  JOIN FETCH e.user u  JOIN FETCH e.parent WHERE (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Student> findAll();

    @Query("SELECT DISTINCT e FROM Student e JOIN FETCH e.user u JOIN FETCH e.userRegisterJoinSemesters urj WHERE e.id = ?1 AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (u.deleted = FALSE OR u.deleted IS NULL) ")
    Optional<Student> findById3(Long id);
}
