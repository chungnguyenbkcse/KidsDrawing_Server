package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.SemesterClass;

@Repository
public interface SemesterClassRepository extends JpaRepository <SemesterClass, UUID>{
    
    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ")
    List<SemesterClass> findAll();

    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ")
    List<SemesterClass> findAll1();

    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH e.userRegisterJoinSemesters urj JOIN FETCH urj.student st JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE st.id = ?1 AND c.id =?2")
    List<SemesterClass> findAllSemesterClassByStudentAndCourse(UUID student_id, UUID course_id);

    @Query(
		value = "SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ",
		countQuery = "SELECT e FROM SemesterClass e INNER JOIN e.semester  INNER JOIN e.course "
	)
    Page<SemesterClass> findAll(Pageable pageable);

    @Query("FROM SemesterClass e WHERE e.id = :id")
    Optional<SemesterClass> findById1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course WHERE e.id = :id")
    Optional<SemesterClass> findById2(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE e.id = :id")
    Optional<SemesterClass> findById3(UUID id);

    boolean existsById(UUID id);
    boolean existsByName(String name);
    void deleteById(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  se WHERE se.id = :id")
    List<SemesterClass> findBySemesterId1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester se JOIN FETCH e.course c WHERE se.id = :id")
    List<SemesterClass> findBySemesterId2(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.course co WHERE co.id = :id")
    List<SemesterClass> findByCourseId1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.course co JOIN FETCH e.semester  WHERE co.id = :id")
    List<SemesterClass> findByCourseId2(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.course c JOIN FETCH e.semester JOIN FETCH e.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.id = :id")
    List<SemesterClass> findByCourseId3(UUID id);
}
