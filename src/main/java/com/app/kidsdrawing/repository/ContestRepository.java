package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("FROM Contest c JOIN FETCH c.artAges WHERE c.artAges = :artAges")
    List<Contest> findByArtAgeIdFetch(UUID artAges);

    @Query("FROM Contest c JOIN FETCH c.artTypes WHERE c.artTypes = :artTypes")
    List<Contest> findByArtTypeIdFetch(UUID artTypes);

    @Query("FROM Contest c JOIN FETCH c.user WHERE c.user = :user")
    List<Contest> findByCreatorIdFetch(UUID user);
}
