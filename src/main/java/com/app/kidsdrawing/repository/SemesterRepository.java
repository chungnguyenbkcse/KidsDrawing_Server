package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository <Semester, Long>{
    @Query("SELECT e FROM Semester e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<Semester> findAll();

    @Query("SELECT DISTINCT e FROM Semester e JOIN FETCH e.semesterClass sc WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY e.id")
    List<Semester> findAll1();

    @Query("SELECT e FROM Semester e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.end_time DESC")
    List<Semester> findAll2();

    @Query("SELECT e FROM Semester e WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND e.id != ?1 ORDER BY e.end_time DESC")
    List<Semester> findAll3(Long id);

    @Query(
		value = "SELECT e FROM Semester e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM Semester e WHERE (e.deleted = FALSE OR e.deleted IS NULL)"
	)
    Page<Semester> findAll(Pageable pageable);

    @Query("FROM Semester e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<Semester> findById2(Long id);

    @Query("FROM Semester e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<Semester> findById1(Long id);

    @Query("SELECT DISTINCT se FROM Semester se JOIN FETCH se.semesterClass sc JOIN FETCH sc.userRegisterJoinSemesters urj JOIN FETCH sc.userRegisterTeachSemesters urt JOIN FETCH urt.teacher te JOIN FETCH te.user WHERE se.id = ?1 AND urj.status = 'Completed' AND (urj.deleted = FALSE OR urj.deleted IS NULL) AND (se.deleted = FALSE OR se.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) ORDER BY urj.id")
    Optional<Semester> findById3(Long id);
    
    boolean existsById(Long id);

    @Modifying
    @Query("UPDATE Semester e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
