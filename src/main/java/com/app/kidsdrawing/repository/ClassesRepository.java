package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, Long>{

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Classes e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
    
    @Query("SELECT DISTINCT c FROM Classes c JOIN FETCH c.classHasRegisterJoinSemesterClasses crj  JOIN FETCH c.teacher te JOIN FETCH te.user JOIN FETCH c.semesterClass sc JOIN FETCH sc.course cou JOIN FETCH sc.semester s WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL)")
    List<Classes> findAll();

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE (c1.deleted = FALSE OR c1.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAll1();

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  WHERE (c1.deleted = FALSE OR c1.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAll2();

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st JOIN FETCH st.user stu  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE te.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByTeacher(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st  JOIN FETCH st.user stu JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c WHERE te.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByTeacher1(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH c1.teacher te JOIN FETCH te.user WHERE (te.id = ?1 AND s.id = ?2) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByTeacherAndSemester(Long teacher_id, Long semester_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artTypes at WHERE at.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByArtType(Long art_type_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels al WHERE al.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByArtLevel(Long art_level_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artAges ag WHERE ag.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByArtAge(Long art_age_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByCourse(Long course_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt WHERE lt.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllByLessonTime(Long lesson_time_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s WHERE sc.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllBySemesterClass(Long semester_class_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.semester s WHERE s.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Classes> findAllBySemester(Long semester_id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st  JOIN FETCH st.user stu JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges  ag WHERE st.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL)  AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByStudent(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st JOIN FETCH st.user stu JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.course cou WHERE st.id = ?1 AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByStudent2(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st JOIN FETCH st.user stu WHERE st.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByStudent1(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa  JOIN FETCH pa.user JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH pa.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE pa.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<Classes> findAllByParent(Long id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.teacher = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByTeacherId(Long id);

    @Query("FROM Classes c JOIN FETCH c.teacher te JOIN FETCH te.user WHERE te.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Classes> findByTeacherId(Long id);

    @Query("FROM Classes c JOIN FETCH c.teacher te JOIN FETCH te.user WHERE te.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Classes> findByTeacherId2(Long id);

    @Query("SELECT c FROM Classes c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Classes> findById1(Long id);

    @Query("SELECT c FROM Classes c JOIN FETCH c.teacher te JOIN FETCH te.user  WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Classes> findById2(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules JOIN FETCH sc.semester s  WHERE c1.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<Classes> findById3(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  WHERE c1.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<Classes> findById4(Long id);

    @Query("SELECT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses  chr JOIN FETCH chr.student st  JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester s  JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE c1.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<Classes> findById5(Long id);

    @Query("SELECT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses  chr JOIN FETCH chr.student st  WHERE c1.id = ?1 AND (c1.deleted = FALSE OR c1.deleted IS NULL)")
    Optional<Classes> findById6(Long id);
}
