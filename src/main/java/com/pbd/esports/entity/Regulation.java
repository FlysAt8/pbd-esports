package com.pbd.esports.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regulations")
@Getter
@Setter
@NoArgsConstructor
public class Regulation {

    @Id
    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "rules_text", nullable = false, columnDefinition = "TEXT")
    private String rulesText;

    @Column(name = "max_teams", nullable = false)
    private Integer maxTeams;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
}