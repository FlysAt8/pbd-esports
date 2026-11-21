package com.pbd.esports.repository;

import com.pbd.esports.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = :id")
    Optional<Team> findByIdWithPlayers(Long id);
}
