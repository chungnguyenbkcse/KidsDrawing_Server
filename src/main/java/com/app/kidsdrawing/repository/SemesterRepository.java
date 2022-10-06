package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository <Semester, UUID>{
    @Query("SELECT e FROM Semester e JOIN FETCH e.user ")
    List<Semester> findAll();

    @Query("SELECT DISTINCT e FROM Semester e JOIN FETCH e.user JOIN FETCH e.semesterClass sc JOIN FETCH sc.userRegisterTeachSemesters urt JOIN FETCH urt.classes ")
    List<Semester> findAll1();

    @Query(
		value = "SELECT e FROM Semester e JOIN FETCH e.user ",
		countQuery = "SELECT e FROM Semester e INNER JOIN e.user "
	)
    Page<Semester> findAll(Pageable pageable);

    @Query("FROM Semester e JOIN FETCH e.user WHERE e.id = :id")
    Optional<Semester> findById2(UUID id);

    @Query("FROM Semester e WHERE e.id = :id")
    Optional<Semester> findById1(UUID id);

    @Query("SELECT DISTINCT se FROM Semester se JOIN FETCH se.semesterClass sc JOIN FETCH sc.userRegisterJoinSemesters urj JOIN FETCH sc.userRegisterTeachSemesters urt JOIN FETCH urt.teacher WHERE se.id = :id AND urj.status = 'Completed'")
    Optional<Semester> findById3(UUID id);

    @Query("FROM Semester e JOIN FETCH e.user WHERE e.user = :id")
    List<Semester> findByCreatorId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
