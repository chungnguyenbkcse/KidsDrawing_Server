package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SemesterClass;

@Repository
public interface SemesterClassRepository extends JpaRepository <SemesterClass, Long>{
    
    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ORDER BY e.id")
    List<SemesterClass> findAll();

    @Query("SELECT  DISTINCT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY e.id")
    List<SemesterClass> findAll1();

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE st.id = ?1 AND c.id =?2 ORDER BY e.id")
    List<SemesterClass> findAllSemesterClassByStudentAndCourse(Long student_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester LEFT JOIN FETCH e.userRegisterTeachSemesters urt LEFT JOIN FETCH urt.teacher te  JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE te.id = ?1 AND c.id =?2 ORDER BY e.id")
    List<SemesterClass> findAllSemesterClassByTeacherAndCourse(Long teacher_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH urj.payer pa JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE pa.id = ?1 AND c.id =?2 AND urj.status = 'Completed' ORDER BY e.id")
    List<SemesterClass> findAllSemesterClassByParentAndCourse1(Long student_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH urj.payer pa JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE pa.id = ?1 AND c.id =?2 AND urj.status = 'Waiting' ORDER BY e.id")
    List<SemesterClass> findAllSemesterClassByParentAndCourse2(Long student_id, Long course_id);

    @Query(
		value = "SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ORDER BY e.id",
		countQuery = "SELECT e FROM SemesterClass e INNER JOIN e.semester  INNER JOIN e.course "
	)
    Page<SemesterClass> findAll(Pageable pageable);

    @Query("FROM SemesterClass e WHERE e.id = ?1")
    Optional<SemesterClass> findById1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course WHERE e.id = ?1")
    Optional<SemesterClass> findById2(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE e.id = ?1")
    Optional<SemesterClass> findById3(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime WHERE e.id = ?1")
    Optional<SemesterClass> findById4(Long id);

    boolean existsById(Long id);
    boolean existsByName(String name);
    void deleteById(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  se WHERE se.id = ?1 ORDER BY e.id")
    List<SemesterClass> findBySemesterId1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester se JOIN FETCH e.course c WHERE se.id = ?1 ORDER BY e.id")
    List<SemesterClass> findBySemesterId2(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.course co WHERE co.id = ?1 ORDER BY e.id")
    List<SemesterClass> findByCourseId1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.course co JOIN FETCH e.semester  WHERE co.id = ?1 ORDER BY e.id")
    List<SemesterClass> findByCourseId2(Long id);

    @Query("SELECT  DISTINCT e FROM SemesterClass e JOIN FETCH e.course c JOIN FETCH e.semester JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.id = ?1 ORDER BY e.id")
    List<SemesterClass> findByCourseId3(Long id);
}
