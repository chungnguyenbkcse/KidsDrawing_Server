package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository <Contest, Long>{

    @Query(
		value = "SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll1(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id",
		countQuery = "SELECT c FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions cs LEFT JOIN cs.userGradeContestSubmissions INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll3(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges INNER JOIN c.artTypes INNER JOIN c.user "
	)
    Page<Contest> findAll2(Pageable pageable);

    @Query("SELECT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id")
    List<Contest> findAll();

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions ugc JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE ugc.score IS NULL ORDER BY c.id")
    List<Contest> findAll1();

    @Query("SELECT DISTINCT c FROM Contest c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.id")
    List<Contest> findAll3();

    @Query("SELECT COUNT(c.id) FROM Contest c")
    int findAll2();

    @Query("SELECT DISTINCT c FROM Contest c JOIN FETCH c.userRegisterJoinContests ur JOIN FETCH ur.student st WHERE st.id = ?1 ORDER BY c.id")
    List<Contest> findAll3(Long student_id);

    @Query("FROM Contest c WHERE c.id = ?1")
    Optional<Contest> findById1(Long id);

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.id = ?1")
    Optional<Contest> findById2(Long id);

    @Query("FROM Contest c WHERE c.name = ?1")
    Optional<Contest> findByName1(String name);

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs LEFT JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.name = ?1")
    Optional<Contest> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Contest c WHERE c.id = ?1")
    boolean existsById(Long id);

    @Query("SELECT count(c.name) = 1 FROM Contest c WHERE c.name = ?1")
    Boolean existsByName(String name);

    void deleteById(Long id);

    @Query("FROM Contest c JOIN FETCH c.artAges ag WHERE ag.id = ?1 ORDER BY c.id")
    List<Contest> findByArtAgeId1(Long artAges);

    @Query("FROM Contest c JOIN FETCH c.artAges ag JOIN FETCH c.user JOIN FETCH c.artTypes WHERE ag.id = ?1 ORDER BY c.id")
    List<Contest> findByArtAgeId2(Long artAges);

    @Query("FROM Contest c JOIN FETCH c.artTypes at WHERE at.id = ?1 ORDER BY c.id")
    List<Contest> findByArtTypeId1(Long artTypes);

    @Query("FROM Contest c JOIN FETCH c.artTypes at JOIN FETCH c.user JOIN FETCH c.artAges WHERE at.id = ?1 ORDER BY c.id")
    List<Contest> findByArtTypeId2(Long artTypes);

    @Query("FROM Contest c JOIN FETCH c.user u WHERE u.id = ?1 ORDER BY c.id")
    List<Contest> findByCreatorId1(Long user);

    @Query("FROM Contest c JOIN FETCH c.user u JOIN FETCH c.artAges JOIN FETCH c.artTypes WHERE u.id = ?1 ORDER BY c.id")
    List<Contest> findByCreatorId2(Long user);
}
