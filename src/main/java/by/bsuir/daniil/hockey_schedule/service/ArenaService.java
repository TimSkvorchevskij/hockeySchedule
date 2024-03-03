package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.dto.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.MatchDTO;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArenaService {

    private ArenaRepository arenaRepository;

    private MatchRepository matchRepository;

    private ConvertArenaDTOAndMatchDTO convertArenaDTOAndMatchDTO;

    public String getCityById(Integer arenaId){
        return arenaRepository.findById(arenaId).map(Arena::getCity).orElse("undefined");
    }
//    @Transactional
    public List<ArenaDTO> getAllArenas(){
        List<ArenaDTO> arenaDTOList = new ArrayList<>();
        for(Arena arena : arenaRepository.findAllArenaWithMatches()){
            arenaDTOList.add(convertArenaDTOAndMatchDTO.convertToArenaDTO(arena));
        }
        return arenaDTOList;
    }
    public Arena getArenaById(Integer arenaId){
        return arenaRepository.findById(arenaId).orElse(null);
    }

    public Integer getCapacityById(Integer arenaId){
        return arenaRepository.findById(arenaId).map(Arena::getCapacity).orElse(null);
    }

    public String createArena(Arena arena){
        arenaRepository.save(arena);
        return "allGood";
    }
    public String deleteArena(Integer id){
        arenaRepository.deleteById(id);
        return "All good";
    }
    public Arena upload(Integer id, Arena arena){
        if (arenaRepository.findById(id).isPresent() && id.equals(arena.getArenaId())){
            return arenaRepository.save(arena);
        }
        else {  return null;    }
    }
}
