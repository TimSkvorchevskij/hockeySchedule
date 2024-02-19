package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.model.Match;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchService {

    List<Match> getAllMatches();
    Match addMatch(Match newMatch);

    String deleteMatch(Integer delMatch_id);
    List<Match> findByDate(ZonedDateTime date);

    List<Match> findByTeam(String team);

    Optional<Match> findById(Integer id);
}
