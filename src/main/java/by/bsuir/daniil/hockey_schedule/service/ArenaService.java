package by.bsuir.daniil.hockey_schedule.service;


import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ArenaService {

    private final ArenaRepository arenaRepository;
    private final CacheManager<String,Object> cacheManager;
    private final MatchRepository matchRepository;
    private static final String ARENA_DTO = "arenaDTO";

    @Transactional
    public List<ArenaDTOWithMatch> getAllArenas() {
        List<ArenaDTOWithMatch> arenaDTOWithMatchList = new ArrayList<>();
        for (Arena arena : arenaRepository.findAllWithMatchListAndTeamList()) {
            arenaDTOWithMatchList.add(ConvertDTOClasses.convertToArenaDTOWithTeam(arena));
        }
        return arenaDTOWithMatchList;
    }



    boolean checkValidationCapacity(Integer minValue, Integer maxValue){
        return (minValue == null && maxValue == null) || ((minValue != null && maxValue != null) && (minValue > maxValue));
    }
    @Transactional
    public List<ArenaDTO> getArenaByCapacity(Integer minValue, Integer maxValue) {
        List<ArenaDTO> arenaDTOList = new ArrayList<>();
        if (checkValidationCapacity(minValue, maxValue)) {
            return arenaDTOList;
        } else if (minValue == null) {
            for (Arena arena : arenaRepository.findArenaByMaxCapacity(maxValue)) {
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
            }
        } else if (maxValue == null) {
            for (Arena arena : arenaRepository.findArenaByMinCapacity(minValue)) {
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
            }
        } else {
            for (Arena arena : arenaRepository.findArenaByMinAndMaxCapacity(minValue, maxValue)) {
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
            }
        }
        return arenaDTOList;
    }

        public ArenaDTO getArenaById(Integer arenaId) {
        Object cachedData = cacheManager.get(ARENA_DTO + arenaId.toString());
        if (cachedData != null) {
            return (ArenaDTO) cachedData;
        } else {
            ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arenaRepository.findById(arenaId).orElse(null));
            if (arenaDTO == null) {
                return null;
            } else {
                cacheManager.put(ARENA_DTO  + arenaId.toString(), arenaDTO);
            }
            return arenaDTO;
        }
    }

    public ArenaDTO createArena(Arena arena) {
        arenaRepository.save(arena);
        ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
        cacheManager.put(ARENA_DTO  + arena.getId().toString(), arenaDTO);
        return arenaDTO;
    }
@Transactional
    public String deleteArena(Integer arenaId) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new IllegalStateException("Arena with Id: " + arenaId + " doesnt exist!"));
        List<Match> matchList = arena.getMatchList();
        for (Match match : matchList) {
            match.setArena(null);
            cacheManager.put("matchDTOWithTeamAndArena_" + match.getId().toString(), ConvertDTOClasses.convertToMatchDTOWithTeamAndArena(match));
            matchRepository.save(match);
        }
        arenaRepository.deleteById(arenaId);
        cacheManager.remove(ARENA_DTO  + arenaId.toString());
        return "All good";
    }

    public ArenaDTO update(Integer arenaId, String city, Integer capacity) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new IllegalStateException("Arena with id: " + arenaId + " doesn't exist!"));
        if (city != null && !city.isEmpty()) {
            arena.setCity(city);
        }
        if (capacity != null) {
            arena.setCapacity(capacity);
        }
        arenaRepository.save(arena);
        ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
        cacheManager.put(ARENA_DTO  + arenaId.toString(), arenaDTO);
        return arenaDTO;
    }
}
