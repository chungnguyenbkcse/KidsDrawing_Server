package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserReadNotification;
@Repository
public interface UserReadNotificationRepository extends JpaRepository <UserReadNotification, UUID>{
    boolean existsByUserId(UUID id);
    boolean existsByNotificationId(UUID id);
    Optional<UserReadNotification> findByNotificationId(UUID id);
    List<UserReadNotification> findByUserId(UUID id);
    void deleteById(UUID id);
}