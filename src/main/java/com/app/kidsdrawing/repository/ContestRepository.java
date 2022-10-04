package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository <Contest, UUID>{

    @Query(
		value = "SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.start_time",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user ORDER BY c.start_time"
	)
    Page<Contest> findAll(Pageable pageable);

    @Query(
		value = "SELECT c FROM Contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.start_time",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user ORDER BY c.start_time"
	)
    Page<Contest> findAll1(Pageable pageable);

    @Query(
		value = "SELECT c FROM Contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.start_time",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user ORDER BY c.start_time"
	)
    Page<Contest> findAll2(Pageable pageable);

    @Query("SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.start_time")
    List<Contest> findAll();

    @Query("SELECT c FROM Contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.start_time")
    List<Contest> findAll1();

    @Query("FROM Contest c WHERE c.id = :id")
    Optional<Contest> findById1(UUID id);

    @Query("FROM Contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.id = :id")
    Optional<Contest> findById2(UUID id);

    @Query("FROM Contest c WHERE c.name = :name")
    Optional<Contest> findByName1(String name);

    @Query("FROM Contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.name = :name")
    Optional<Contest> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Contest c WHERE c.id = :id")
    boolean existsById(UUID id);

    @Query("SELECT count(c.name) = 1 FROM Contest c WHERE c.name = :name")
    Boolean existsByName(String name);

    void deleteById(UUID id);

    @Query("FROM Contest c JOIN FETCH c.artAges WHERE c.artAges = :artAges")
    List<Contest> findByArtAgeId1(UUID artAges);

    @Query("FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.user JOIN FETCH c.artTypes WHERE c.artAges = :artAges")
    List<Contest> findByArtAgeId2(UUID artAges);

    @Query("FROM Contest c JOIN FETCH c.artTypes WHERE c.artTypes = :artTypes")
    List<Contest> findByArtTypeId1(UUID artTypes);

    @Query("FROM Contest c JOIN FETCH c.artTypes JOIN FETCH c.user JOIN FETCH c.artAges WHERE c.artTypes = :artTypes")
    List<Contest> findByArtTypeId2(UUID artTypes);

    @Query("FROM Contest c JOIN FETCH c.user WHERE c.user = :user")
    List<Contest> findByCreatorId1(UUID user);

    @Query("FROM Contest c JOIN FETCH c.user JOIN FETCH c.artAges JOIN FETCH c.artTypes WHERE c.user = :user")
    List<Contest> findByCreatorId2(UUID user);
}
