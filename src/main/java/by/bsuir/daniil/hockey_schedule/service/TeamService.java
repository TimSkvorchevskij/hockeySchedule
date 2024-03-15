package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.hibernate.type.ConvertedBasicType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamDTO addTeam(Team newTeam){
        return ConvertDTOClasses.convertToTeamDTO(newTeam);
    }

    public String deleteTeam(Integer id){
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalStateException("Error to delete"));
        List<Match> matchList = team.getMatchList();
        for(Match match: matchList){
            match.getTeamList().remove(team);
            matchRepository.save(match);
        }
        teamRepository.deleteById(id);
        return "All Good";
    }

    public TeamDTOWithMatch addMatchInMatchList(Integer teamId, Integer matchId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team with id: " + teamId + "doesn't exist"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + "doesnt exist"));
        if (match.getTeamList().isEmpty()){
            List<Team> teamList = new ArrayList<>();
            teamList.add(team);
            match.setTeamList(teamList);
            matchRepository.save(match);
        }
        else if (!match.getTeamList().contains(team) && match.getTeamList().size() < 2){
            match.getTeamList().add(team);
                matchRepository.save(match);
        }
        return ConvertDTOClasses.convertToTeamDTOWithMatch(teamRepository.findById(teamId).orElse(null));
    }

    @Transactional
    public List<TeamDTO> getAllTeams(){
    List<TeamDTO> teamDTOList = new ArrayList<>();
    for(Team team: teamRepository.findAll()){
        teamDTOList.add(ConvertDTOClasses.convertToTeamDTO(team));
    }
    return teamDTOList;
    }

    public TeamDTO delMatchInMatchList(Integer teamId, Integer matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("Match doesnt exist"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team doesnt exist"));
        if (match.getTeamList().remove(team)) {
            matchRepository.save(match);
        }
        return ConvertDTOClasses.convertToTeamDTO(teamRepository.findById(teamId).orElse(null));
    }
}
