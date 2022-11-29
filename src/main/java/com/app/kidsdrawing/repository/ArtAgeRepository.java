package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ArtAge;
@Repository
public interface ArtAgeRepository extends JpaRepository <ArtAge, Long>{
    @Query(
		value = "SELECT DISTINCT c FROM ArtAge c  WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id",
		countQuery = "SELECT COUNT(c) FROM ArtAge c WHERE (c.deleted = FALSE OR c.deleted IS NULL)"
	)
    Page<ArtAge> findAll(Pageable pageable);

    @Query("SELECT c FROM ArtAge c WHERE (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    List<ArtAge> findAll();

    @Query("SELECT c FROM ArtAge c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    Optional<ArtAge> findByName(String name);

    @Query("SELECT c FROM ArtAge c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    Optional<ArtAge> findById(Long id);

    @Query("SELECT COUNT(c.id) = 1 FROM ArtAge c WHERE c.id = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    boolean existsById(Long id);

    @Query("SELECT COUNT(c.id) = 1 FROM ArtAge c WHERE c.name = ?1 AND (c.deleted = FALSE OR c.deleted IS NULL) ORDER BY c.id")
    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE ArtAge e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
