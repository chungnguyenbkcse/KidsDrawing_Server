package com.app.kidsdrawing.repository;

import java.util.Optional;

import com.app.kidsdrawing.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Optional<Role> findByName(String name);
}
