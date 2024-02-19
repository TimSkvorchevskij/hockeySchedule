package by.bsuir.daniil.hockeySchedule.service.impl;

import by.bsuir.daniil.hockeySchedule.model.Match;
import by.bsuir.daniil.hockeySchedule.repository.MatchRepositoryDAO;
import by.bsuir.daniil.hockeySchedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchServiceImplDAO implements MatchService{

    private final MatchRepositoryDAO repository;

    @Override
    public List<Match> getAllMatches() {
        return repository.getAllMatches();
    }

    @Override
    public Match addMatch(Match newMatch) {
        return repository.addMatch(newMatch);
    }

    @Override
    public boolean deleteMatch(Match delMatch) {
        return repository.deleteMatch(delMatch);
    }

    @Override
    public List<Match> findByDate(ZonedDateTime date) {
        return repository.findByDate(date);
    }

    @Override
    public List<Match> findByTeam(String team) {
        return repository.findByTeam(team);
    }

    @Override
    public Optional<Match> findById(Integer id) {
        return null;
    }
}
