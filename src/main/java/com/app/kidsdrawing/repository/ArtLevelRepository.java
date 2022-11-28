package com.app.kidsdrawing.repository;

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
    Page<ArtLevel> findAll(Pageable pageable);
    Optional<ArtLevel> findByName(String name);
    boolean existsById(Long id);
    Boolean existsByName(String name);
    
    @Modifying
    @Query("UPDATE ArtLevel e SET e.deleted = true WHERE e.id = ?1")
    void deleteById(Long id);
}
