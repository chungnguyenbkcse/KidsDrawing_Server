package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterJoinSemester;

@Repository
public interface UserRegisterJoinSemesterRepository extends JpaRepository <UserRegisterJoinSemester, Long>{
    
    @Query("SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass ORDER BY e.id")
    List<UserRegisterJoinSemester> findAll();

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass sc JOIN FETCH sc.course JOIN FETCH sc.semester ORDER BY e.id")
    List<UserRegisterJoinSemester> findAll1();

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass sc JOIN FETCH sc.course WHERE e.status = 'Completed'")
    List<UserRegisterJoinSemester> findAll3();

    @Query("SELECT SUM(e.price) FROM UserRegisterJoinSemester e  WHERE e.status = 'Completed'")
    Float findAll2();

    @Query(
		value = "SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterJoinSemester e INNER JOIN e.student  INNER JOIN e.payer INNER JOIN e.semesterClass "
	)
    Page<UserRegisterJoinSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterJoinSemester e WHERE e.id = ?1")
    Optional<UserRegisterJoinSemester> findById1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass WHERE e.id = ?1")
    Optional<UserRegisterJoinSemester> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  JOIN FETCH e.payer WHERE sc.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId2(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  JOIN FETCH e.payer WHERE e.status = 'Completed' ORDER BY e.id")
    List<UserRegisterJoinSemester> findBySemesterClassId3(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st WHERE st.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.payer JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId2(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.classHasRegisterJoinSemesterClass chr JOIN FETCH chr.classes  c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE st.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId3(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.classHasRegisterJoinSemesterClass chr JOIN FETCH chr.classes  c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByStudentId4(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.payer pa WHERE pa.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByPayerId1(Long id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.payer pa JOIN FETCH e.semesterClass sc JOIN FETCH e.student  JOIN FETCH sc.course c WHERE pa.id = ?1 ORDER BY e.id")
    List<UserRegisterJoinSemester> findByPayerId2(Long id);


}