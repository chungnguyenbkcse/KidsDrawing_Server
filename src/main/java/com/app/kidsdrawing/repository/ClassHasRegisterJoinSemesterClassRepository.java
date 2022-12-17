package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;

@Repository
public interface ClassHasRegisterJoinSemesterClassRepository extends JpaRepository <ClassHasRegisterJoinSemesterClass, Long>{
    @Query("SELECT count(c.id) = 1 FROM ClassHasRegisterJoinSemesterClass c  WHERE c.id = ?1")
    boolean existsById(Long id);

    void deleteById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes cl JOIN FETCH c.student  st JOIN FETCH st.user stu WHERE (cl.deleted = FALSE OR cl.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findAll();

    @Query("SELECT DISTINCT chr FROM ClassHasRegisterJoinSemesterClass chr  JOIN FETCH chr.classes c1 JOIN FETCH c1.sections JOIN FETCH chr.student st JOIN FETCH st.user stu JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at  JOIN FETCH c.artAges ag WHERE (at.deleted = FALSE OR at.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findAll1();

    @Query("SELECT DISTINCT chr FROM ClassHasRegisterJoinSemesterClass chr  JOIN FETCH chr.classes c1  JOIN FETCH chr.student st  JOIN FETCH st.user stu JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c   WHERE st.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findAllByStudent(Long id);

    @Query("SELECT DISTINCT chr FROM ClassHasRegisterJoinSemesterClass chr  JOIN FETCH chr.classes c1  JOIN FETCH chr.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa  JOIN FETCH pa.user JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH c1.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH c1.teacher te JOIN FETCH te.user JOIN FETCH sc.course c   WHERE pa.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c1.deleted = FALSE OR c1.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findAllByParent(Long id);

    @Query("SELECT c FROM ClassHasRegisterJoinSemesterClass c JOIN FETCH c.classes cl  WHERE c.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    Optional<ClassHasRegisterJoinSemesterClass> findById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.student st JOIN FETCH st.user stu JOIN FETCH c.classes cl WHERE cl.id = ?1 AND urj = ?2 AND (stu.deleted = FALSE OR stu.deleted IS NULL) AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    Optional<ClassHasRegisterJoinSemesterClass> findByClassIdAndUserRegisterJoinSemester(Long class_id, Long user_register_join_semester_id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.student st JOIN FETCH st.user stu WHERE c.id = ?1 AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    Optional<ClassHasRegisterJoinSemesterClass> findByUserRegisterJoinSemesterId1(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.student st JOIN FETCH st.user stu JOIN FETCH c.classes cl WHERE c.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    Optional<ClassHasRegisterJoinSemesterClass> findByUserRegisterJoinSemesterId2(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId1(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl JOIN FETCH c.student st JOIN FETCH st.user stu WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId2(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl JOIN FETCH c.student st JOIN FETCH st.user stu JOIN FETCH st.parent pa JOIN FETCH pa.user WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId3(Long id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl JOIN FETCH c.student st JOIN FETCH st.user stu WHERE cl.id = ?1 AND st.id =?2 AND (cl.deleted = FALSE OR cl.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL)")
    Optional<ClassHasRegisterJoinSemesterClass> findByClassesIdAndStudentId(Long classes_id, Long student_id);
}
