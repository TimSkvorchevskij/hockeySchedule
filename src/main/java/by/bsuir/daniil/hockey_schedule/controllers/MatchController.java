package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    private MatchService matchService;
    @GetMapping
    public List<MatchDTOWithTeamAndArena> getAllMatches(){
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
    public String deleteMatch(@RequestParam Integer matchId){
        return matchService.deleteMatch(matchId);
    }

    @PutMapping("/setArena")
    public Match setNewArenaId(@RequestParam Integer matchId, @RequestParam Integer newArenaId){
        return matchService.setNewArena(matchId,newArenaId);
    }
}
