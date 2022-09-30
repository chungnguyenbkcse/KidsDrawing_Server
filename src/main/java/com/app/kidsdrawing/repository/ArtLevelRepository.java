package com.app.kidsdrawing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ArtLevel;
@Repository
public interface ArtLevelRepository extends JpaRepository <ArtLevel, UUID>{
    Page<ArtLevel> findAll(Pageable pageable);
    Optional<ArtLevel> findByName(String name);
    boolean existsById(UUID id);
    Boolean existsByName(String name);
    void deleteById(UUID id);
}
