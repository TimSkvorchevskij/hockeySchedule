package by.bsuir.daniil.hockey_schedule.dto;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeam;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertDTOClasses {

    static public ArenaDTOWithMatch convertToArenaDTOWithTeam(Arena arena){
        if (arena == null){
            return null;
        }
        ArenaDTOWithMatch arenaDTO = new ArenaDTOWithMatch();
        arenaDTO.setId(arena.getId());
        arenaDTO.setCity(arena.getCity());
        arenaDTO.setCapacity(arena.getCapacity());

        List<MatchDTOWithTeam> matchDTOWithTeamList = new ArrayList<>();
        if (arena.getMatchList() != null) {
            for (Match match : arena.getMatchList()) {
                matchDTOWithTeamList.add(convertToMatchDTOWithTeam(match));
            }
        }
        arenaDTO.setMatchDTOWithTeamList(matchDTOWithTeamList);
        return arenaDTO;
    }
    static public ArenaDTO convertToArenaDTO(Arena arena){
        if (arena == null){
            return null;
        }
        ArenaDTO arenaDTO = new ArenaDTO();
        arenaDTO.setId(arena.getId());
        arenaDTO.setCity(arena.getCity());
        arenaDTO.setCapacity(arena.getCapacity());
        return arenaDTO;
    }

    static public MatchDTOWithTeam convertToMatchDTOWithTeam(Match match){
        if (match == null){
            return null;
        }
        MatchDTOWithTeam matchDTOWithTeam = new MatchDTOWithTeam();
        matchDTOWithTeam.setId(match.getId());
        matchDTOWithTeam.setDateTime(match.getDateTime());
        List<TeamDTO> teamDTOList = new ArrayList<>();
        if (match.getTeamList() != null) {
            for (Team team : match.getTeamList()) {
                teamDTOList.add(convertToTeamDTO(team));
            }
        }
        matchDTOWithTeam.setTeamDTOList(teamDTOList);
        return matchDTOWithTeam;
    }
    static public MatchDTOWithTeamAndArena convertToMatchDTOWithTeamAndArena(Match match){
        if (match == null){
            return null;
        }
        MatchDTOWithTeamAndArena matchDTOWithTeamAndArena = new MatchDTOWithTeamAndArena();
        matchDTOWithTeamAndArena.setId(match.getId());
        matchDTOWithTeamAndArena.setDateTime(match.getDateTime());
        matchDTOWithTeamAndArena.setArenaDTO(convertToArenaDTO(match.getArena()));
        List<TeamDTO> teamDTOList = new ArrayList<>();
        if (match.getTeamList() != null){
            for (Team team : match.getTeamList()) {
                teamDTOList.add(convertToTeamDTO(team));
            }
        }
        matchDTOWithTeamAndArena.setTeamDTOList(teamDTOList);
        return matchDTOWithTeamAndArena;
    }

    static public TeamDTO convertToTeamDTO(Team team) {
        if (team == null){
            return null;
        }
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setTeamName(team.getTeamName());
        return teamDTO;
    }

    static public TeamDTOWithMatch convertToTeamDTOWithMatch(Team team) {
        if (team == null){
            return null;
        }
        TeamDTOWithMatch teamDTO = new TeamDTOWithMatch();
        teamDTO.setId(team.getId());
        teamDTO.setTeamName(team.getTeamName());
        List<MatchDTOWithArena> matchDTOWithArenaList = new ArrayList<>();
        if (team.getMatchList() != null) {
            for (Match match : team.getMatchList()) {
                matchDTOWithArenaList.add(convertToMatchDTOWithArena(match));
            }
        }
        teamDTO.setMatchDTOWithArenaList(matchDTOWithArenaList);
        return teamDTO;
    }

    static public MatchDTOWithArena convertToMatchDTOWithArena(Match match){
        MatchDTOWithArena matchDTOWithArena = new MatchDTOWithArena();
        matchDTOWithArena.setId(match.getId());
        matchDTOWithArena.setDateTime(match.getDateTime());
        matchDTOWithArena.setArenaDTO(convertToArenaDTO(match.getArena()));
        return matchDTOWithArena;
    }
}
