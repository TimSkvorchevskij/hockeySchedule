package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
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
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams();
    }

    @PostMapping("/create")
    public TeamDTO addTeam(@RequestBody Team newTeam){
        return teamService.addTeam(newTeam);
    }

    @DeleteMapping("/delete")
    public String deleteTeam(@RequestParam Integer teamId){
        return teamService.deleteTeam(teamId);
    }

//    @PutMapping("/addMatch")
//    public TeamDTOWithMatch addInMatchList(@RequestParam Integer teamId, @RequestParam Integer matchId){
//        return teamService.addMatchInMatchList(teamId, matchId);
//    }

//    @PutMapping("/delMatch")
//    public TeamDTO delInMatchList(@RequestParam Integer teamId,@RequestParam Integer matchId){
//        return teamService.delMatchInMatchList(teamId, matchId);
//    }
}
