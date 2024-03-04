package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public Team addTeam(Team newTeam){
        return teamRepository.save(newTeam);
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

    public Team addMatchInMatchList(Integer teamId, Integer matchId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team with id: " + teamId + "doesn't exist"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("match with id: " + matchId + "doesnt exist"));
        if (match.getTeamList().isEmpty()){
            List<Team> teamList = new ArrayList<>();
            teamList.add(team);
            match.setTeamList(teamList);
            matchRepository.save(match);
        }
        else if (!match.getTeamList().contains(team)){
            match.getTeamList().add(team);
                matchRepository.save(match);
        }
        return teamRepository.findById(teamId).orElse(null);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Team delMatchInMatchList(Integer teamId, Integer matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalStateException("Match doesnt exist"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalStateException("team doesnt exist"));
        if (match.getTeamList().remove(team)) {
            matchRepository.save(match);
        }
        return teamRepository.findById(teamId).orElse(null);
    }
}
