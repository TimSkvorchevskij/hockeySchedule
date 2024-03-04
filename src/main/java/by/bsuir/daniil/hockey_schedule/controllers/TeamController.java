package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @PostMapping("/create")
    public Team addTeam(@RequestBody Team newTeam){
        return teamService.addTeam(newTeam);
    }

    @DeleteMapping("/delete")
    public String deleteTeam(@RequestParam Integer teamId){
        return teamService.deleteTeam(teamId);
    }

    @PutMapping("/addMatch")
    public Team addInMatchList(@RequestParam Integer teamId,@RequestParam Integer matchId){
        return teamService.addMatchInMatchList(teamId, matchId);
    }

    @PutMapping("/delMatch")
    public Team delInMatchList(@RequestParam Integer teamId,@RequestParam Integer matchId){
        return teamService.delMatchInMatchList(teamId, matchId);
    }
}
