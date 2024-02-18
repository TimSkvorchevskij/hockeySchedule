package by.bsuir.daniil.hockeySchedule.service;

import by.bsuir.daniil.hockeySchedule.model.Match;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();
    Match addMatch(Match newMatch);

    boolean deleteMatch(Match delMatch);
    List<Match> findByDate(ZonedDateTime date);

    List<Match> findByTeam(String team);

    Match findById(Integer id);
}
