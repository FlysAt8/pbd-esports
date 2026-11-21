package com.pbd.esports.dto;

import java.util.List;

public record TeamDto(
        Long id,
        String name,
        List<PlayerDto> players
) {}