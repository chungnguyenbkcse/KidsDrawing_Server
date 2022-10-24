package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTutorialPage;

@Repository
public interface UserRegisterTutorialPageRepository extends JpaRepository <UserRegisterTutorialPage, UUID>{
    @Query("SELECT e FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial ")
    List<UserRegisterTutorialPage> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial ",
		countQuery = "SELECT e FROM UserRegisterTutorialPage e INNER JOIN e.userRegisterTutorial "
	)
    Page<UserRegisterTutorialPage> findAll(Pageable pageable);

    @Query("FROM UserRegisterTutorialPage e WHERE e.id = :id")
    Optional<UserRegisterTutorialPage> findById1(UUID id);

    @Query("FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial WHERE e.id = :id")
    Optional<UserRegisterTutorialPage> findById2(UUID id);

    @Query("FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial urt WHERE urt.id = :id")
    List<UserRegisterTutorialPage> findByUserRegisterTutorialId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
