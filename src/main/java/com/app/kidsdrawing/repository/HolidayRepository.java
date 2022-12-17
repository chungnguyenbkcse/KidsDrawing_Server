package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.app.kidsdrawing.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository <Holiday, Long>{

    @Query("SELECT e FROM Holiday e JOIN FETCH e.semester s WHERE (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY e.id")
    List<Holiday> findAll();

    @Query(
		value = "SELECT e FROM Holiday e JOIN FETCH e.semester s WHERE (s.deleted = FALSE OR s.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM Holiday e INNER JOIN e.semester s WHERE (s.deleted = FALSE OR s.deleted IS NULL)"
	)
    Page<Holiday> findAll(Pageable pageable);

    @Query("FROM Holiday e WHERE e.id = ?1")
    Optional<Holiday> findById1(Long id);

    @Query("FROM Holiday e JOIN FETCH e.semester s WHERE e.id = ?1 AND (s.deleted = FALSE OR s.deleted IS NULL)")
    Optional<Holiday> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Holiday e JOIN FETCH e.semester s WHERE s.id = ?1 AND (s.deleted = FALSE OR s.deleted IS NULL)")
    List<Holiday> findBySemesterId(Long id);

    @Query("SELECT COUNT(e.id) = 1 FROM Holiday e WHERE e.id = ?1")
    boolean existsById(Long id);
    
    void deleteById(Long id);
}