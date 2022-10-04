package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.TeacherRegisterQualification;
@Repository
public interface TeacherRegisterQualificationRepository extends JpaRepository <TeacherRegisterQualification, UUID> {
    @Query("SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.reviewer  JOIN FETCH e.teacher JOIN FETCH e.course ORDER BY e.id")
    List<TeacherRegisterQualification> findAll();

    @Query(
		value = "SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.reviewer  JOIN FETCH e.teacher JOIN FETCH e.course ORDER BY e.id",
		countQuery = "SELECT e FROM TeacherRegisterQualification e INNER JOIN e.reviewer  INNER JOIN e.teacher INNER JOIN e.course ORDER BY e.id"
	)
    Page<TeacherRegisterQualification> findAll(Pageable pageable);

    @Query("FROM TeacherRegisterQualification e WHERE e.id = :id")
    Optional<TeacherRegisterQualification> findById1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course JOIN FETCH e.reviewer  JOIN FETCH e.teacher WHERE e.id = :id")
    Optional<TeacherRegisterQualification> findById2(UUID id);


    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher WHERE e.teacher = :id")
    List<TeacherRegisterQualification> findByTeacherId1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher JOIN FETCH e.course JOIN FETCH e.reviewer  WHERE e.teacher = :id")
    List<TeacherRegisterQualification> findByTeacherId2(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course WHERE e.course = :id")
    List<TeacherRegisterQualification> findByCourseId1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course JOIN FETCH e.reviewer  JOIN FETCH e.teacher WHERE e.course = :id")
    List<TeacherRegisterQualification> findByCourseId2(UUID id);

    boolean existsById(UUID id);
    void deleteById(UUID id);
}
