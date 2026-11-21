package com.pbd.esports.dto;

import java.time.LocalDateTime;

public record ApplicationDto(
        Long id,
        String teamName,
        String tournamentTitle,
        String status,
        Integer seedNumber,
        LocalDateTime appliedAt
) {}