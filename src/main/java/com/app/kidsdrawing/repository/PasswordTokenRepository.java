package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.PasswordResetToken;

@Repository
public interface PasswordTokenRepository extends JpaRepository <PasswordResetToken, UUID>{
    Optional<PasswordResetToken> findByToken(String token);
}
