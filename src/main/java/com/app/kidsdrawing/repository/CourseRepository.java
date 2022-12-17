package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository <Course, Long>{

    @Query(
		value = "SELECT c FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Course c INNER JOIN c.artAges ag INNER JOIN c.artLevels al INNER JOIN c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)"
	)
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll();

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.semesterClasses sc LEFT JOIN FETCH sc.semester s JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll1();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll3();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.userRegisterJoinSemesters ur WHERE ur.status = 'Completed' AND (ur.deleted = FALSE OR ur.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll4();

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.semesterClasses sc LEFT JOIN FETCH sc.semester s JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at LEFT JOIN FETCH sc.userRegisterJoinSemesters ur WHERE (ur.deleted = FALSE OR ur.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll5();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te JOIN FETCH te.user WHERE te.id != ?1 AND tr.status = 'Approved' AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAllCourseNewForTeacher(Long teacher_id);

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te JOIN FETCH te.user JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE te.id = ?1 AND tr.status = 'Approved' AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) ORDER BY c.id")
    List<Course> findAllCourseNewForTeacher1(Long teacher_id);

    @Query("SELECT COUNT(c.id) FROM Course c WHERE (c.deleted = FALSE OR c.deleted IS NULL)")
    int findAll2();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.userRegisterJoinSemesters ur JOIN FETCH ur.student st JOIN FETCH st.user stu WHERE st.id = ?1 AND ur.status = 'Completed' AND (ur.deleted = FALSE OR ur.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY c.id")
    List<Course> findTotalCourseForStudent(Long student_id);

    @Query("FROM Course c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findByName1(String name);

    @Query("FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    Optional<Course> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByName(String name);

    @Query("FROM Course c JOIN FETCH c.artAges ag WHERE ag.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)")
    boolean findByArtAgeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at JOIN FETCH c.artLevels al WHERE ag.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    boolean findByArtAgeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at WHERE at.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL)")
    boolean findByArtTypeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag JOIN FETCH c.artLevels al WHERE at.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    boolean findByArtTypeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al WHERE al = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    boolean findByArtLevelId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE al.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    boolean findByArtLevelId2(Long id);


    @Query("FROM Course c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findById1(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes at JOIN FETCH c.artAges ag WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL)")
    Optional<Course> findById2(Long id);

    @Modifying
    @Query("UPDATE Course e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}