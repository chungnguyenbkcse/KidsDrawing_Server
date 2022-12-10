package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterJoinSemester;

@Repository
public interface UserRegisterJoinSemesterRepository extends JpaRepository <UserRegisterJoinSemester, Long>{
    
    @Query("SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  st JOIN FETCH st.parent JOIN FETCH e.semesterClass WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findAll();

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  st JOIN FETCH st.parent JOIN FETCH e.semesterClass sc JOIN FETCH sc.course JOIN FETCH sc.semester WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findAll1();

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  st JOIN FETCH st.parent JOIN FETCH e.semesterClass sc JOIN FETCH sc.course WHERE e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL)")
    List<UserRegisterJoinSemester> findAll3();

    @Query("SELECT SUM(e.price) FROM UserRegisterJoinSemester e  WHERE e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Float findAll2();

    @Query(
		value = "SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  st JOIN FETCH st.parent JOIN FETCH e.semesterClass WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterJoinSemester e INNER JOIN e.student  INNER JOIN e.semesterClass WHERE (e.deleted = FALSE OR e.deleted IS NULL)"
	)
    Page<UserRegisterJoinSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterJoinSemester e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<UserRegisterJoinSemester> findById1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student  st JOIN FETCH st.parent JOIN FETCH e.semesterClass WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<UserRegisterJoinSemester> findById2(Long id);
    
    boolean existsById(Long id);

    @Modifying
    @Query("UPDATE UserRegisterJoinSemester e SET e.deleted = true WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    void deleteById(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student st WHERE sc.id = ?1 AND st.id = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    Optional<UserRegisterJoinSemester> findBySemesterClassIdAndStudent(Long semester_class_id, Long student_id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  st JOIN FETCH st.parent WHERE sc.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId2(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  st JOIN FETCH st.parent  WHERE sc.id = ?1 AND e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId3(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 AND e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId4(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH sc.course co WHERE e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL) AND co.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByCourse(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH st.parent JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId2(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH st.parent JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = ?1 AND e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId3(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH st.parent pa WHERE pa.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByPayerId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH st.parent pa JOIN FETCH e.semesterClass sc JOIN FETCH sc.course c WHERE pa.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByPayerId2(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH st.parent pa JOIN FETCH e.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.semester WHERE pa.id = ?1 AND e.status = 'Completed' AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findByPayerId3(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student te JOIN FETCH e.semesterClass sc JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime lt JOIN FETCH sc.semester se WHERE se.id = ?1 AND te.id  = ?2 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemester(Long semester_id, Long student_id);


}