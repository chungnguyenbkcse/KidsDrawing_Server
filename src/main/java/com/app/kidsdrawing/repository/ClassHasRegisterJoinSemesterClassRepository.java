package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;

@Repository
public interface ClassHasRegisterJoinSemesterClassRepository extends JpaRepository <ClassHasRegisterJoinSemesterClass, UUID>{
    boolean existsById(UUID id);
    void deleteById(ClassHasRegisterJoinSemesterClassKey id);
    Optional<ClassHasRegisterJoinSemesterClass> findById(ClassHasRegisterJoinSemesterClassKey id);
    Boolean existsByUserRegisterJoinSemesterId(UUID id);
    ClassHasRegisterJoinSemesterClass findByUserRegisterJoinSemesterId(UUID id);
    List<ClassHasRegisterJoinSemesterClass> findByClassesId(UUID id);
}
