package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
@Repository
public interface UserRegisterTeachSemesterRepository extends JpaRepository <UserRegisterTeachSemester, UUID> {
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<UserRegisterTeachSemester> findBySemesterClassId(UUID id);
}
