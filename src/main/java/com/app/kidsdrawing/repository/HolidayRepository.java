package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository <Holiday, Long>{
    boolean existsById(Long id);
    void deleteById(Long id);
}