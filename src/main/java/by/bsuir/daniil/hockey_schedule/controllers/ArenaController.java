package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.service.ArenaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/arena")
public class ArenaController {

    private ArenaService arenaService;

    @GetMapping
    public List<ArenaDTOWithMatch> getAllArenas(){
        return arenaService.getAllArenas();
    }
    @GetMapping("/{id}")
    public ArenaDTO getArenaById(@PathVariable Integer id){
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
    public ArenaDTO update(@RequestParam Integer arenaId,
                        @RequestParam(required = false) String city,
                        @RequestParam(required = false) Integer capacity){
        return arenaService.update(arenaId, city,capacity);
    }
}
