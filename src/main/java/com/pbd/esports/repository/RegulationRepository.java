package com.pbd.esports.repository;

import com.pbd.esports.entity.Regulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulationRepository extends JpaRepository <Regulation, Long> {
}
