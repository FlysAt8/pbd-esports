package com.pbd.esports.service;

import com.pbd.esports.dto.TournamentDto;
import com.pbd.esports.entity.Tournament;
import com.pbd.esports.repository.TournamentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional(readOnly = true)
    public List<TournamentDto> findTournamentsDynamic(String title, BigDecimal minPrize, LocalDate startDateAfter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tournament> cq = cb.createQuery(Tournament.class);
        Root<Tournament> root = cq.from(Tournament.class);
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (minPrize != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("prizePool"), minPrize));
        }
        if (startDateAfter != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDateAfter));
        }
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<Tournament> tournaments = entityManager.createQuery(cq).getResultList();

        // Преобразуем в DTO
        return tournaments.stream()
                .map(t -> new TournamentDto(t.getId(), t.getTitle(), t.getStartDate(), t.getPrizePool()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TournamentDto> findAll() {
        return tournamentRepository.findAll().stream()
                .map(t -> new TournamentDto(t.getId(), t.getTitle(), t.getStartDate(), t.getPrizePool()))
                .collect(Collectors.toList());
    }
}