package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTOWithMatch>> getAllTeams(){
        List<TeamDTOWithMatch> teamDTOWithMatchList = teamService.getAllTeams();
        if (teamDTOWithMatchList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(teamDTOWithMatchList,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(teamService.findTeamById(id),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<TeamDTO> addTeam(@RequestBody Team newTeam){
        return new ResponseEntity<>(teamService.addTeam(newTeam), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteTeam(@RequestParam Integer teamId){
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/addMatch")
    public ResponseEntity<TeamDTO> addInMatchList(@RequestParam Integer teamId, @RequestParam Integer matchId){
        return new ResponseEntity<>(teamService.addMatchInMatchList(teamId, matchId),HttpStatus.OK);
    }

    @PutMapping("/delMatch")
    public ResponseEntity<TeamDTO> delInMatchList(@RequestParam Integer teamId,@RequestParam Integer matchId){
        return new ResponseEntity<>(teamService.delMatchInMatchList(teamId, matchId),HttpStatus.OK);
    }
}
