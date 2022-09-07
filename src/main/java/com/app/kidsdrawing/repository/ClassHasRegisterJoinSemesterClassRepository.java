package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;

@Repository
public interface ClassHasRegisterJoinSemesterClassRepository extends JpaRepository <ClassHasRegisterJoinSemesterClass, Long>{
    boolean existsById(Long id);
    void deleteById(ClassHasRegisterJoinSemesterClassKey id);
    Optional<ClassHasRegisterJoinSemesterClass> findById(ClassHasRegisterJoinSemesterClassKey id);
    Boolean existsByUserRegisterJoinSemesterId(Long id);
    ClassHasRegisterJoinSemesterClass findByUserRegisterJoinSemesterId(Long id);
    List<ClassHasRegisterJoinSemesterClass> findByClassesId(Long id);
}
