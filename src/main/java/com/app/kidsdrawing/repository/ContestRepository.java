package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository <Contest, UUID>{
    Page<Contest> findAll(Pageable pageable);
    Optional<Contest> findByName(String name);
    boolean existsById(UUID id);
    Boolean existsByName(String name);
    void deleteById(UUID id);
}
