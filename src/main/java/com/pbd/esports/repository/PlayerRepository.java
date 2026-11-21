package com.pbd.esports.repository;

import com.pbd.esports.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.team.name = :teamName")
    List<Player> findPlayersByTeamName(String teamName);
}