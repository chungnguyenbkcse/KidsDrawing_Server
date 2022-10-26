package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository <Course, Long>{

    @Query(
		value = "SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ",
		countQuery = "SELECT COUNT(c) FROM Course c INNER JOIN c.artAges INNER JOIN c.artLevels INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ")
    List<Course> findAll();

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.semesterClasses sc LEFT JOIN FETCH sc.semester JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ")
    List<Course> findAll1();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes")
    List<Course> findAll3();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te WHERE te.id != ?1")
    List<Course> findAllCourseNewForTeacher(Long teacher_id);

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.teacherRegisterQualifications tr JOIN FETCH tr.teacher te JOIN FETCH c.semesterClasses sc LEFT JOIN FETCH sc.userRegisterTeachSemesters urt LEFT JOIN FETCH urt.teacher te1 JOIN FETCH sc.semester JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes WHERE te.id = ?1")
    List<Course> findAllCourseNewForTeacher1(Long teacher_id);

    @Query("SELECT COUNT(c.id) FROM Course c")
    int findAll2();

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.userRegisterJoinSemesters ur JOIN FETCH ur.student st WHERE st.id = ?1")
    List<Course> findTotalCourseForStudent(Long student_id);

    @Query("FROM Course c WHERE c.name = :name")
    Optional<Course> findByName1(String name);

    @Query("FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.name = :name")
    Optional<Course> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.id = :id")
    boolean existsById(Long id);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.name = :name")
    Boolean existsByName(String name);

    @Query("FROM Course c JOIN FETCH c.artAges ag WHERE ag.id = :id")
    boolean findByArtAgeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes JOIN FETCH c.artLevels JOIN FETCH c.user WHERE ag.id = :id")
    boolean findByArtAgeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at WHERE at.id = :id")
    boolean findByArtTypeId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artTypes at JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.user WHERE at.id = :id")
    boolean findByArtTypeId2(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al WHERE al = :id")
    boolean findByArtLevelId1(Long id);

    @Query("FROM Course c JOIN FETCH c.artLevels al JOIN FETCH c.artTypes JOIN FETCH c.artAges JOIN FETCH c.user WHERE al.id = :id")
    boolean findByArtLevelId2(Long id);

    @Query("FROM Course c JOIN FETCH c.user u WHERE u.id = :id")
    boolean findByCreatorId1(Long id);

    @Query("FROM Course c JOIN FETCH c.user u JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE u.id = :id")
    boolean findByCreatorId2(Long id);

    @Query("FROM Course c WHERE c.id = :id")
    Optional<Course> findById1(Long id);

    @Query("FROM Course c JOIN FETCH c.user JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c.id = :id")
    Optional<Course> findById2(Long id);

    void deleteById(Long id);
}