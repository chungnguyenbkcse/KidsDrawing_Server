package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTutorial;

@Repository
public interface UserRegisterTutorialRepository extends JpaRepository <UserRegisterTutorial, Long>{
    
    @Query("SELECT e FROM UserRegisterTutorial e JOIN FETCH e.section  se JOIN FETCH e.creator JOIN FETCH se.classes ORDER BY e.id")
    List<UserRegisterTutorial> findAll();

    @Query("SELECT DISTINCT e FROM UserRegisterTutorial e JOIN FETCH e.section  se JOIN FETCH e.creator JOIN FETCH se.classes ORDER BY e.id")
    List<UserRegisterTutorial> findAll1();

    @Query(
		value = "SELECT e FROM UserRegisterTutorial e JOIN FETCH e.section  se JOIN FETCH e.creator JOIN FETCH se.classes ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterTutorial e INNER JOIN e.section  se INNER JOIN e.creator INNER JOIN se.classes"
	)
    Page<UserRegisterTutorial> findAll(Pageable pageable);

    @Query("FROM UserRegisterTutorial e WHERE e.id = ?1")
    Optional<UserRegisterTutorial> findById1(Long id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section se JOIN FETCH e.creator JOIN FETCH se.classes WHERE e.id = ?1")
    Optional<UserRegisterTutorial> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section se WHERE se.id = ?1 ORDER BY e.id")
    List<UserRegisterTutorial> findBySectionId1(Long id);

    @Query("FROM UserRegisterTutorial e JOIN FETCH e.section se JOIN FETCH e.creator JOIN FETCH se.classes WHERE se.id = ?1 ORDER BY e.id")
    List<UserRegisterTutorial> findBySectionId2(Long id);
}
