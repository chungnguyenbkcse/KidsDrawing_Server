package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
@Repository
public interface UserRegisterTeachSemesterRepository extends JpaRepository <UserRegisterTeachSemester, UUID> {
    
    @Query("SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass ORDER BY e.id")
    List<UserRegisterTeachSemester> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterTeachSemester e INNER JOIN e.teacher  INNER JOIN e.semesterClass ORDER BY e.id"
	)
    Page<UserRegisterTeachSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterTeachSemester e WHERE e.id = :id")
    Optional<UserRegisterTeachSemester> findById1(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.id = :id")
    Optional<UserRegisterTeachSemester> findById2(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass WHERE e.semesterClass = :id")
    List<UserRegisterTeachSemester> findBySemesterClassId1(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass JOIN FETCH e.teacher  WHERE e.semesterClass = :id")
    List<UserRegisterTeachSemester> findBySemesterClassId2(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher WHERE e.teacher = :id")
    List<UserRegisterTeachSemester> findByTeacherId1(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.teacher = :id")
    List<UserRegisterTeachSemester> findByTeacherId2(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE e.teacher = :id")
    List<UserRegisterTeachSemester> findByTeacherId3(UUID id);
}
