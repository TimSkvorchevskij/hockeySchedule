package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final ArenaRepository arenaRepository;
    private final TeamRepository teamRepository;
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match addMatch(Match newMatch) {
        if (newMatch.getTeamList() != null) {
            List<Team> teamList = newMatch.getTeamList();
            teamRepository.saveAll(teamList);
        }
        if (newMatch.getArena() != null){
            arenaRepository.save(newMatch.getArena());
        }
        return matchRepository.save(newMatch);
    }

    public String deleteMatch(Integer delMatchId) {
        matchRepository.deleteById(delMatchId);
        return "Successfully";
    }
    public Match setNewArena(Integer matchId,Integer newArenaId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + " doesn't exist"));
        Arena arena = arenaRepository.findById(newArenaId).orElseThrow(() -> new IllegalStateException("arena with id: " + newArenaId + " doesnt exist"));
        match.setArena(arena);
        return matchRepository.save(match);
    }

    public Optional<Match> findById(Integer id) {
        return matchRepository.findById(id);
    }
}
