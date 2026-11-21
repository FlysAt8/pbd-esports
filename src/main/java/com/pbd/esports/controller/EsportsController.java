package com.pbd.esports.controller;

import com.pbd.esports.dto.ApplicationDto;
import com.pbd.esports.dto.TeamDto;
import com.pbd.esports.dto.TournamentDto;
import com.pbd.esports.service.ApplicationService;
import com.pbd.esports.service.TeamService;
import com.pbd.esports.service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EsportsController {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final ApplicationService applicationService;

    public EsportsController(TournamentService tournamentService,
                             TeamService teamService,
                             ApplicationService applicationService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.applicationService = applicationService;
    }

    @GetMapping("/tournaments")
    public List<TournamentDto> getTournaments(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) BigDecimal minPrize,
            @RequestParam(required = false) String startDateAfter) {
        LocalDate date = (startDateAfter != null) ? LocalDate.parse(startDateAfter) : null;
        return tournamentService.findTournamentsDynamic(title, minPrize, date);
    }

    @GetMapping("/teams/{id}/players")
    public TeamDto getTeamWithPlayers(@PathVariable Long id) {
        return teamService.findTeamWithPlayers(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    @PostMapping("/applications")
    public ApplicationDto createApplication(@RequestParam Long teamId,
                                            @RequestParam Long tournamentId) {
        return applicationService.createApplication(teamId, tournamentId);
    }
}