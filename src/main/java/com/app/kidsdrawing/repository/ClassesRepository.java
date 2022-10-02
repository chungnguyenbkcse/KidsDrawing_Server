package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository <Classes, UUID>{

    @Query("SELECT count(c.id) = 1 FROM classes c LEFT JOIN FETCH c.creator_id LEFT JOIN FETCH c.user_register_teach_semester_id WHERE c.id = :id")
    boolean existsById(UUID id);

    @Query("SELECT count(c.id) = 1 FROM classes c LEFT JOIN FETCH c.creator_id LEFT JOIN FETCH c.user_register_teach_semester_id WHERE c.name = :name")
    Boolean existsByName(String name);

    void deleteById(UUID id); 
    
    @Query("FROM classes c LEFT JOIN FETCH c.creator_id LEFT JOIN FETCH c.user_register_teach_semester_id")
    List<Classes> findAllUsingFetch();

    @Query("SELECT count(c.id) = 1 FROM classes c LEFT JOIN FETCH c.creator_id WHERE c.user_register_teach_semester_id = :id")
    Boolean existsByUserRegisterTeachSemesterIdUsingFetch(UUID id);

    @Query("FROM classes c LEFT JOIN FETCH c.creator_id WHERE c.user_register_teach_semester_id = :id")
    Optional<Classes> findByUserRegisterTeachSemesterIdUsingFetch(UUID id);

    @Query("FROM classes c LEFT JOIN FETCH c.creator_id LEFT JOIN FETCH c.user_register_teach_semester_id WHERE c.id = :id")
    Optional<Classes> findByIdUsingFetch(UUID id);
}
