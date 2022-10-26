package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository <Section, Long>{
    @Query("SELECT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher")
    List<Section> findAll();

    @Query(
		value = "SELECT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher",
		countQuery = "SELECT e FROM Section e INNER JOIN e.classes cl INNER JOIN cl.userRegisterTeachSemester urt INNER JOIN urt.teacher"
	)
    Page<Section> findAll(Pageable pageable);

    @Query("FROM Section e WHERE e.id = :id")
    Optional<Section> findById1(Long id);

    @Query("FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher WHERE e.id = :id")
    Optional<Section> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher WHERE cl.id = :id ")
    List<Section> findByClassesId(Long id);

    boolean existsById(Long id);
    void deleteById(Long id);
}