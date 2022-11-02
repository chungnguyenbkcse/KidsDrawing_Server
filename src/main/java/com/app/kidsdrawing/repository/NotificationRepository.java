package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long>{

    @Query("SELECT e FROM Notification e ORDER BY e.id")
    List<Notification> findAll();

    @Query(
		value = "SELECT e FROM Notification e ORDER BY e.id",
		countQuery = "SELECT e FROM Notification e "
	)
    Page<Notification> findAll(Pageable pageable);

    @Query("FROM Notification e WHERE e.id = ?1")
    Optional<Notification> findById(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);
}
