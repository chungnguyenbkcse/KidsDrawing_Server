package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository <Contest, Long>{

    @Query(
		value = "SELECT c FROM Contest c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges ag INNER JOIN c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)"
	)
    Page<Contest> findAll(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions INNER JOIN c.artAges ag INNER JOIN c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)"
	)
    Page<Contest> findAll1(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs  JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT c FROM Contest c LEFT JOIN c.userRegisterJoinContests LEFT JOIN c.contestSubmissions cs  INNER JOIN c.artAges ag INNER JOIN c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)"
	)
    Page<Contest> findAll3(Pageable pageable);

    @Query(
		value = "SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs  JOIN FETCH c.artAges ag JOIN FETCH c.artTypes  at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM Contest c INNER JOIN c.artAges ag INNER JOIN c.artTypes at WHERE (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)"
	)
    Page<Contest> findAll2(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs  JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE cs.score IS NULL AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findAll1();

    @Query("SELECT DISTINCT c FROM Contest c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findAll3();

    @Query("SELECT c FROM Contest c JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findAll();

    @Query("SELECT COUNT(c.id) FROM Contest c WHERE (c.deleted = FALSE OR c.deleted IS NULL)")
    int findAll2();

    @Query("SELECT DISTINCT c FROM Contest c JOIN FETCH c.userRegisterJoinContests ur JOIN FETCH ur.student st JOIN FETCH st.user stu WHERE st.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (stu.deleted = FALSE OR stu.deleted IS NULL) ORDER BY c.id")
    List<Contest> findAll3(Long student_id);

    @Query("FROM Contest c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Contest> findById1(Long id);

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs  JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)")
    Optional<Contest> findById2(Long id);

    @Query("FROM Contest c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Contest> findByName1(String name);

    @Query("SELECT DISTINCT c FROM Contest c LEFT JOIN FETCH c.userRegisterJoinContests LEFT JOIN FETCH c.contestSubmissions cs  JOIN FETCH c.artAges ag JOIN FETCH c.artTypes at  WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL)")
    Optional<Contest> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Contest c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT count(c.name) = 1 FROM Contest c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Contest e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);

    @Query("FROM Contest c JOIN FETCH c.artAges ag WHERE ag.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findByArtAgeId1(Long artAges);

    @Query("FROM Contest c JOIN FETCH c.artAges ag  JOIN FETCH c.artTypes at WHERE ag.id = ?1 AND (at.deleted = FALSE OR at.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findByArtAgeId2(Long artAges);

    @Query("FROM Contest c JOIN FETCH c.artTypes at WHERE at.id = ?1 AND (at.deleted = FALSE OR at.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<Contest> findByArtTypeId1(Long artTypes);

    @Query("FROM Contest c JOIN FETCH c.artTypes at  JOIN FETCH c.artAges  ag WHERE at.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) ORDER BY c.id")
    List<Contest> findByArtTypeId2(Long artTypes);

}
