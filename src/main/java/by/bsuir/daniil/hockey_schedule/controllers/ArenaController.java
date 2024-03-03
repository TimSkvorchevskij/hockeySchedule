package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.service.ArenaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v2/arena")
public class ArenaController {

    private ArenaService arenaService;

    @GetMapping
    public List<ArenaDTO> getAllArenas(){
        return arenaService.getAllArenas();
    }
    @GetMapping("/{id}")
    public Arena getArenaById(@PathVariable Integer id){
        return arenaService.getArenaById(id);
    }

    @PostMapping("/create")
    public String createArena(@RequestBody Arena arena){
        return arenaService.createArena(arena);
    }

    @DeleteMapping("/delete")
    public String deleteArena(@RequestParam Integer id){
        return arenaService.deleteArena(id);
    }

    @PutMapping("/change")
    public Arena upload(@RequestParam Integer id, @RequestBody Arena arena){
        return arenaService.upload(id,arena);
    }
}
