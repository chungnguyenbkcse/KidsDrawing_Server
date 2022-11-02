package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent ORDER BY e.id")
    List<User> findAll();

    @Query("SELECT COUNT(e.id) FROM User e INNER JOIN e.roles ro WHERE ro.name = 'STUDENT_USER'")
    int findAll1();

    @Query("SELECT COUNT(e.id) FROM User e INNER JOIN e.roles ro WHERE ro.name = 'PARENT_USER'")
    int findAll2();

    @Query("SELECT COUNT(e.id) FROM User e INNER JOIN e.roles ro WHERE ro.name = 'TEACHER_USER'")
    int findAll3();

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent JOIN FETCH e.roles ro WHERE ro.name = 'STUDENT_USER' ORDER BY e.id")
    List<User> findAllStudent();

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent JOIN FETCH e.roles ro WHERE ro.name = ?1 ORDER BY e.id")
    List<User> findAllUserByRole(String role_name);

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent LEFT JOIN FETCH e.roles ORDER BY e.id")
    List<User> findAllFetchRole();

    @Query(
		value = "SELECT e FROM User e LEFT JOIN FETCH e.parent ",
		countQuery = "SELECT e FROM User e LEFT JOIN e.parent "
	)
    Page<User> findAll(Pageable pageable);

    @Query("SELECT e FROM User e WHERE e.id = ?1")
    Optional<User> findById1(Long id);

    @Query("SELECT DISTINCT e FROM User e JOIN FETCH e.userRegisterJoinSemesters2 WHERE e.id = ?1")
    Optional<User> findById3(Long id);

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent WHERE e.id = ?1")
    Optional<User> findById2(Long id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.username = ?1")
    Boolean existsByUsername(String username);

    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.email = ?1")
    Boolean existsByEmail(String email);

    @Query("FROM User e WHERE e.username = ?1 OR e.email = ?2")
    Optional<User> findByUsernameOrEmail1(String username, String email);

    @Query("FROM User e LEFT JOIN FETCH e.parent WHERE e.username = ?1 OR e.email = ?2")
    Optional<User> findByUsernameOrEmail2(String username, String email);

    @Query("SELECT e FROM User e WHERE e.username = ?1")
    Optional<User> findByUsername1(String username);

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent WHERE e.username = ?1")
    Optional<User> findByUsername2(String username);

    @Query("FROM User e WHERE e.email = ?1")
    Optional<User> findByEmail1(String email);

    @Query("FROM User e LEFT JOIN FETCH e.parent WHERE e.email = ?1")
    Optional<User> findByEmail2(String email);

    @Query("FROM User e JOIN FETCH e.parent p WHERE p.id = ?1 ORDER BY e.id")
    List<User> findByParentId(Long id);

    @Query("SELECT DISTINCT e FROM User e JOIN FETCH e.parent p JOIN FETCH e.userRegisterJoinSemesters2 urj JOIN FETCH urj.semesterClass sc JOIN FETCH sc.semester  s JOIN FETCH s.holidays JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime  WHERE p.id = ?1 ORDER BY e.id")
    List<User> findByParentId1(Long id);

    @Query("SELECT DISTINCT x FROM User x JOIN FETCH x.parent p JOIN FETCH x.userRegisterJoinContests e JOIN FETCH e.student JOIN FETCH e.contest c JOIN FETCH c.userRegisterJoinContests JOIN FETCH c.contestSubmissions cs JOIN FETCH cs.userGradeContestSubmissions JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.user WHERE p.id = ?1 ORDER BY x.id")
    List<User> findByParentId2(Long id);

    @Query("SELECT DISTINCT e FROM User e JOIN FETCH e.parent p JOIN FETCH e.userRegisterJoinSemesters2 urj JOIN FETCH urj.semesterClass sc JOIN FETCH sc.semester  s JOIN FETCH sc.course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE p.id = ?1 ORDER BY e.id")
    List<User> findByParentId3(Long id);
}
