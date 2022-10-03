package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;

@Repository
public interface ClassHasRegisterJoinSemesterClassRepository extends JpaRepository <ClassHasRegisterJoinSemesterClass, UUID>{
    @Query("SELECT count(c.id) = 1 FROM ClassHasRegisterJoinSemesterClass c  WHERE c.id = :id")
    boolean existsById(UUID id);

    void deleteById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  JOIN FETCH c.userRegisterJoinSemester")
    List<ClassHasRegisterJoinSemesterClass> findAll();

    @Query("SELECT c FROM ClassHasRegisterJoinSemesterClass c JOIN FETCH c.classes  JOIN FETCH c.userRegisterJoinSemester WHERE c.id = :id ")
    Optional<ClassHasRegisterJoinSemesterClass> findById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("SELECT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.userRegisterJoinSemester JOIN FETCH c.classes WHERE c.classes = ?1 AND c.userRegisterJoinSemester = ?2")
    Optional<ClassHasRegisterJoinSemesterClass> findByClassIdAndUserRegisterJoinSemester(UUID class_id, UUID user_register_join_semester_id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.userRegisterJoinSemester JOIN FETCH c.classes WHERE c.userRegisterJoinSemester = :id")
    List<ClassHasRegisterJoinSemesterClass> findByUserRegisterJoinSemesterId(UUID id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  JOIN FETCH c.userRegisterJoinSemester WHERE c.classes = :id")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId(UUID id);
}
