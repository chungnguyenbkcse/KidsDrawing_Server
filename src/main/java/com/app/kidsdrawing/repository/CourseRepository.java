package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository <Course, UUID>{

    @Query(
		value = "SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.create_time",
		countQuery = "SELECT COUNT(c) FROM Course c INNER JOIN c.artAges INNER JOIN c.artLevels INNER JOIN c.artTypes INNER JOIN c.user ORDER BY c.create_time"
	)
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.create_time")
    List<Course> findAll();

    @Query("SELECT c FROM Course c JOIN FETCH c.semesterClasses sc JOIN FETCH sc.semester JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user ORDER BY c.create_time")
    List<Course> findAll1();

    @Query("FROM Course c WHERE c.name = :name")
    Optional<Course> findByName1(String name);

    @Query("FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.user WHERE c.name = :name")
    Optional<Course> findByName2(String name);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.id = :id")
    boolean existsById(UUID id);

    @Query("SELECT count(c.id) = 1 FROM Course c WHERE c.name = :name")
    Boolean existsByName(String name);

    @Query("FROM Course c JOIN FETCH c.artAges WHERE c.artAges = :id")
    boolean findByArtAgeId1(UUID id);

    @Query("FROM Course c JOIN FETCH c.artAges JOIN FETCH c.artTypes JOIN FETCH c.artLevels JOIN FETCH c.user WHERE c.artAges = :id")
    boolean findByArtAgeId2(UUID id);

    @Query("FROM Course c JOIN FETCH c.artTypes WHERE c.artTypes = :id")
    boolean findByArtTypeId1(UUID id);

    @Query("FROM Course c JOIN FETCH c.artTypes JOIN FETCH c.artAges JOIN FETCH c.artLevels JOIN FETCH c.user WHERE c.artTypes = :id")
    boolean findByArtTypeId2(UUID id);

    @Query("FROM Course c JOIN FETCH c.artLevels WHERE c.artLevels = :id")
    boolean findByArtLevelId1(UUID id);

    @Query("FROM Course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges JOIN FETCH c.user WHERE c.artLevels = :id")
    boolean findByArtLevelId2(UUID id);

    @Query("FROM Course c JOIN FETCH c.user WHERE c.user = :id")
    boolean findByCreatorId1(UUID id);

    @Query("FROM Course c JOIN FETCH c.user JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c.user = :id")
    boolean findByCreatorId2(UUID id);

    @Query("FROM Course c WHERE c.id = :id")
    Optional<Course> findById1(UUID id);

    @Query("FROM Course c JOIN FETCH c.user JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c.id = :id")
    Optional<Course> findById2(UUID id);

    void deleteById(UUID id);
}