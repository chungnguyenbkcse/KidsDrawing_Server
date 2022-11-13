package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, Long>{

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.id = ?1")
    boolean existsById(Long id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.name = ?1")
    Boolean existsByName(String name);

    void deleteById(Long id); 
    
    @Query("FROM Classes c  JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester")
    List<Classes> findAll();

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays JOIN FETCH u.teacher JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges")
    List<Classes> findAll1();

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays JOIN FETCH u.teacher te JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE te.id = ?1")
    List<Classes> findAllByTeacher(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.userRegisterJoinSemester urj JOIN FETCH urj.student st  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays JOIN FETCH u.teacher te JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE st.id = ?1")
    List<Classes> findAllByStudent(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.userRegisterJoinSemester urj JOIN FETCH urj.student st WHERE st.id = ?1")
    List<Classes> findAllByStudent1(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses crj JOIN FETCH crj.userRegisterJoinSemester urj JOIN FETCH urj.student st JOIN FETCH urj.payer pa  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays JOIN FETCH u.teacher te JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE pa.id = ?1")
    List<Classes> findAllByParent(Long id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.userRegisterTeachSemester = ?1")
    Boolean existsByUserRegisterTeachSemesterId(Long id);

    @Query("FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester urt WHERE urt.id = ?1")
    Optional<Classes> findByUserRegisterTeachSemesterId(Long id);

    @Query("FROM Classes c JOIN FETCH c.userRegisterTeachSemester urt WHERE urt.id = ?1")
    Optional<Classes> findByUserRegisterTeachSemesterId2(Long id);

    @Query("SELECT c FROM Classes c WHERE c.id = ?1 ")
    Optional<Classes> findById1(Long id);

    @Query("SELECT c FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.id = ?1 ")
    Optional<Classes> findById2(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays WHERE c1.id = ?1 ")
    Optional<Classes> findById3(Long id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays WHERE c1.id = ?1 ")
    Optional<Classes> findById4(Long id);

    @Query("SELECT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses  chr JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH urj.student JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s LEFT JOIN FETCH s.holidays JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c1.id = ?1 ")
    Optional<Classes> findById5(Long id);

    @Query("SELECT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses  chr JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH urj.student WHERE c1.id = ?1 ")
    Optional<Classes> findById6(Long id);

    @Query("FROM Classes c JOIN FETCH c.user u WHERE u.id = ?1")
    List<Classes> findByCreatorId1(Long id);

    @Query("FROM Classes c JOIN FETCH c.user  u JOIN FETCH c.userRegisterTeachSemester WHERE u.id = ?1")
    List<Classes> findByCreatorId2(Long id);
}
