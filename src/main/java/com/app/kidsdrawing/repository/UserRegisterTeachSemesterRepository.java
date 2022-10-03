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

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.id = :id")
    Optional<UserRegisterTeachSemester> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.semesterClass = :id")
    List<UserRegisterTeachSemester> findBySemesterClassId(UUID id);

    @Query("FROM UserRegisterTeachSemester e JOIN FETCH e.teacher  JOIN FETCH e.semesterClass WHERE e.teacher = :id")
    List<UserRegisterTeachSemester> findByTeacherId(UUID id);
}
