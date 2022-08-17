package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
}
