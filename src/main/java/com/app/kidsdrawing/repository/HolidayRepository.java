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

    @Query("SELECT e FROM Holiday e JOIN FETCH e.semester")
    List<Holiday> findAll();

    @Query(
		value = "SELECT e FROM Holiday e JOIN FETCH e.semester",
		countQuery = "SELECT e FROM Holiday e INNER JOIN e.semester"
	)
    Page<Holiday> findAll(Pageable pageable);

    @Query("FROM Holiday e WHERE e.id = :id")
    Optional<Holiday> findById1(Long id);

    @Query("FROM Holiday e JOIN FETCH e.semester WHERE e.id = :id")
    Optional<Holiday> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Holiday e JOIN FETCH e.semester s WHERE s.id = :id")
    Optional<Holiday> findBySemesterId(Long id);

    @Query("SELECT COUNT(e.id) = 1 FROM Holiday e WHERE e.id = :id")
    boolean existsById(Long id);
    
    void deleteById(Long id);
}