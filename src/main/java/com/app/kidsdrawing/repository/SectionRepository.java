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
    @Query("SELECT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user WHERE (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<Section> findAll();

    @Query(
		value = "SELECT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user WHERE (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id",
		countQuery = "SELECT e FROM Section e INNER JOIN e.classes cl INNER JOIN cl.teacher te INNER JOIN te.user WHERE (cl.deleted = FALSE OR cl.deleted IS NULL)"
	)
    Page<Section> findAll(Pageable pageable);

    @Query("FROM Section e WHERE e.id = ?1")
    Optional<Section> findById1(Long id);

    @Query("FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user WHERE e.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL)")
    Optional<Section> findById2(Long id);

    @Query("SELECT DISTINCT e FROM Section e JOIN FETCH e.classes cl JOIN FETCH cl.teacher te JOIN FETCH te.user WHERE cl.id = ?1 AND (cl.deleted = FALSE OR cl.deleted IS NULL) ORDER BY e.id")
    List<Section> findByClassesId(Long id);

    boolean existsById(Long id);
    void deleteById(Long id);

    @Query("SELECT e FROM Section e JOIN FETCH  e.classes c JOIN FETCH c.semesterClass sc JOIN FETCH sc.course cou WHERE e.id = ?1 AND (cou.deleted = FALSE OR cou.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL) AND (sc.deleted = FALSE OR sc.deleted IS NULL) AND (c.deleted = FALSE OR c.deleted IS NULL)")
    Optional<Section> findById7(Long id);
}