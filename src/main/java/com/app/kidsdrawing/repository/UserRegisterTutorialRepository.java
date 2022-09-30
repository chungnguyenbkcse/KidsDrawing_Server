package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTutorial;

@Repository
public interface UserRegisterTutorialRepository extends JpaRepository <UserRegisterTutorial, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<UserRegisterTutorial> findBySectionId(UUID id);
}
