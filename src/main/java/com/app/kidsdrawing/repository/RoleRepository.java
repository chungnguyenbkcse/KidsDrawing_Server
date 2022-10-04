package com.app.kidsdrawing.repository;

import java.util.Optional;

import com.app.kidsdrawing.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{

    @Query("FROM Role e WHERE e.name = :name")
    Optional<Role> findByName(String name);
}
