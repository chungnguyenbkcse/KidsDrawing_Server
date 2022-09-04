package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserGradeContestSubmission;

@Repository
public interface UserGradeContestSubmissionRepository extends JpaRepository <UserGradeContestSubmission, Long>{
    boolean existsById(Long id);
    boolean existsByContestSubmissionId(Long id);
    void deleteById(Long id);
    List<UserGradeContestSubmission> findByTeacherId(Long id);
}
