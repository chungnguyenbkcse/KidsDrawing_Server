package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTutorialPage;

@Repository
public interface UserRegisterTutorialPageRepository extends JpaRepository <UserRegisterTutorialPage, UUID>{
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<UserRegisterTutorialPage> findByUserRegisterTutorialId(UUID id);
}
