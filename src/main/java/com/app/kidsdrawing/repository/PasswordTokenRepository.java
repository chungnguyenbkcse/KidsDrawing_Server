package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.PasswordResetToken;

@Repository
public interface PasswordTokenRepository extends JpaRepository <PasswordResetToken, UUID>{

    @Query("SELECT e FROM PasswordResetToken e JOIN FETCH e.user ORDER BY e.id")
    List<PasswordResetToken> findAll();

    @Query(
		value = "SELECT e FROM PasswordResetToken e JOIN FETCH e.user ORDER BY e.id",
		countQuery = "SELECT e FROM PasswordResetToken e INNER JOIN e.user ORDER BY e.id"
	)
    Page<PasswordResetToken> findAll(Pageable pageable);

    @Query("FROM PasswordResetToken e JOIN FETCH e.user WHERE e.id = :id")
    Optional<PasswordResetToken> findById(UUID id);
    
    @Query("FROM PasswordResetToken e JOIN FETCH e.user WHERE e.token = :token")
    Optional<PasswordResetToken> findByToken(String token);

    @Query("FROM PasswordResetToken e JOIN FETCH e.user WHERE e.user = :id")
    List<PasswordResetToken> findByUserId(UUID id);
}
