package com.app.kidsdrawing.repository;

import java.util.Optional;

import com.app.kidsdrawing.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    @Query("FROM Role e WHERE e.name = ?1")
    Optional<Role> findByName(String name);
}
