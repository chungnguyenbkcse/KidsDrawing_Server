package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID>{
    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent ORDER BY e.id")
    List<User> findAll();

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent LEFT JOIN FETCH e.roles ORDER BY e.id")
    List<User> findAllFetchRole();

    @Query(
		value = "SELECT e FROM User e LEFT JOIN FETCH e.parent ORDER BY e.id",
		countQuery = "SELECT e FROM User e LEFT JOIN e.parent ORDER BY e.id"
	)
    Page<User> findAll(Pageable pageable);

    @Query("SELECT e FROM User e WHERE e.id = :id")
    Optional<User> findById1(UUID id);

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent WHERE e.id = :id")
    Optional<User> findById2(UUID id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.username = :username")
    Boolean existsByUsername(String username);

    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.email = :email")
    Boolean existsByEmail(String email);

    @Query("FROM User e WHERE e.username = ?1 OR e.email = ?2")
    Optional<User> findByUsernameOrEmail1(String username, String email);

    @Query("FROM User e LEFT JOIN FETCH e.parent WHERE e.username = ?1 OR e.email = ?2")
    Optional<User> findByUsernameOrEmail2(String username, String email);

    @Query("SELECT e FROM User e WHERE e.username = ?1")
    Optional<User> findByUsername1(String username);

    @Query("SELECT e FROM User e LEFT JOIN FETCH e.parent WHERE e.username = ?1")
    Optional<User> findByUsername2(String username);

    @Query("FROM User e WHERE e.email = :email")
    Optional<User> findByEmail1(String email);

    @Query("FROM User e LEFT JOIN FETCH e.parent WHERE e.email = :email")
    Optional<User> findByEmail2(String email);

    @Query("FROM User e JOIN FETCH e.parent WHERE e.parent = :id")
    List<User> findByParentId(UUID id);
}
