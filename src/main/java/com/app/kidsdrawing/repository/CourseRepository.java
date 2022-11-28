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
		value = "SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Course c INNER JOIN c.artAges INNER JOIN c.artLevels INNER JOIN c.artTypes WHERE (c.deleted = FALSE OR c.deleted IS NULL)"
	)
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll();

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.semesterClasses sc LEFT JOIN FETCH sc.semester JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll1();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll3();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.userRegisterJoinSemesters ur WHERE ur.status = 'Completed' AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAll4();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te WHERE te.id != ?1 AND tr.status = 'Approved' AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAllCourseNewForTeacher(Long teacher_id);

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE te.id = ?1 AND tr.status = 'Approved' AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findAllCourseNewForTeacher1(Long teacher_id);

    @Query("SELECT COUNT(c.id) FROM Course c WHERE (c.deleted = FALSE OR c.deleted IS NULL)")
    int findAll2();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.userRegisterJoinSemesters ur JOIN FETCH ur.student st WHERE st.id = ?1 AND ur.status = 'Completed' AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Course> findTotalCourseForStudent(Long student_id);

    @Query("FROM Course c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findByName1(String name);

    @Query("FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByName(String name);

    @Query("FROM Course c JOIN FETCH c.artAges ag WHERE ag.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtAgeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes JOIN FETCH c.artLevels WHERE ag.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtAgeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at WHERE at.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtTypeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at JOIN FETCH c.artAges JOIN FETCH c.artLevels WHERE at.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtTypeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al WHERE al = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtLevelId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE al.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByArtLevelId2(Long id);

    @Query("FROM Course c JOIN FETCH c.user u WHERE u.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByCreatorId1(Long id);

    @Query("FROM Course c JOIN FETCH c.user u JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE u.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean findByCreatorId2(Long id);

    @Query("FROM Course c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findById1(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Course> findById2(Long id);

    @Modifying
    @Query("UPDATE Course e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}