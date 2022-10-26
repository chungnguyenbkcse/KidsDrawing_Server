package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SectionTemplate;

@Repository
public interface SectionTemplateRepository extends JpaRepository <SectionTemplate, Long>{
    @Query("SELECT e FROM SectionTemplate e JOIN FETCH e.course JOIN FETCH e.user ")
    List<SectionTemplate> findAll();

    @Query(
		value = "SELECT e FROM SectionTemplate e  JOIN FETCH e.course JOIN FETCH e.user ",
		countQuery = "SELECT e FROM SectionTemplate e  INNER JOIN e.course INNER JOIN e.user "
	)
    Page<SectionTemplate> findAll(Pageable pageable);

    @Query("FROM SectionTemplate e WHERE e.id = :id")
    Optional<SectionTemplate> findById1(Long id);

    @Query("FROM SectionTemplate e JOIN FETCH e.course JOIN FETCH e.user  WHERE e.id = :id")
    Optional<SectionTemplate> findById2(Long id);
    
    @Query("SELECT DISTINCT e FROM SectionTemplate e JOIN FETCH e.course co WHERE co.id = :id")
    List<SectionTemplate> findByCourseId1(Long id);

    @Query("SELECT DISTINCT e FROM SectionTemplate e JOIN FETCH e.course co JOIN FETCH e.user WHERE co.id = :id")
    List<SectionTemplate> findByCourseId2(Long id);

    @Query("SELECT DISTINCT e FROM SectionTemplate e JOIN FETCH e.course co WHERE co.id = ?1 AND e.number = ?2")
    Optional<SectionTemplate> findByCourseIdAndNumber1(Long course_id, int number);

    @Query("SELECT DISTINCT e FROM SectionTemplate e  JOIN FETCH e.course co JOIN FETCH e.user WHERE co.id = ?1 AND e.number = ?2")
    Optional<SectionTemplate> findByCourseIdAndNumber2(Long course_id, int number);

    @Query("FROM SectionTemplate e JOIN FETCH e.user u WHERE u.id = :id")
    List<SectionTemplate> findByCreatorId1(Long id);

    @Query("FROM SectionTemplate e JOIN FETCH e.user u JOIN FETCH e.course WHERE u.id = :id")
    List<SectionTemplate> findByCreatorId2(Long id);

    boolean existsById(Long id);
    void deleteById(Long id);
}
