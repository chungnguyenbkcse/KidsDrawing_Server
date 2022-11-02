package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserReadNotification;
@Repository
public interface UserReadNotificationRepository extends JpaRepository <UserReadNotification, Long>{
   
    @Query("SELECT e FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification ORDER BY e.id")
    List<UserReadNotification> findAll();

    @Query(
		value = "SELECT e FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification ORDER BY e.id",
		countQuery = "SELECT e FROM UserReadNotification e INNER JOIN e.user  INNER JOIN e.notification ORDER BY e.id"
	)
    Page<UserReadNotification> findAll(Pageable pageable);

    @Query("FROM UserReadNotification e WHERE e.id = ?1")
    Optional<UserReadNotification> findById1(Long id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user  JOIN FETCH e.notification WHERE e.id = ?1")
    Optional<UserReadNotification> findById2(Long id);
    
    boolean existsByUserId(Long id);
    boolean existsByNotificationId(Long id);

    @Query("FROM UserReadNotification e JOIN FETCH e.notification no WHERE no.id = ?1")
    List<UserReadNotification> findByNotificationId1(Long id);

    @Query("FROM UserReadNotification e JOIN FETCH e.notification no JOIN FETCH e.user  WHERE no.id = ?1")
    List<UserReadNotification> findByNotificationId2(Long id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user u WHERE u.id = ?1")
    List<UserReadNotification> findByUserId1(Long id);

    @Query("FROM UserReadNotification e JOIN FETCH e.user u JOIN FETCH e.notification WHERE u.id = ?1")
    List<UserReadNotification> findByUserId2(Long id);

    void deleteById(Long id);
}