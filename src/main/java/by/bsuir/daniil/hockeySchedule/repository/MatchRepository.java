package by.bsuir.daniil.hockeySchedule.repository;

import by.bsuir.daniil.hockeySchedule.model.Match;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class MatchRepository {
    private final List<Match> schedule = new ArrayList<>();

    public List<Match> getAllMatches(){
        return schedule;
    }

    public Match addMatch(Match newMatch) {
        schedule.add(newMatch);
        return newMatch;
    }

    public boolean deleteMatch(Match delMatch) {
        return schedule.remove(delMatch);
    }

    public List<Match> findByDate(ZonedDateTime date) {
        List<Match> matches = new ArrayList<>();
        for (Match tmp : schedule) {
            if (tmp.getDateTime().getYear() == date.getYear() &&
                    tmp.getDateTime().getMonthValue() == date.getMonthValue() &&
                    tmp.getDateTime().getDayOfYear() == date.getDayOfYear()) {
                matches.add(tmp);
            }
        }
        return matches;
    }

    public List<Match> findByTeam(String team) {
        List<Match> matches = new ArrayList<>();
        for (Match tmp : schedule) {
            if (tmp.getAwayTeam().equals(team) || tmp.getHostTeam().equals(team)) {
                matches.add(tmp);
            }
        }
        return matches;
    }

    public Match findById(Integer id) {
        for (Match tmp : schedule) {
            if (tmp.getMatchId().equals(id)) {
                return tmp;
            }
        }
        return null;
    }

}
