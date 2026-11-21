package com.pbd.esports.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_seq")
    @SequenceGenerator(name = "tournament_seq", sequenceName = "tournaments_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "prize_pool")
    private BigDecimal prizePool;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.PERSIST)
    private List<Application> applications = new ArrayList<>();

    @OneToOne(mappedBy = "tournament", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Regulation regulation;

    public void setRegulation(Regulation regulation) {
        if (regulation != null) {
            regulation.setTournament(this);
        }
        this.regulation = regulation;
    }
}