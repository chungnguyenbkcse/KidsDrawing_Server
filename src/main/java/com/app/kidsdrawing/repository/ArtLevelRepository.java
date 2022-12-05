package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ArtLevel;
@Repository
public interface ArtLevelRepository extends JpaRepository <ArtLevel, Long>{
    @Query(
		value = "SELECT DISTINCT c FROM ArtLevel c  WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM ArtLevel c WHERE (c.deleted = FALSE OR c.deleted IS NULL)"
	)
    Page<ArtLevel> findAll(Pageable pageable);

    @Query("SELECT c FROM ArtLevel c WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<ArtLevel> findAll();

    @Query("SELECT c FROM ArtLevel c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    Optional<ArtLevel> findByName(String name);

    @Query("SELECT c FROM ArtLevel c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    Optional<ArtLevel> findById(Long id);

    @Query("SELECT COUNT(c.id) = 1 FROM ArtLevel c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    boolean existsById(Long id);

    @Query("SELECT COUNT(c.id) = 1 FROM ArtLevel c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE ArtLevel e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
