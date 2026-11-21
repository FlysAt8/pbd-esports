package com.pbd.esports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TournamentDto(
        Long id,
        String title,
        LocalDate startDate,
        BigDecimal prizePool
) {}