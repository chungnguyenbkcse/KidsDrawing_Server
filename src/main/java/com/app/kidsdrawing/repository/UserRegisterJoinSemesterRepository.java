package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterJoinSemester;

@Repository
public interface UserRegisterJoinSemesterRepository extends JpaRepository <UserRegisterJoinSemester, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<UserRegisterJoinSemester> findBySemesterClassId(UUID id);
    List<UserRegisterJoinSemester> findByStudentId(UUID id);
    List<UserRegisterJoinSemester> findByPayerId(UUID id);
}