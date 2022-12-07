package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    @Query("SELECT e FROM User e  WHERE (e.deleted = FALSE OR e.deleted IS NULL)  ORDER BY e.id")
    List<User> findAll();

    @Query("SELECT COUNT(e.id) FROM User e  WHERE e.authorization = 'STUDENT' AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    int findAll1();

    @Query("SELECT COUNT(e.id) FROM User e  WHERE e.authorization = 'PARENT' AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    int findAll2();

    @Query("SELECT COUNT(e.id) FROM User e  WHERE e.authorization = 'TEACHER' AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    int findAll3();

    @Query("SELECT e FROM User e WHERE e.authorization = 'STUDENT' AND (e.deleted = FALSE OR e.deleted IS NULL)  ORDER BY e.id")
    List<User> findAllStudent();

    @Query("SELECT e FROM User e WHERE e.authorization = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL)  ORDER BY e.id")
    List<User> findAllUserByRole(String role_name);

    @Query("SELECT e FROM User e WHERE (e.deleted = FALSE OR e.deleted IS NULL)  ORDER BY e.id")
    List<User> findAllFetchRole();

    @Query(
		value = "SELECT e FROM User e WHERE (e.deleted = FALSE OR e.deleted IS NULL) ",
		countQuery = "SELECT e FROM User e  WHERE (e.deleted = FALSE OR e.deleted IS NULL) "
	)
    Page<User> findAll(Pageable pageable);

    @Query("SELECT e FROM User e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findById1(Long id);


    @Query("SELECT e FROM User e WHERE e.id = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findById2(Long id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.username = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Boolean existsByUsername(String username);

    @Query("SELECT COUNT(e.id) = 1 FROM User e WHERE e.email = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Boolean existsByEmail(String email);

    @Query("FROM User e WHERE (e.username = ?1 OR e.email = ?2) AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findByUsernameOrEmail1(String username, String email);

    @Query("FROM User e WHERE (e.username = ?1 OR e.email = ?2) AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findByUsernameOrEmail2(String username, String email);

    @Query("SELECT e FROM User e WHERE e.username = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findByUsername1(String username);

    @Query("SELECT e FROM User e WHERE e.username = ?1 AND (e.deleted = FALSE OR e.deleted IS NULL) ")
    Optional<User> findByUsername2(String username);

    @Query("FROM User e WHERE e.email = ?1")
    Optional<User> findByEmail1(String email);

    @Modifying
    @Query("UPDATE User e SET e.deleted = TRUE WHERE e.id = ?1")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE User e SET e.deleted = FALSE WHERE e.id = ?1")
    void restoreById(Long id);

    @Modifying
    @Query("UPDATE User e SET e.deleted = FALSE WHERE e.username = ?1")
    void restoreByUsername(String username);
}
