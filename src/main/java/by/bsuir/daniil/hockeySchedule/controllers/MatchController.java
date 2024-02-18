package by.bsuir.daniil.hockeySchedule.controllers;


import by.bsuir.daniil.hockeySchedule.model.Match;
import by.bsuir.daniil.hockeySchedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private MatchService matchService;

    @GetMapping
    public List<Match> getAllMatches(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match findById(@PathVariable String id){
        //        if (match != null)

        return matchService.findById(Integer.parseInt(id));
    }

    @PostMapping("/create")
    public Match addMatch(@RequestBody Match match){
        return matchService.addMatch(match);
    }

    @DeleteMapping("/delete")
    public String deleteMatch(@RequestBody Match match){
        if (matchService.deleteMatch(match))
            return "Delete Successfully";
        return "Element not found";
    }

    @GetMapping("/findByTeam/{team}")
    public List<Match> findByTeam(@PathVariable String team){
        return matchService.findByTeam(team);
    }
}
