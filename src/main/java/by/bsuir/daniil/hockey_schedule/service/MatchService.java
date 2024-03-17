package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final ArenaRepository arenaRepository;
    private final TeamRepository teamRepository;
    private final CacheManager cacheManager = CacheManager.getInstance();

    @Transactional
    public List<MatchDTOWithTeamAndArena> getAllMatches() {
        List<MatchDTOWithTeamAndArena> matchDTOWithTeamAndArenaList = new ArrayList<>();
        for (Match match : matchRepository.findAll()) {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match);
            matchDTOWithTeamAndArenaList.add(matchDTOWithTeamAndArena);
            cacheManager.put("matchDTOWithTeamAndArena_" + match.getId().toString(), matchDTOWithTeamAndArena);
        }
        return matchDTOWithTeamAndArenaList;
    }

//    @Transactional
    public MatchDTOWithTeamAndArena addMatch(Match newMatch) {

        if (newMatch.getTeamList() != null) {
            List<Team> teamList = newMatch.getTeamList();
            if (teamList.size() > 2) {
                throw new IllegalStateException("max 2 team");
            }
            teamRepository.saveAll(teamList);
        }
        if (newMatch.getArena() != null) {
            arenaRepository.save(newMatch.getArena());
        }
        matchRepository.save(newMatch);
        MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(matchRepository.save(newMatch));
        cacheManager.put("matchDTOWithTeamAndArena_" + newMatch.getId().toString(), matchDTOWithTeamAndArena);
        return matchDTOWithTeamAndArena;
    }

    @Transactional
    public String deleteMatch(Integer delMatchId) {
        matchRepository.deleteById(delMatchId);
        cacheManager.evict("matchDTOWithTeamAndArena_" + delMatchId.toString());
        return "Successfully";
    }

    public MatchDTOWithArena setNewArena(Integer matchId, Integer newArenaId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + " doesn't exist"));
        Arena arena = arenaRepository.findById(newArenaId).orElseThrow(() -> new IllegalStateException("arena with id: " + newArenaId + " doesnt exist"));
        match.setArena(arena);
        matchRepository.save(match);
        cacheManager.put("matchDTOWithTeamAndArena_" + matchId.toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        return ConvertDTOClasses.convertToMatchDTOWithArena(match);
    }

    @Transactional
    public MatchDTOWithTeamAndArena findById(Integer id) {
        Object cachedData = cacheManager.get("matchDTOWithTeamAndArena_" + id.toString());
        if (cachedData != null) {
            return (MatchDTOWithTeamAndArena) cachedData;
        } else {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(matchRepository.findById(id).orElse(null));
            if (matchDTOWithTeamAndArena == null) {
                return null;
            } else {
                cacheManager.put("matchDTOWithTeamAndArena_" + id.toString(), matchDTOWithTeamAndArena);
            }
            return matchDTOWithTeamAndArena;
        }
    }
}
