package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository <Semester, UUID>{
    @Query("SELECT e FROM Semester e JOIN FETCH e.user ORDER BY e.id")
    List<Semester> findAll();

    @Query(
		value = "SELECT e FROM Semester e JOIN FETCH e.user ORDER BY e.id",
		countQuery = "SELECT e FROM Semester e INNER JOIN e.user ORDER BY e.id"
	)
    Page<Semester> findAll(Pageable pageable);

    @Query("FROM Semester e JOIN FETCH e.user WHERE e.id = :id")
    Optional<Semester> findById2(UUID id);

    @Query("FROM Semester e WHERE e.id = :id")
    Optional<Semester> findById1(UUID id);

    @Query("FROM Semester e JOIN FETCH e.user WHERE e.user = :id")
    List<Semester> findByCreatorId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
