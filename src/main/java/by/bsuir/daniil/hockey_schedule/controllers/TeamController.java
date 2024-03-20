package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TeamController",
        description = "You can add edit and view information about teams")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    private TeamService teamService;

    @Operation(summary = "Просмотр всех команд",
            description = "Позволяет просмотреть все команды вместе установленными им матчами")
    @GetMapping
    public ResponseEntity<List<TeamDTOWithMatch>> getAllTeams() {
        List<TeamDTOWithMatch> teamDTOWithMatchList = teamService.getAllTeams();
        if (teamDTOWithMatchList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(teamDTOWithMatchList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Поиск команды по ID",
            description = "Позволяет получить информацию о команде")
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable @Parameter(description = "ID команды которую хотим найти") final Integer id) {
        return new ResponseEntity<>(teamService.findTeamById(id), HttpStatus.OK);
    }

    @Operation(summary = "Регистрация команды",
            description = "Позволяет зарегистрировать новую команду")
    @PostMapping("/create")
    public ResponseEntity<TeamDTO> addTeam(@RequestBody @Parameter(description = "JSON объект новой команды") final Team newTeam) {
        return new ResponseEntity<>(teamService.addTeam(newTeam), HttpStatus.OK);
    }

    @Operation(summary = "Удаление команды",
            description = "Позволяет удалить команду")
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteTeam(@RequestParam @Parameter(description = "ID команды которую нужно удалить") final Integer teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Добавление матча",
            description = "Позволяет добавить новый матч в список")
    @PutMapping("/addMatch")
    public ResponseEntity<TeamDTO> addInMatchList(@RequestParam @Parameter(description = "ID команды которую в которую нужно добавить") final Integer teamId,
                                                  @RequestParam @Parameter(description = "ID матча который нужно добавить") final Integer matchId) {
        return new ResponseEntity<>(teamService.addMatchInMatchList(teamId, matchId), HttpStatus.OK);
    }

    @Operation(summary = "Удаление матча",
            description = "Позволяет удлаить из списка матч")
    @PutMapping("/delMatch")
    public ResponseEntity<TeamDTO> delInMatchList(@RequestParam @Parameter(description = "ID команды из которой нужно удалить") final Integer teamId,
                                                  @RequestParam @Parameter(description = "ID матча который нужно удалить") final Integer matchId) {
        return new ResponseEntity<>(teamService.delMatchInMatchList(teamId, matchId), HttpStatus.OK);
    }
}
