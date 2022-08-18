package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.AnonymousNotification;

@Repository
public interface AnonymousNotificationRepository extends JpaRepository <AnonymousNotification, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
    void deleteByNotificationId(Long id);
    Optional<AnonymousNotification> findByNotificationId(Long id);
}
