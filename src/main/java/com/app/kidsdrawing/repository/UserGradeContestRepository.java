package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserGradeContest;

@Repository
public interface UserGradeContestRepository extends JpaRepository <UserGradeContest, UUID>{
    @Query("SELECT e FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest ORDER BY e.id")
    List<UserGradeContest> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest ORDER BY e.id",
		countQuery = "SELECT e FROM UserGradeContest e INNER JOIN e.user  INNER JOIN e.contest ORDER BY e.id"
	)
    Page<UserGradeContest> findAll(Pageable pageable);

    @Query("FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest WHERE e.id = :id")
    Optional<UserGradeContest> findById(UUID id);

    @Query("FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest WHERE e.contest = :id")
    List<UserGradeContest> findByContestId(UUID id);

    @Query("FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest WHERE e.user = :id")
    List<UserGradeContest> findByTeacherId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
