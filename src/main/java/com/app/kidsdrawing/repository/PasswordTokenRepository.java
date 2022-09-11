package com.app.kidsdrawing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.PasswordResetToken;

@Repository
public interface PasswordTokenRepository extends JpaRepository <PasswordResetToken, Long>{
    Optional<PasswordResetToken> findByToken(String token);
}
