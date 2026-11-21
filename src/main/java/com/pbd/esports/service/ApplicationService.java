package com.pbd.esports.service;

import com.pbd.esports.dto.ApplicationDto;
import com.pbd.esports.entity.Application;
import com.pbd.esports.entity.Team;
import com.pbd.esports.entity.Tournament;
import com.pbd.esports.repository.ApplicationRepository;
import com.pbd.esports.repository.TeamRepository;
import com.pbd.esports.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              TeamRepository teamRepository,
                              TournamentRepository tournamentRepository) {
        this.applicationRepository = applicationRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public ApplicationDto createApplication(Long teamId, Long tournamentId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow();

        Application app = new Application();
        app.setTeam(team);
        app.setTournament(tournament);
        app.setStatus("PENDING");

        Application saved = applicationRepository.save(app);

        return new ApplicationDto(
                saved.getId(),
                saved.getTeam().getName(),
                saved.getTournament().getTitle(),
                saved.getStatus(),
                saved.getSeedNumber(),
                saved.getAppliedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }
}