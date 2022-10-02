package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, UUID>{

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.id = :id")
    boolean existsByIdUsingFetch(UUID id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.name = :name")
    Boolean existsByNameUsingFetch(String name);

    void deleteById(UUID id); 
    
    @Query("FROM Classes c  JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester")
    List<Classes> findAllFetchUsingUserAndUserRegisterTeachSemester();

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.userRegisterTeachSemester = :id")
    Boolean existsByUserRegisterTeachSemesterIdUsingFetch(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.userRegisterTeachSemester = :id")
    List<Classes> findByUserRegisterTeachSemesterIdUsingFetch(UUID id);

    Optional<Classes> findById(UUID id);
}
