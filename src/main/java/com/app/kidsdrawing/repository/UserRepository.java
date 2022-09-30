package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID>{
    Page<User> findAll(Pageable pageable);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByParentId(UUID id);
}
