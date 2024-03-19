package by.bsuir.daniil.hockey_schedule.controllers;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.service.ArenaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/arena")
public class ArenaController {

    private ArenaService arenaService;

    @GetMapping
    public ResponseEntity<List<ArenaDTOWithMatch>> getAllArenas() {
        List<ArenaDTOWithMatch> arenaDTOWithMatchList = arenaService.getAllArenas();
        if (arenaDTOWithMatchList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(arenaDTOWithMatchList,HttpStatus.OK);
        }
    }

    @GetMapping("/search/capacity")
    public ResponseEntity<List<ArenaDTO>> getArenaByCapacity(@RequestParam(value = "moreThan", required = false) Integer minValue,
                                                            @RequestParam(value = "lessThan", required = false) Integer maxValue){
        List<ArenaDTO> arenaDTOList = arenaService.getArenaByCapacity(minValue, maxValue);
        if (arenaDTOList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(arenaDTOList,HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArenaDTO> getArenaById(@PathVariable Integer id){     ///////////////////////////
        return new ResponseEntity<>(arenaService.getArenaById(id),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ArenaDTO> createArena(@RequestBody Arena arena){
        return new ResponseEntity<>(arenaService.createArena(arena), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteArena(@RequestParam Integer id){
        arenaService.deleteArena(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/change")
    public ResponseEntity<ArenaDTO> update(@RequestParam Integer arenaId,
                        @RequestParam(required = false) String city,
                        @RequestParam(required = false) Integer capacity){
        return new ResponseEntity<>(arenaService.update(arenaId, city,capacity),HttpStatus.OK);
    }
}
