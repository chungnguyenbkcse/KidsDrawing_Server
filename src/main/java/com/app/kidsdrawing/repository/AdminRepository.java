package com.app.kidsdrawing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.kidsdrawing.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Long>{

}
