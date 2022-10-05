package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterJoinSemester;

@Repository
public interface UserRegisterJoinSemesterRepository extends JpaRepository <UserRegisterJoinSemester, UUID>{
    
    @Query("SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass ")
    List<UserRegisterJoinSemester> findAll();

    @Query("SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass sc JOIN FETCH sc.course JOIN FETCH sc.semester ")
    List<UserRegisterJoinSemester> findAll1();

    @Query(
		value = "SELECT e FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass ",
		countQuery = "SELECT e FROM UserRegisterJoinSemester e INNER JOIN e.student  INNER JOIN e.payer INNER JOIN e.semesterClass "
	)
    Page<UserRegisterJoinSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterJoinSemester e WHERE e.id = :id")
    Optional<UserRegisterJoinSemester> findById1(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student  JOIN FETCH e.payer JOIN FETCH e.semesterClass WHERE e.id = :id")
    Optional<UserRegisterJoinSemester> findById2(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = :id")
    List<UserRegisterJoinSemester> findBySemesterClassId1(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  JOIN FETCH e.payer WHERE sc.id = :id")
    List<UserRegisterJoinSemester> findBySemesterClassId2(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.student  JOIN FETCH e.payer WHERE e.status = 'Completed'")
    List<UserRegisterJoinSemester> findBySemesterClassId3(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st WHERE st.id = :id")
    List<UserRegisterJoinSemester> findByStudentId1(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.payer JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = :id")
    List<UserRegisterJoinSemester> findByStudentId2(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.classHasRegisterJoinSemesterClass chr JOIN FETCH chr.classes  c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE st.id = :id")
    List<UserRegisterJoinSemester> findByStudentId3(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.student st JOIN FETCH e.classHasRegisterJoinSemesterClass chr JOIN FETCH chr.classes  c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c WHERE st.id = :id")
    List<UserRegisterJoinSemester> findByStudentId4(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.payer pa WHERE pa.id = :id")
    List<UserRegisterJoinSemester> findByPayerId1(UUID id);

    @Query("FROM UserRegisterJoinSemester e JOIN FETCH e.payer pa JOIN FETCH e.semesterClass JOIN FETCH e.student  WHERE pa.id = :id")
    List<UserRegisterJoinSemester> findByPayerId2(UUID id);
}