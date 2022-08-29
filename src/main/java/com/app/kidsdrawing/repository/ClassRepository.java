package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Class;

@Repository
public interface ClassRepository extends JpaRepository <Class, Long>{
    boolean existsById(Long id);
    Boolean existsByName(String name);
    void deleteById(Long id);
    Boolean existsByUserRegisterTeachSemester(Long id);
    Class findByUserRegisterTeachSemester(Long id);
}
