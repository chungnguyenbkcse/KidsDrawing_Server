package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.TutorialPage;

@Repository
public interface TutorialPageRepository extends JpaRepository <TutorialPage, Long>{
    @Query("SELECT e FROM TutorialPage e JOIN FETCH e.tutorial ")
    List<TutorialPage> findAll();

    @Query(
		value = "SELECT e FROM TutorialPage e JOIN FETCH e.tutorial ",
		countQuery = "SELECT e FROM TutorialPage e INNER JOIN e.tutorial "
	)
    Page<TutorialPage> findAll(Pageable pageable);

    @Query("FROM TutorialPage e WHERE e.id = :id")
    Optional<TutorialPage> findById1(Long id);

    @Query("FROM TutorialPage e JOIN FETCH e.tutorial WHERE e.id = :id")
    Optional<TutorialPage> findById2(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("FROM TutorialPage e JOIN FETCH e.tutorial t WHERE t.id = :id")
    List<TutorialPage> findByTutorialId(Long id);

    @Query("FROM TutorialPage e JOIN FETCH e.tutorial t JOIN FETCH t.section s WHERE s.id = :id")
    List<TutorialPage> findBySection(Long id);
}