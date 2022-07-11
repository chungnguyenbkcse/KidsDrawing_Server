package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kidsdrawing.entity.ArtLevel;

public interface ArtLevelRepository extends JpaRepository <ArtLevel, Long>{
    Page<ArtLevel> findAll(Pageable pageable);
    Optional<ArtLevel> findByName(String name);
    boolean existsById(Long id);
    Boolean existsByName(String name);
    void deleteById(Long id);
}
