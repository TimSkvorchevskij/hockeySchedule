package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.aspect.AspectAnnotation;
import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.exception.BadRequestException;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Component
@Transactional
public class MatchService {
    private final MatchRepository matchRepository;
    private final ArenaRepository arenaRepository;
    private final TeamRepository teamRepository;
    private final CacheManager<String, Object> cacheManager;
    private static final String MATCH_DTO = "matchDTOWithTeamAndArena_";
    private static final String DOESNT_EXIST = "Match doesn't exist ID = ";

    public List<MatchDTOWithTeamAndArena> getAllMatches() {
        List<MatchDTOWithTeamAndArena> matchDTOWithTeamAndArenaList = new ArrayList<>();
        for (Match match : matchRepository.findAll()) {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match);
            matchDTOWithTeamAndArenaList.add(matchDTOWithTeamAndArena);
            cacheManager.put(MATCH_DTO + match.getId().toString(), matchDTOWithTeamAndArena);
        }
        return matchDTOWithTeamAndArenaList;
    }

    @AspectAnnotation
    public MatchDTOWithTeamAndArena addMatch(final Match newMatch) {
        if (newMatch.getTeamList() != null) {
            List<Team> teamList = newMatch.getTeamList();
            if (teamList.size() > 2) {
                throw new BadRequestException("A match can contain only two teams");
            }
            List<Team> newTeamList = new ArrayList<>();
            for (Team team : teamList) {
                if (team.getId() == null) {
                    newTeamList.add(teamRepository.save(team));
                } else {
                    teamRepository.findById(team.getId()).ifPresent(newTeamList::add);
                }
            }
            newMatch.setTeamList(newTeamList);
        }
        if (newMatch.getArena() != null) {
            Arena arena = newMatch.getArena();
            if (arena.getId() == null) {
                arenaRepository.save(arena);
            } else {
                newMatch.setArena(null);
                arenaRepository.findById(arena.getId()).ifPresent(newMatch::setArena);
            }
        }
        MatchDTOWithTeamAndArena matchDTOWithTeamAndArena =
                ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(matchRepository.save(newMatch));
        cacheManager.put(MATCH_DTO + newMatch.getId(), matchDTOWithTeamAndArena);
        return matchDTOWithTeamAndArena;
    }

    @AspectAnnotation
    public void deleteMatch(final Integer delMatchId) {
        Optional<Match> matchOptional = matchRepository.findById(delMatchId);
        if (matchOptional.isPresent()) {
            matchRepository.deleteById(delMatchId);
            // Дополнительные действия...
        } else {
            // Если матч не найден, бросаем исключение или выполняем другую логику
            throw new ResourceNotFoundException(DOESNT_EXIST + delMatchId);
        }
        cacheManager.remove(MATCH_DTO + delMatchId);
    }

    @AspectAnnotation
    public MatchDTOWithArena setNewArena(final Integer matchId, final Integer newArenaId) {
        Match match = matchRepository.findById(matchId).orElseThrow(()
                -> new ResourceNotFoundException(DOESNT_EXIST + matchId));
        Arena arena = arenaRepository.findById(newArenaId).orElseThrow(()
                -> new ResourceNotFoundException("Arena with id: " + newArenaId + " doesnt exist"));
        match.setArena(arena);
        matchRepository.save(match);
        cacheManager.put(MATCH_DTO + matchId, ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        return ConvertDTOClasses.convertToMatchDTOWithArena(match);
    }

    @AspectAnnotation
    public MatchDTOWithTeamAndArena findById(final Integer id) {
        Object cachedData = cacheManager.get(MATCH_DTO + id.toString());
        if (cachedData != null) {
            return (MatchDTOWithTeamAndArena) cachedData;
        } else {
            MatchDTOWithTeamAndArena matchDTOWithTeamAndArena =
                    ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(matchRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(DOESNT_EXIST + id)));
            cacheManager.put(MATCH_DTO + id, matchDTOWithTeamAndArena);
            return matchDTOWithTeamAndArena;
        }
    }
}
