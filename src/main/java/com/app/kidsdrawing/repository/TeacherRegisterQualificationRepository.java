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
    @Query("SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher JOIN FETCH e.course ")
    List<TeacherRegisterQualification> findAll();

    @Query(
		value = "SELECT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher JOIN FETCH e.course ",
		countQuery = "SELECT e FROM TeacherRegisterQualification e INNER JOIN e.reviewer  INNER JOIN e.teacher INNER JOIN e.course "
	)
    Page<TeacherRegisterQualification> findAll(Pageable pageable);

    @Query("FROM TeacherRegisterQualification e WHERE e.id = :id")
    Optional<TeacherRegisterQualification> findById1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course JOIN FETCH e.teacher WHERE e.id = :id")
    Optional<TeacherRegisterQualification> findById2(UUID id);


    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher te WHERE te.id = :id")
    List<TeacherRegisterQualification> findByTeacherId1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH e.course WHERE te.id = :id")
    List<TeacherRegisterQualification> findByTeacherId2(UUID id);

    @Query("SELECT DISTINCT e FROM TeacherRegisterQualification e JOIN FETCH e.teacher te JOIN FETCH e.course co JOIN FETCH co.artLevels JOIN FETCH co.artTypes JOIN FETCH co.artAges WHERE te.id = :id")
    List<TeacherRegisterQualification> findByTeacherId3(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course co WHERE co.id = :id")
    List<TeacherRegisterQualification> findByCourseId1(UUID id);

    @Query("FROM TeacherRegisterQualification e JOIN FETCH e.course co JOIN FETCH e.teacher WHERE co.id = :id")
    List<TeacherRegisterQualification> findByCourseId2(UUID id);

    boolean existsById(UUID id);
    void deleteById(UUID id);
}
