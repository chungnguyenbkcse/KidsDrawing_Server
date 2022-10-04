package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository <Section, UUID>{
    @Query("SELECT e FROM Section e JOIN FETCH e.classes ORDER BY e.id")
    List<Section> findAll();

    @Query(
		value = "SELECT e FROM Section e JOIN FETCH e.classes ORDER BY e.id",
		countQuery = "SELECT e FROM Section e INNER JOIN e.classes ORDER BY e.id"
	)
    Page<Section> findAll(Pageable pageable);

    @Query("FROM Section e WHERE e.id = :id")
    Optional<Section> findById1(UUID id);

    @Query("FROM Section e JOIN FETCH e.classes WHERE e.id = :id")
    Optional<Section> findById2(UUID id);

    @Query("FROM Section e JOIN FETCH e.classes WHERE e.classes = :id ORDER BY e.number")
    List<Section> findByClassesId(UUID id);

    boolean existsById(UUID id);
    void deleteById(UUID id);
}