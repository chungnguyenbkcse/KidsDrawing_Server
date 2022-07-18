package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterJoinSemester;

@Repository
public interface UserRegisterJoinSemesterRepository extends JpaRepository <UserRegisterJoinSemester, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
}