package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTutorialPage;

@Repository
public interface UserRegisterTutorialPageRepository extends JpaRepository <UserRegisterTutorialPage, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
    List<UserRegisterTutorialPage> findByUserRegisterTutorialId(Long id);
}
