package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.aspect.AspectAnnotation;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.service.ArenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ArenaController",
        description = "You can add edit and view information about arenas")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/arena")
@CrossOrigin(origins = "http://localhost:3000")
public class ArenaController {

    private ArenaService arenaService;

    @Operation(summary = "Просмотр всех арен",
            description = "Позволяет просмотреть все арены")
    @GetMapping
    public ResponseEntity<List<ArenaDTOWithMatch>> getAllArenas() {
        List<ArenaDTOWithMatch> arenaDTOWithMatchList = arenaService.getAllArenas();
        if (arenaDTOWithMatchList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(arenaDTOWithMatchList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Получение арены по вместительности",
            description = "Позволяет просмотреть список арен с указанной вместительностью")
    @GetMapping("/search/capacity")
    public ResponseEntity<List<ArenaDTO>> getArenaByCapacity(
            @RequestParam(value = "moreThan", required = false)
            @Parameter(description = "Минимальное значение вместительности") final Integer minValue,
            @RequestParam(value = "lessThan", required = false)
            @Parameter(description = "Максимальное значение вместительности") final Integer maxValue) {
        List<ArenaDTO> arenaDTOList = arenaService.getArenaByCapacity(minValue, maxValue);
        if (arenaDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(arenaDTOList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Просмотр по ID",
            description = "Позволяет просмотреть арену по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArenaDTO> getArenaById(
            @PathVariable @Parameter(description = "ID арены которую нужно найти") final Integer id) {
        return new ResponseEntity<>(arenaService.getArenaById(id), HttpStatus.OK);
    }

    @Operation(summary = "Регистрация арены",
            description = "Позволяет создать новвую арену")
    @PostMapping("/create")
    @AspectAnnotation
    public ResponseEntity<ArenaDTO> createArena(
            @RequestBody @Parameter(description = "Json объект новой арены") final Arena arena) {
        return new ResponseEntity<>(arenaService.createArena(arena), HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление арены",
            description = "Позволяет удлаить арену по ID")
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteArena(
            @RequestParam @Parameter(description = "ID арены которую нужно удалить") final Integer id) {
        arenaService.deleteArena(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Изменение данных арены",
            description = "Позволяет изменить арены")
    @PutMapping("/change")
    public ResponseEntity<ArenaDTO> update(
            @RequestParam @Parameter(description = "ID арены в которой нужно изменить параметры") final Integer arenaId,
            @RequestParam(required = false) @Parameter(description = "Новый город") final String city,
            @RequestParam(required = false) @Parameter(description = "Новая вместительность") final Integer capacity) {
        return new ResponseEntity<>(arenaService.update(arenaId, city, capacity), HttpStatus.OK);
    }
}
