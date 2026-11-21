package com.pbd.esports.repository;

import com.pbd.esports.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository <Application, Long> {
}
