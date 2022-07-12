package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ArtType;

@Repository
public interface ArtTypeRepository extends JpaRepository <ArtType, Long>{
    Page<ArtType> findAll(Pageable pageable);
    Optional<ArtType> findByName(String name);
    boolean existsById(Long id);
    Boolean existsByName(String name);
    void deleteById(Long id);
}
