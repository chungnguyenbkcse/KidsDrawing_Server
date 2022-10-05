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

    @Query("SELECT e FROM PasswordResetToken e JOIN FETCH e.user ")
    List<PasswordResetToken> findAll();

    @Query(
		value = "SELECT e FROM PasswordResetToken e JOIN FETCH e.user ",
		countQuery = "SELECT e FROM PasswordResetToken e INNER JOIN e.user "
	)
    Page<PasswordResetToken> findAll(Pageable pageable);

    @Query("FROM PasswordResetToken e WHERE e.id = :id")
    Optional<PasswordResetToken> findById1(UUID id);

    @Query("FROM PasswordResetToken e JOIN FETCH e.user WHERE e.id = :id")
    Optional<PasswordResetToken> findById2(UUID id);
    
    @Query("FROM PasswordResetToken e WHERE e.token = :token")
    Optional<PasswordResetToken> findByToken1(String token);

    @Query("FROM PasswordResetToken e JOIN FETCH e.user WHERE e.token = :token")
    Optional<PasswordResetToken> findByToken2(String token);

    @Query("FROM PasswordResetToken e JOIN FETCH e.user u WHERE u.id = :id")
    List<PasswordResetToken> findByUserId(UUID id);
}
