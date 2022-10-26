package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterJoinContest;

@Repository
public interface UserRegisterJoinContestRepository extends JpaRepository <UserRegisterJoinContest, Long>{
    @Query("SELECT e FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest ")
    List<UserRegisterJoinContest> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest ",
		countQuery = "SELECT e FROM UserRegisterJoinContest e INNER JOIN e.student  INNER JOIN e.contest "
	)
    Page<UserRegisterJoinContest> findAll(Pageable pageable);

    @Query("FROM UserRegisterJoinContest e WHERE e.id = :id")
    Optional<UserRegisterJoinContest> findById1(Long id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest WHERE e.id = :id")
    Optional<UserRegisterJoinContest> findById2(Long id);
    
    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.contest co WHERE co.id = :id")
    List<UserRegisterJoinContest> findByContestId1(Long id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.contest co JOIN FETCH e.student  WHERE co.id = :id")
    List<UserRegisterJoinContest> findByContestId2(Long id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student st WHERE st.id = :id")
    List<UserRegisterJoinContest> findByStudentId1(Long id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student st JOIN FETCH e.contest WHERE st.id = :id")
    List<UserRegisterJoinContest> findByStudentId2(Long id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student st JOIN FETCH e.contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE st.id = :id")
    List<UserRegisterJoinContest> findByStudentId3(Long id);

    @Query("SELECT DISTINCT e FROM UserRegisterJoinContest e JOIN FETCH e.student st JOIN FETCH e.contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH st.parent pa WHERE pa.id = :id")
    List<UserRegisterJoinContest> findByParent(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);
}