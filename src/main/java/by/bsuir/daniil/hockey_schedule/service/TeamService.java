package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
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
    private final CacheManager cacheManager;

    private static final String TEAM_DTO = "teamDTO_";
    private static final String MATCH_DTO = "matchDTOWithTeamAndArena_";


    public TeamDTO addTeam(Team newTeam) {
        teamRepository.save(newTeam);
        cacheManager.put(TEAM_DTO + newTeam.getId().toString(), ConvertDTOClasses.convertToTeamDTO(newTeam));
        return ConvertDTOClasses.convertToTeamDTO(newTeam);
    }

    public TeamDTO findTeamById(Integer teamId){
        Object cachedData = cacheManager.get(TEAM_DTO + teamId.toString());
        if (cachedData != null) {
            return (TeamDTO) cachedData;
        } else {
            TeamDTO teamDTO = ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId).orElse(null));
            if (teamDTO == null) {
                return null;
            } else {
                cacheManager.put(TEAM_DTO + teamId.toString(), teamDTO);
            }
            return teamDTO;
        }
    }

    @Transactional
    public String deleteTeam(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalStateException("Error to delete"));
        List<Match> matchList = team.getMatchList();
        for (Match match : matchList) {
            match.getTeamList().remove(team);
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        }
        cacheManager.evict(TEAM_DTO+ id.toString());
        teamRepository.deleteById(id);
        return "All Good";
    }

    public TeamDTO addMatchInMatchList(Integer teamId, Integer matchId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team with id: " + teamId + "doesn't exist"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + "doesnt exist"));
        if (match.getTeamList().isEmpty()) {
            List<Team> teamList = new ArrayList<>();
            teamList.add(team);
            match.setTeamList(teamList);
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        } else if (!match.getTeamList().contains(team) && match.getTeamList().size() < 2) {
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


    public TeamDTO delMatchInMatchList(Integer teamId, Integer matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("Match doesnt exist"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team doesnt exist"));
        if (match.getTeamList().remove(team)) {
            matchRepository.save(match);
            cacheManager.put(MATCH_DTO + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
        }
        cacheManager.evict(TEAM_DTO + teamId.toString());
        return ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId).orElse(null));
    }
}
