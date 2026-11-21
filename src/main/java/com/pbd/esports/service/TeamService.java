package com.pbd.esports.service;

import com.pbd.esports.dto.PlayerDto;
import com.pbd.esports.dto.TeamDto;
import com.pbd.esports.entity.Team;
import com.pbd.esports.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public Optional<TeamDto> findTeamWithPlayers(Long teamId) {
        return teamRepository.findByIdWithPlayers(teamId)
                .map(team -> new TeamDto(
                        team.getId(),
                        team.getName(),
                        team.getPlayers().stream()
                                .map(p -> new PlayerDto(p.getId(), p.getNickname(), p.getRole()))
                                .collect(Collectors.toList())
                ));
    }
}