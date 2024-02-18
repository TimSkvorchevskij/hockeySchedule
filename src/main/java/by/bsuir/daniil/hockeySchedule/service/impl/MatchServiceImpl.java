package by.bsuir.daniil.hockeySchedule.service.impl;

import by.bsuir.daniil.hockeySchedule.model.Match;
import by.bsuir.daniil.hockeySchedule.repository.MatchRepository;
import by.bsuir.daniil.hockeySchedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchRepository repository;

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
    public Match findById(Integer id) {
        return repository.findById(id);
    }
}
