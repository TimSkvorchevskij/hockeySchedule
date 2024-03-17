package by.bsuir.daniil.hockey_schedule.service;


import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.ConvertDTOClasses;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ArenaService {

    private final ArenaRepository arenaRepository;
    private final CacheManager cacheManager;

    @Transactional
    public List<ArenaDTOWithMatch> getAllArenas() {
//        Object cacheData = cacheManager.get("AllArenas");
//        if (cacheData != null) {
//            return (List<ArenaDTOWithMatch>) cacheData;
//        } else {
        List<ArenaDTOWithMatch> arenaDTOWithMatchList = new ArrayList<>();
        for (Arena arena : arenaRepository.findAll()) {
            arenaDTOWithMatchList.add(ConvertDTOClasses.convertToArenaDTOWithTeam(arena));
//            cacheManager.put("AllArenas", arenaDTOWithMatchList);
        }
        return arenaDTOWithMatchList;
    }

    @Transactional
    public List<ArenaDTO> getArenaByCapacity(Integer minValue, Integer maxValue) {
        List<ArenaDTO> arenaDTOList = new ArrayList<>();
        if ((minValue == null && maxValue == null) || ((minValue != null && maxValue != null) && (minValue > maxValue))) {
            return arenaDTOList;
        } else if (minValue == null) {
            for (Arena arena : arenaRepository.findArenaByMaxCapacity(maxValue)) {
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
                cacheManager.put(arenaDTO.getId().toString(), arenaDTO);
            }
        } else if (maxValue == null) {
            for (Arena arena : arenaRepository.findArenaByMinCapacity(minValue)) {
                //arenaDTOList.add(ConvertDTOClasses.convertToArenaDTO(arena));
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
                cacheManager.put(arenaDTO.getId().toString(), arenaDTO);
            }
        } else {
            for (Arena arena : arenaRepository.findArenaByMinAndMaxCapacity(minValue, maxValue)) {
//                arenaDTOList.add(ConvertDTOClasses.convertToArenaDTO(arena));
                ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arena);
                arenaDTOList.add(arenaDTO);
                cacheManager.put(arenaDTO.getId().toString(), arenaDTO);
            }
        }
        return arenaDTOList;
    }

    public ArenaDTO getArenaById(Integer arenaId) {
        Object cachedData = cacheManager.get(arenaId.toString());
        if (cachedData != null) {
            return (ArenaDTO) cachedData;
        } else {
            ArenaDTO arenaDTO = ConvertDTOClasses.convertToArenaDTO(arenaRepository.findById(arenaId).orElse(null));
            if (arenaDTO == null) {
                return null;
            } else {
                cacheManager.put(arenaId.toString(), arenaDTO);
            }
            return arenaDTO;
        }
    }

    public String createArena(Arena arena) {
        arenaRepository.save(arena);
        cacheManager.put(arena.getId().toString(), ConvertDTOClasses.convertToArenaDTO(arena));
        return "allGood";
    }

    public String deleteArena(Integer arenaId) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new IllegalStateException("Arena with Id: " + arenaId + " doesnt exist!"));
        List<Match> matchList = arena.getMatchList();
        for (Match match : matchList) {
            match.setArena(null);
        }
        arenaRepository.deleteById(arenaId);
        cacheManager.evict(arenaId.toString());
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
        cacheManager.put(arenaId.toString(), arenaDTO);
        return arenaDTO;
    }
}
