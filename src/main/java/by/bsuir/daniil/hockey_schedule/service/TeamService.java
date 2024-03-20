package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.aspect.AspectAnnotation;
import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.exception.BadRequestException;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
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
public class TeamService {
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final CacheManager<String, Object> cacheManager;
    private static final String TEAM_DOESNT_EXIST = "Team doesn't exist. ID = ";
    private static final String MATCH_DOESNT_EXIST = "Match doesn't exist. ID = ";
    private static final String TEAM_DTO = "teamDTO_";
    private static final String MATCH_DTO = "matchDTOWithTeamAndArena_";

    @AspectAnnotation
    public TeamDTO addTeam(final Team newTeam) {
        teamRepository.save(newTeam);
        cacheManager.put(TEAM_DTO + newTeam.getId().toString(), ConvertDTOClasses.convertToTeamDTO(newTeam));
        return ConvertDTOClasses.convertToTeamDTO(newTeam);
    }

    @AspectAnnotation
    public TeamDTO findTeamById(final Integer teamId) {
        Object cachedData = cacheManager.get(TEAM_DTO + teamId.toString());
        if (cachedData != null) {
            return (TeamDTO) cachedData;
        } else {
            TeamDTO teamDTO = ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException(TEAM_DOESNT_EXIST + teamId)));
            cacheManager.put(TEAM_DTO + teamId.toString(), teamDTO);
            return teamDTO;
        }
    }

    @AspectAnnotation
    @Transactional
    public void deleteTeam(final Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TEAM_DOESNT_EXIST + id));
        List<Match> matchList = team.getMatchList();
        for (Match match : matchList) {
            match.getTeamList().remove(team);
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        }
        cacheManager.remove(TEAM_DTO + id.toString());
        teamRepository.deleteById(id);
    }

    @AspectAnnotation
    public TeamDTO addMatchInMatchList(final Integer teamId, final Integer matchId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException(TEAM_DOESNT_EXIST + teamId));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new ResourceNotFoundException(MATCH_DOESNT_EXIST + matchId));
        if (match.getTeamList().isEmpty()) {
            List<Team> teamList = new ArrayList<>();
            teamList.add(team);
            match.setTeamList(teamList);
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        } else if (match.getTeamList().contains(team)) {
            throw new BadRequestException("This match is already set");
        } else if (match.getTeamList().size() >= 2) {
            throw new BadRequestException("A match can contain only two teams");
        } else {
            match.getTeamList().add(team);
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        }
        return ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId).orElse(null));
    }

    @Transactional
    public List<TeamDTOWithMatch> getAllTeams() {
        List<TeamDTOWithMatch> teamDTOList = new ArrayList<>();
        for (Team team : teamRepository.findAll()) {
            teamDTOList.add(ConvertDTOClasses.convertToTeamDTOWithMatch(team));
            cacheManager.put(TEAM_DTO + team.getId().toString(), ConvertDTOClasses.convertToTeamDTO(team));
        }
        return teamDTOList;
    }


    @AspectAnnotation
    public TeamDTO delMatchInMatchList(final Integer teamId, final Integer matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new ResourceNotFoundException(MATCH_DOESNT_EXIST + matchId));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException(TEAM_DOESNT_EXIST + teamId));
        if (match.getTeamList().remove(team)) {
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        }
        cacheManager.remove(TEAM_DTO + teamId.toString());
        return ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId).orElse(null));
    }
}
