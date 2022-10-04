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
    
    @Query("SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ORDER BY e.id")
    List<SemesterClass> findAll();

    @Query(
		value = "SELECT e FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course ORDER BY e.id",
		countQuery = "SELECT e FROM SemesterClass e INNER JOIN e.semester  INNER JOIN e.course ORDER BY e.id"
	)
    Page<SemesterClass> findAll(Pageable pageable);

    @Query("FROM SemesterClass e WHERE e.id = :id")
    Optional<SemesterClass> findById1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course WHERE e.id = :id")
    Optional<SemesterClass> findById2(UUID id);

    boolean existsById(UUID id);
    boolean existsByName(String name);
    void deleteById(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  WHERE e.semester = :id")
    List<SemesterClass> findBySemesterId1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.semester  JOIN FETCH e.course WHERE e.semester = :id")
    List<SemesterClass> findBySemesterId2(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.course WHERE e.course = :id")
    List<SemesterClass> findByCourseId1(UUID id);

    @Query("FROM SemesterClass e JOIN FETCH e.course JOIN FETCH e.semester  WHERE e.course = :id")
    List<SemesterClass> findByCourseId2(UUID id);
}
