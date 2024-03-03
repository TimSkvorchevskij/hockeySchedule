package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final ArenaRepository arenaRepository;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match addMatch(Match newMatch) {
        return matchRepository.save(newMatch);
    }

    public String deleteMatch(Integer delMatchId) {
        matchRepository.deleteById(delMatchId);
        return "Successfully";
    }
    public Match setNewArena(Integer matchId,Integer newArenaId) {
        Optional<Match> tmpMatch = matchRepository.findById(matchId);
        Optional<Arena> tmpArena = arenaRepository.findById(newArenaId);
        if (tmpMatch.isPresent() && tmpArena.isPresent()) {
            Match match = tmpMatch.get();
            match.setArena(tmpArena.get());
            return matchRepository.save(match);
        } else {
            return null;
        }
    }
    public List<Match> findByDate(ZonedDateTime date) {
        return matchRepository.findAll();
    }

    public List<Match> findByTeam(String team) {
        return matchRepository.findByAwayTeamOrHostTeam(team,team);
    }

    public Optional<Match> findById(Integer id) {
        return matchRepository.findById(id);
    }
}
