package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SemesterClass;

@Repository
public interface SemesterClassRepository extends JpaRepository <SemesterClass, Long>{
    
    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAll();

    @Query("SELECT  DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s  JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAll1();

    @Query("SELECT  DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt WHERE (lt.deleted = FALSE OR lt.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAll2();

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH st.user stu JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at  WHERE st.id = ?1 AND c.id =?2 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND urj.status = 'Waiting' AND (e.deleted = FALSE OR e.deleted IS NULL) AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAllSemesterClassByStudentAndCourseWaiting(Long student_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH st.user stu JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at  WHERE st.id = ?1 AND c.id =?2 AND urj.status = 'Completed' AND (c.deleted = FALSE OR c.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAllSemesterClassByStudentAndCourseCompleted(Long student_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.userRegisterTeachSemesters urt JOIN FETCH urt.teacher te JOIN FETCH te.user JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE te.id = ?1 AND c.id =?2 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAllSemesterClassByTeacherAndCourse(Long teacher_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester  s JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa  JOIN FETCH pa.user JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at  WHERE pa.id = ?1 AND c.id =?2 AND (c.deleted = FALSE OR c.deleted IS NULL) AND urj.status = 'Completed' AND (s.deleted = FALSE OR s.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAllSemesterClassByParentAndCourse1(Long student_id, Long course_id);

    @Query("SELECT DISTINCT e FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa JOIN FETCH pa.user JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at  WHERE pa.id = ?1 AND c.id =?2 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND urj.status = 'Waiting' AND (e.deleted = FALSE OR e.deleted IS NULL) AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findAllSemesterClassByParentAndCourse2(Long student_id, Long course_id);

    @Query(
		value = "SELECT e FROM SemesterClass e JOIN FETCH e.semester s  JOIN FETCH e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY e.id DESC",
		countQuery = "SELECT e FROM SemesterClass e INNER JOIN e.semester s  INNER JOIN e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)"
	)
    Page<SemesterClass> findAll(Pageable pageable);

    @Query("FROM SemesterClass e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<SemesterClass> findById1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  s JOIN FETCH e.course cou WHERE e.id = ?1 AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<SemesterClass> findById2(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)")
    Optional<SemesterClass> findById3(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester s JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<SemesterClass> findById4(Long id);

    boolean existsById(Long id);
    boolean existsByName(String name);
    
    @Modifying
    @Query("UPDATE SemesterClass e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  se WHERE se.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (se.deleted = FALSE OR se.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findBySemesterId1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester se JOIN FETCH e.course c WHERE se.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (se.deleted = FALSE OR se.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findBySemesterId2(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.course co WHERE co.id = ?1 AND (co.deleted = FALSE OR co.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findByCourseId1(Long id);

    @Query("FROM SemesterClass e JOIN FETCH e.course c JOIN FETCH e.semester s JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findByCourseId2(Long id);

    @Query("SELECT  DISTINCT e FROM SemesterClass e JOIN FETCH e.course c JOIN FETCH e.semester  s JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE c.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) ORDER BY e.id DESC")
    List<SemesterClass> findByCourseId3(Long id);
}
