package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    private MatchService matchService;
    @GetMapping
    public ResponseEntity<List<MatchDTOWithTeamAndArena>> getAllMatches(){
        List<MatchDTOWithTeamAndArena> matchDTOWithTeamAndArenaList = matchService.getAllMatches();
        if (matchDTOWithTeamAndArenaList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(matchDTOWithTeamAndArenaList,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTOWithTeamAndArena> findById(@PathVariable String id){
        return new ResponseEntity<>(matchService.findById(Integer.parseInt(id)),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MatchDTOWithTeamAndArena> addMatch(@RequestBody Match match){
        return new ResponseEntity<>(matchService.addMatch(match),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMatch(@RequestParam Integer matchId){
        matchService.deleteMatch(matchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/setArena")
    public ResponseEntity<MatchDTOWithArena> setNewArenaId(@RequestParam Integer matchId, @RequestParam Integer newArenaId){
        return new ResponseEntity<>(matchService.setNewArena(matchId,newArenaId),HttpStatus.OK);
    }
}
