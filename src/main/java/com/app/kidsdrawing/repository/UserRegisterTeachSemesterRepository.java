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
    
    @Query("SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass ORDER BY e.id")
    List<UserRegisterTeachSemester> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterTeachSemester e INNER JOIN e.teacher  INNER JOIN e.semesterClass "
	)
    Page<UserRegisterTeachSemester> findAll(Pageable pageable);

    @Query("FROM UserRegisterTeachSemester e WHERE e.id = ?1")
    Optional<UserRegisterTeachSemester> findById1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.id = ?1")
    Optional<UserRegisterTeachSemester> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc WHERE sc.id = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemesterClassId1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.semesterClass sc JOIN FETCH e.teacher  WHERE sc.id = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findBySemesterClassId2(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te WHERE te = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId1(Long id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH e.semesterClass WHERE te.id = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId2(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterTeachSemester e JOIN FETCH e.teacher te JOIN FETCH e.semesterClass sc JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE te.id = ?1 ORDER BY e.id")
    List<UserRegisterTeachSemester> findByTeacherId3(Long id);
}
