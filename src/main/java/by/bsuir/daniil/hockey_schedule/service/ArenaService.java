package by.bsuir.daniil.hockey_schedule.service;


import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArenaService {

    private ArenaRepository arenaRepository;

    public List<Arena> getAllArenas() {
        return arenaRepository.findAll();
    }

    public Arena getArenaById(Integer arenaId){
        return arenaRepository.findById(arenaId).orElse(null);
    }

    public String createArena(Arena arena){
        arenaRepository.save(arena);
        return "allGood";
    }

    public String deleteArena(Integer arenaId){
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new IllegalStateException("Arena with Id: " + arenaId + " doesnt exist!"));
        List<Match> matchList = arena.getMatchList();
        for (Match match: matchList){
            match.setArena(null);
        }
        arenaRepository.deleteById(arenaId);
        return "All good";
    }

    public Arena update(Integer arenaId, String city, Integer capacity){
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new IllegalStateException("Arena with id: " + arenaId + " doesn't exist!"));
        if (city != null && !city.isEmpty()){
            arena.setCity(city);
        }
        if (capacity != null){
            arena.setCapacity(capacity);
        }
        return arenaRepository.save(arena);
    }
}
