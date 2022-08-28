package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
@Repository
public interface UserRegisterTeachSemesterRepository extends JpaRepository <UserRegisterTeachSemester, Long> {
    boolean existsById(Long id);
    void deleteById(Long id);
    List<UserRegisterTeachSemester> findBySemesterClassId(Long id);
}
