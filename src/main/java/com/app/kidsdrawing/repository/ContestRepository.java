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
		value = "SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll(Pageable pageable);

    @Query(
		value = "SELECT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ",
		countQuery = "SELECT COUNT(c) FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll1(Pageable pageable);

    @Query(
		value = "SELECT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ",
		countQuery = "SELECT c FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions cs LEFT JOIN cs.userGradeContestSubmissions INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll3(Pageable pageable);

    @Query(
		value = "SELECT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll2(Pageable pageable);

    @Query("SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ")
    List<Contest> findAll();

    @Query("SELECT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ")
    List<Contest> findAll1();

    @Query("FROM Contest c WHERE c.id = :id")
    Optional<Contest> findById1(UUID id);

    @Query("FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.id = :id")
    Optional<Contest> findById2(UUID id);

    @Query("FROM Contest c WHERE c.name = :name")
    Optional<Contest> findByName1(String name);

    @Query("FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.name = :name")
    Optional<Contest> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Contest c WHERE c.id = :id")
    boolean existsById(UUID id);

    @Query("SELECT count(c.name) = 1 FROM Contest c WHERE c.name = :name")
    Boolean existsByName(String name);

    void deleteById(UUID id);

    @Query("FROM Contest c JOIN FETCH c.artAges ag WHERE ag.id = :artAges")
    List<Contest> findByArtAgeId1(UUID artAges);

    @Query("FROM Contest c JOIN FETCH c.artAges ag JOIN FETCH c.user JOIN FETCH c.artTypes WHERE ag.id = :artAges")
    List<Contest> findByArtAgeId2(UUID artAges);

    @Query("FROM Contest c JOIN FETCH c.artTypes at WHERE at.id = :artTypes")
    List<Contest> findByArtTypeId1(UUID artTypes);

    @Query("FROM Contest c JOIN FETCH c.artTypes at JOIN FETCH c.user JOIN FETCH c.artAges WHERE at.id = :artTypes")
    List<Contest> findByArtTypeId2(UUID artTypes);

    @Query("FROM Contest c JOIN FETCH c.user u WHERE u.id = :user")
    List<Contest> findByCreatorId1(UUID user);

    @Query("FROM Contest c JOIN FETCH c.user u JOIN FETCH c.artAges JOIN FETCH c.artTypes WHERE u.id = :user")
    List<Contest> findByCreatorId2(UUID user);
}
