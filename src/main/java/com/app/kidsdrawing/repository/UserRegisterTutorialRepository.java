package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTutorial;

@Repository
public interface UserRegisterTutorialRepository extends JpaRepository <UserRegisterTutorial, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
    List<UserRegisterTutorial> findBySectionId(Long id);
}
