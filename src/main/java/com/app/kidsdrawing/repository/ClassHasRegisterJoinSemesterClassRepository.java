package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;

@Repository
public interface ClassHasRegisterJoinSemesterClassRepository extends JpaRepository <ClassHasRegisterJoinSemesterClass, Long>{
    @Query("SELECT count(c.id) = 1 FROM ClassHasRegisterJoinSemesterClass c  WHERE c.id = ?1")
    boolean existsById(Long id);

    void deleteById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  JOIN FETCH c.userRegisterJoinSemester")
    List<ClassHasRegisterJoinSemesterClass> findAll();

    @Query("SELECT DISTINCT chr FROM ClassHasRegisterJoinSemesterClass chr  JOIN FETCH chr.classes c1 JOIN FETCH chr.userRegisterJoinSemester urj JOIN FETCH c1.user  JOIN FETCH c1.userRegisterTeachSemester u JOIN FETCH u.semesterClass sc JOIN FETCH u.teacher JOIN FETCH sc.semester s JOIN FETCH sc.course c JOIN FETCH c.artLevels JOIN FETCH c.artTypes JOIN FETCH c.artAges")
    List<ClassHasRegisterJoinSemesterClass> findAll1();

    @Query("SELECT c FROM ClassHasRegisterJoinSemesterClass c JOIN FETCH c.classes  WHERE c.id = ?1 ")
    Optional<ClassHasRegisterJoinSemesterClass> findById(ClassHasRegisterJoinSemesterClassKey id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.userRegisterJoinSemester urj JOIN FETCH c.classes cl WHERE cl.id = ?1 AND urj = ?2")
    Optional<ClassHasRegisterJoinSemesterClass> findByClassIdAndUserRegisterJoinSemester(Long class_id, Long user_register_join_semester_id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.userRegisterJoinSemester urj WHERE urj.id = ?1")
    Optional<ClassHasRegisterJoinSemesterClass> findByUserRegisterJoinSemesterId1(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.userRegisterJoinSemester urj JOIN FETCH c.classes cl WHERE urj.id = ?1")
    Optional<ClassHasRegisterJoinSemesterClass> findByUserRegisterJoinSemesterId2(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl WHERE cl.id = ?1")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId1(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl JOIN FETCH c.userRegisterJoinSemester WHERE cl.id = ?1")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId2(Long id);

    @Query("SELECT DISTINCT c FROM ClassHasRegisterJoinSemesterClass c  JOIN FETCH c.classes  cl JOIN FETCH c.userRegisterJoinSemester u JOIN FETCH u.student st JOIN FETCH st.parent WHERE cl.id = ?1")
    List<ClassHasRegisterJoinSemesterClass> findByClassesId3(Long id);
}
