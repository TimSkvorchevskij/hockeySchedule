package by.bsuir.daniil.hockey_schedule.dto;

import by.bsuir.daniil.hockey_schedule.dto.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.MatchDTO;
import by.bsuir.daniil.hockey_schedule.dto.TeamDTO;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertArenaDTOAndMatchDTO {

    private MatchRepository matchRepository;
    private ArenaRepository arenaRepository;
    public ArenaDTO convertToArenaDTO(Arena arena){
        ArenaDTO arenaDTO = new ArenaDTO();
        arenaDTO.setArenaId(arena.getArenaId());
        arenaDTO.setCity(arena.getCity());
        arenaDTO.setCapacity(arena.getCapacity());

        List<MatchDTO> matchDTOList = new ArrayList<>();
        for(Match match: arena.getMatchList()){
            matchDTOList.add(convertToMatchDTO(match));
        }
        arenaDTO.setMatchList(matchDTOList);
        return arenaDTO;
    }

    public MatchDTO convertToMatchDTO(Match match){
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setMatchId(match.getMatchId());
        matchDTO.setDateTime(match.getDateTime());
        Hibernate.initialize(match.getTeamList());
        List<TeamDTO> teamDTOList = new ArrayList<>();
        for(Team team: match.getTeamList()){
            teamDTOList.add(convertToTeamDTO(team));
        }
        matchDTO.setTeams(teamDTOList);
        return matchDTO;
    }

    public TeamDTO convertToTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(team.getTeamId());
        teamDTO.setTeamName(team.getTeamName());
        return teamDTO;
    }
}
