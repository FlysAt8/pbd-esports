package com.pbd.esports.repository;

import com.pbd.esports.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query(value = "SELECT * FROM tournaments WHERE prize_pool > :minPrize", nativeQuery = true)
    List<Tournament> findRichTournaments(Double minPrize);
}