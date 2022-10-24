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
    @Query("SELECT e FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest ")
    List<UserGradeContest> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest ",
		countQuery = "SELECT e FROM UserGradeContest e INNER JOIN e.user  INNER JOIN e.contest "
	)
    Page<UserGradeContest> findAll(Pageable pageable);

    @Query("FROM UserGradeContest e WHERE e.id = :id")
    Optional<UserGradeContest> findById1(UUID id);

    @Query("FROM UserGradeContest e JOIN FETCH e.user  JOIN FETCH e.contest WHERE e.id = :id")
    Optional<UserGradeContest> findById2(UUID id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.contest c WHERE c.id = :id")
    List<UserGradeContest> findByContestId1(UUID id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.contest c JOIN FETCH e.user WHERE c.id = :id")
    List<UserGradeContest> findByContestId2(UUID id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.user u WHERE u.id = :id")
    List<UserGradeContest> findByTeacherId1(UUID id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.user  u JOIN FETCH e.contest WHERE u.id = :id")
    List<UserGradeContest> findByTeacherId2(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
