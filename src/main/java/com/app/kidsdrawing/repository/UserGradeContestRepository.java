package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.UserGradeContestKey;

@Repository
public interface UserGradeContestRepository extends JpaRepository <UserGradeContest, Long>{
    @Query("SELECT e FROM UserGradeContest e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.contest ORDER BY e.id")
    List<UserGradeContest> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContest e JOIN FETCH e.teacher te JOIN FETCH te.user  JOIN FETCH e.contest ORDER BY e.id",
		countQuery = "SELECT e FROM UserGradeContest e INNER JOIN e.teacher  INNER JOIN e.contest "
	)
    Page<UserGradeContest> findAll(Pageable pageable);

    @Query("FROM UserGradeContest e WHERE e.id = ?1")
    Optional<UserGradeContest> findById1(UserGradeContestKey id);

    @Query("FROM UserGradeContest e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.contest WHERE e.id = ?1")
    Optional<UserGradeContest> findById2(UserGradeContestKey id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.contest c WHERE c.id = ?1 ORDER BY e.id")
    List<UserGradeContest> findByContestId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.contest c JOIN FETCH e.teacher te JOIN FETCH te.user WHERE c.id = ?1 AND te.id = ?2 ORDER BY e.id")
    Optional<UserGradeContest> findByContestAnsTeacher(Long contest_id, Long teacher_id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.contest c JOIN FETCH e.teacher te JOIN FETCH te.user WHERE c.id = ?1 ORDER BY e.number")
    List<UserGradeContest> findByContestId2(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.teacher u JOIN FETCH u.user WHERE u.id = ?1 ORDER BY e.id")
    List<UserGradeContest> findByTeacherId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContest e JOIN FETCH e.teacher  u JOIN FETCH u.user JOIN FETCH e.contest WHERE u.id = ?1 ORDER BY e.id")
    List<UserGradeContest> findByTeacherId2(Long id);
    
    boolean existsById(Long id);


    void deleteById(UserGradeContestKey id);
}
