package by.bsuir.daniil.hockey_schedule.controllers;


import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Match> findById(@PathVariable String id){
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

    @GetMapping("/findByTeam")
    public List<Match> findByTeam(@RequestParam String team){
        return matchService.findByTeam(team);
    }
}