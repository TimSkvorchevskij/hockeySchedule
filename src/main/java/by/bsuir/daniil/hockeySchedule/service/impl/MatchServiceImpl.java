package by.bsuir.daniil.hockeySchedule.service.impl;

import by.bsuir.daniil.hockeySchedule.model.Match;
import by.bsuir.daniil.hockeySchedule.repository.MatchRepository;
import by.bsuir.daniil.hockeySchedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;
    @Override
    public List<Match> getAllMatches() {
        return repository.findAll();
    }

    @Override
    public Match addMatch(Match newMatch) {
        return repository.save(newMatch);
    }

    @Override
    public boolean deleteMatch(Match delMatch) {
        repository.delete(delMatch);
        return true;
    }

    @Override
    public List<Match> findByDate(ZonedDateTime date) {
        return repository.findAll();
    }

    @Override
    public List<Match> findByTeam(String team) {
        return repository.findByAwayTeamOrHostTeam(team,team);
    }

    @Override
    public Optional<Match> findById(Integer id) {
        return repository.findById(id);
    }
}
