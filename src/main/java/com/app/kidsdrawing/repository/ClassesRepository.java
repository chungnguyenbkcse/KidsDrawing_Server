package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
    void deleteById(Long id);
    Boolean existsByUserRegisterTeachSemesterId(Long id);
    Classes findByUserRegisterTeachSemesterId(Long id);
}
