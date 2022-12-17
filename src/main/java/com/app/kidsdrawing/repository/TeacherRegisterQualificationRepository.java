package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.TeacherRegisterQualification;
@Repository
public interface TeacherRegisterQualificationRepository extends JpaRepository <TeacherRegisterQualification, Long> {
    @Query("SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findAll();

    @Query(
		value = "SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM TeacherRegisterQualification e INNER JOIN e.teacher te INNER JOIN te.user INNER JOIN e.course cou WHERE (e.deleted = FALSE OR e.deleted IS NULL) AND (cou.deleted = FALSE OR cou.deleted IS NULL)"
	)
    Page<TeacherRegisterQualification> findAll(Pageable pageable);

    @Query("FROM TeacherRegisterQualification e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<TeacherRegisterQualification> findById1(Long id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course cou JOIN FETCH e.teacher te JOIN FETCH te.user WHERE e.id = ?1 AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL)")
    Optional<TeacherRegisterQualification> findById2(Long id);


    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher te WHERE te.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findByTeacherId1(Long id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.course cou WHERE te.id = ?1 AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findByTeacherId2(Long id);

    @Query("SELECT DISTINCT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH te.user JOIN FETCH e.course co JOIN FETCH co.artLevels al JOIN FETCH co.artTypes at JOIN FETCH co.artAges ag WHERE te.id = ?1 AND (co.deleted = FALSE OR co.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) AND (at.deleted = FALSE OR at.deleted IS NULL) AND (ag.deleted = FALSE OR ag.deleted IS NULL) AND (al.deleted = FALSE OR al.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findByTeacherId3(Long id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course co WHERE co.id = ?1 AND (co.deleted = FALSE OR co.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findByCourseId1(Long id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course co JOIN FETCH e.teacher te JOIN FETCH te.user WHERE co.id = ?1 AND (co.deleted = FALSE OR co.deleted IS NULL) AND (e.deleted = FALSE OR e.deleted IS NULL) ORDER BY e.id")
    List<TeacherRegisterQualification> findByCourseId2(Long id);

    boolean existsById(Long id);
    
    @Modifying
    @Query("UPDATE TeacherRegisterQualification e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
