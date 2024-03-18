package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;

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



@Service
@AllArgsConstructor
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final ArenaRepository arenaRepository;
    private final TeamRepository teamRepository;
    private final CacheManager<String,Object> cacheManager;

    private static final String MATCH_DTO = "matchDTOWithTeamAndArena_";

    @Transactional
    public List<MatchDTOWithTeamAndArena> getAllMatches() {
        List<MatchDTOWithTeamAndArena> matchDTOWithTeamAndArenaList = new ArrayList<>();
        for (Match match : matchRepository.findAll()) {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match);
            matchDTOWithTeamAndArenaList.add(matchDTOWithTeamAndArena);
            cacheManager.put(MATCH_DTO + match.getId().toString(), matchDTOWithTeamAndArena);
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
        cacheManager.put(MATCH_DTO + newMatch.getId().toString(), matchDTOWithTeamAndArena);
        return matchDTOWithTeamAndArena;
    }

    @Transactional
    public String deleteMatch(Integer delMatchId) {
        matchRepository.deleteById(delMatchId);
        cacheManager.remove(MATCH_DTO + delMatchId.toString());
        return "Successfully";
    }

    public MatchDTOWithArena setNewArena(Integer matchId, Integer newArenaId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + " doesn't exist"));
        Arena arena = arenaRepository.findById(newArenaId).orElseThrow(() -> new IllegalStateException("arena with id: " + newArenaId + " doesnt exist"));
        match.setArena(arena);
        matchRepository.save(match);
        cacheManager.put(MATCH_DTO + matchId.toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        return ConvertDTOClasses.convertToMatchDTOWithArena(match);
    }

    @Transactional
    public MatchDTOWithTeamAndArena findById(Integer id) {
        Object cachedData = cacheManager.get(MATCH_DTO+ id.toString());
        if (cachedData != null) {
            return (MatchDTOWithTeamAndArena) cachedData;
        } else {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(matchRepository.findById(id).orElse(null));
            if (matchDTOWithTeamAndArena == null) {
                return null;
            } else {
                cacheManager.put(MATCH_DTO + id.toString(), matchDTOWithTeamAndArena);
            }
            return matchDTOWithTeamAndArena;
        }
    }
}
