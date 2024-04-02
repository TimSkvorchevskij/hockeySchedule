package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MatchController",
        description = "You can add edit and view information about matches")
//@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/match")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MatchController {

    private MatchService matchService;

    @Operation(summary = "Просмотр всех матчей",
            description = "Позволяет просмотреть все матчи")
    @GetMapping
    public ResponseEntity<List<MatchDTOWithTeamAndArena>> getAllMatches() {
        List<MatchDTOWithTeamAndArena> matchDTOWithTeamAndArenaList = matchService.getAllMatches();
        if (matchDTOWithTeamAndArenaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(matchDTOWithTeamAndArenaList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Поиск по ID",
            description = "Позволяет просмотреть информацию о матче по ID")
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTOWithTeamAndArena> findById(
            @PathVariable @Parameter(description = "ID матча который нужно найти") final String id) {
        return new ResponseEntity<>(matchService.findById(Integer.parseInt(id)), HttpStatus.OK);
    }

    @Operation(summary = "Регистарция матча",
            description = "Позволяет зарегистрировать новый матч")
    @PostMapping("/create")
    public ResponseEntity<MatchDTOWithTeamAndArena> addMatch(
            @RequestBody @Parameter(description = "Json объект нового матча") final Match match) {
        return new ResponseEntity<>(matchService.addMatch(match), HttpStatus.OK);
    }

    @Operation(summary = "Удаление матча",
            description = "Позволяет удлаить")
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMatch(
            @RequestParam @Parameter(description = "ID матча который нужно удалить") final Integer matchId) {
        matchService.deleteMatch(matchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Установлка арены",
            description = "Позволяет установить новую арену для матча")
    @PutMapping("/setArena")
    public ResponseEntity<MatchDTOWithArena> setNewArenaId(
            @RequestParam @Parameter(description = "ID матча которому нужно установить новую арену") final Integer matchId,
            @RequestParam @Parameter(description = "ID арены") final Integer newArenaId) {
        return new ResponseEntity<>(matchService.setNewArena(matchId, newArenaId), HttpStatus.OK);
    }
}
