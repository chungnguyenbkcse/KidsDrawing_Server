package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserGradeContestSubmission;

@Repository
public interface UserGradeContestSubmissionRepository extends JpaRepository <UserGradeContestSubmission, UUID>{
    boolean existsById(UUID id);
    boolean existsByContestSubmissionId(UUID id);
    void deleteById(UUID id);
    List<UserGradeContestSubmission> findByTeacherId(UUID id);
}
