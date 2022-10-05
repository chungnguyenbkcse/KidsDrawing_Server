package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterTutorial;

@Repository
public interface UserRegisterTutorialRepository extends JpaRepository <UserRegisterTutorial, UUID>{
    
    @Query("SELECT e FROM UserRegisterTutorial e JOIN FETCH e.section  JOIN FETCH e.creator ")
    List<UserRegisterTutorial> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTutorial e JOIN FETCH e.section  JOIN FETCH e.creator ",
		countQuery = "SELECT e FROM UserRegisterTutorial e INNER JOIN e.section  INNER JOIN e.creator "
	)
    Page<UserRegisterTutorial> findAll(Pageable pageable);

    @Query("FROM UserRegisterTutorial e WHERE e.id = :id")
    Optional<UserRegisterTutorial> findById1(UUID id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section  JOIN FETCH e.creator WHERE e.id = :id")
    Optional<UserRegisterTutorial> findById2(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section se WHERE se.id = :id")
    List<UserRegisterTutorial> findBySectionId1(UUID id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section se JOIN FETCH e.creator WHERE se.id = :id")
    List<UserRegisterTutorial> findBySectionId2(UUID id);
}
