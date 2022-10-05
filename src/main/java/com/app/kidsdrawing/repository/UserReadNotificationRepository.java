package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserReadNotification;
@Repository
public interface UserReadNotificationRepository extends JpaRepository <UserReadNotification, UUID>{
   
    @Query("SELECT e FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification ORDER BY e.id")
    List<UserReadNotification> findAll();

    @Query(
		value = "SELECT e FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification ORDER BY e.id",
		countQuery = "SELECT e FROM UserReadNotification e INNER JOIN e.user  INNER JOIN e.notification ORDER BY e.id"
	)
    Page<UserReadNotification> findAll(Pageable pageable);

    @Query("FROM UserReadNotification e WHERE e.id = :id")
    Optional<UserReadNotification> findById1(UUID id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification WHERE e.id = :id")
    Optional<UserReadNotification> findById2(UUID id);
    
    boolean existsByUserId(UUID id);
    boolean existsByNotificationId(UUID id);

    @Query("FROM UserReadNotification e JOIN FETCH e.notification no WHERE no.id = :id")
    List<UserReadNotification> findByNotificationId1(UUID id);

    @Query("FROM UserReadNotification e JOIN FETCH e.notification no JOIN FETCH e.user  WHERE no.id = :id")
    List<UserReadNotification> findByNotificationId2(UUID id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user u WHERE u.id = :id")
    List<UserReadNotification> findByUserId1(UUID id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user u JOIN FETCH e.notification WHERE u.id = :id")
    List<UserReadNotification> findByUserId2(UUID id);

    void deleteById(UUID id);
}