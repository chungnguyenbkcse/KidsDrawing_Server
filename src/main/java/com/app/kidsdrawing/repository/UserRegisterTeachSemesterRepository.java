package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
@Repository
public interface UserRegisterTeachSemesterRepository extends JpaRepository <UserRegisterTeachSemester, Long> {
    
    @Query("SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user  JOIN FETCH e.semesterClass sc WHERE (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user  JOIN FETCH e.semesterClass sc WHERE (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterTeachSemester e INNER JOIN e.teacher te INNER JOIN te.user  INNER JOIN e.semesterClass sc WHERE  (sc.deleted = FALSE OR sc.deleted IS NULL)"
	)
    Page<UserRegisterTeachSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterTeachSemester e WHERE e.id = ?1")
    Optional<UserRegisterTeachSemester> findById1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user  JOIN FETCH e.semesterClass sc WHERE e.id = ?1 AND (sc.deleted = FALSE OR sc.deleted IS NULL)")
    Optional<UserRegisterTeachSemester> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemesterClassId1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.teacher te JOIN FETCH te.user WHERE sc.id = ?1 AND te.id = ?2 AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemesterClassAndTeacher(Long semester_class_id, Long teacher_id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc JOIN FETCH sc.course co WHERE co.id = ?1 AND (co.deleted = FALSE OR co.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findByCourseId(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester se WHERE se.id = ?1 AND te.id  = ?2 AND (lt.deleted = FALSE OR lt.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemester(Long semester_id, Long teacher_id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.teacher te JOIN FETCH te.user  WHERE sc.id = ?1 AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemesterClassId2(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user WHERE te = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.semesterClass sc WHERE te.id = ?1 AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId2(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE te.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId3(Long id);
}
