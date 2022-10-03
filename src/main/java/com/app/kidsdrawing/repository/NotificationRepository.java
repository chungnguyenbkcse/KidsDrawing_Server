package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, UUID>{

    @Query("SELECT e FROM Notification e ORDER BY e.time")
    List<Notification> findAll();

    @Query(
		value = "SELECT e FROM Notification e ORDER BY e.time",
		countQuery = "SELECT e FROM Notification e ORDER BY e.time"
	)
    Page<Notification> findAll(Pageable pageable);

    @Query("FROM Notification e WHERE e.id = :id")
    Optional<Notification> findById(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
