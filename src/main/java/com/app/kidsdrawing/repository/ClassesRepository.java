package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, UUID>{
    boolean existsById(UUID id);
    Boolean existsByName(String name);
    void deleteById(UUID id);
    Boolean existsByUserRegisterTeachSemesterId(UUID id);
    Classes findByUserRegisterTeachSemesterId(UUID id);
}
