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
    boolean existsById(UUID id);

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.name = :name")
    Boolean existsByName(String name);

    void deleteById(UUID id); 
    
    @Query("FROM Classes c  JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester")
    List<Classes> findAll();

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.userRegisterTeachSemester = :id")
    Boolean existsByUserRegisterTeachSemesterId(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.userRegisterTeachSemester = :id")
    List<Classes> findByUserRegisterTeachSemesterId(UUID id);

    @Query("SELECT c FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.id = :id ")
    Optional<Classes> findById(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.user = :id")
    List<Classes> findByCreatorId(UUID id);
}
