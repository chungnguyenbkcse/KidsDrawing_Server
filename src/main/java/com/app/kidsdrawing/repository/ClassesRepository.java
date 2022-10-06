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

    @Query("SELECT DISTINCT c1 FROM Classes c1  JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges")
    List<Classes> findAll1();

    @Query("SELECT count(c.id) = 1 FROM Classes c WHERE c.userRegisterTeachSemester = :id")
    Boolean existsByUserRegisterTeachSemesterId(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester urt WHERE urt.id = :id")
    Optional<Classes> findByUserRegisterTeachSemesterId(UUID id);

    @Query("SELECT c FROM Classes c WHERE c.id = :id ")
    Optional<Classes> findById1(UUID id);

    @Query("SELECT c FROM Classes c JOIN FETCH c.user  JOIN FETCH c.userRegisterTeachSemester WHERE c.id = :id ")
    Optional<Classes> findById2(UUID id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules JOIN FETCH sc.semester s JOIN FETCH s.holidays WHERE c1.id = :id ")
    Optional<Classes> findById3(UUID id);

    @Query("SELECT DISTINCT c1 FROM Classes c1 JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s JOIN FETCH s.holidays WHERE c1.id = :id ")
    Optional<Classes> findById4(UUID id);

    @Query("SELECT c1 FROM Classes c1 JOIN FETCH c1.classHasRegisterJoinSemesterClasses  chr JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH urj.student JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH sc.course c JOIN FETCH sc.schedules sch JOIN FETCH sch.lessonTime JOIN FETCH sc.semester s JOIN FETCH s.holidays JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges WHERE c1.id = :id ")
    Optional<Classes> findById5(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user u WHERE u.id = :id")
    List<Classes> findByCreatorId1(UUID id);

    @Query("FROM Classes c JOIN FETCH c.user  u JOIN FETCH c.userRegisterTeachSemester WHERE u.id = :id")
    List<Classes> findByCreatorId2(UUID id);
}
