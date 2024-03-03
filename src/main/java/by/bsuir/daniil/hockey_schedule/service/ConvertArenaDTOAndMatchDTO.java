package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.dto.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.MatchDTO;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertArenaDTOAndMatchDTO {
    public ArenaDTO convertToArenaDTO(Arena arena){
        ArenaDTO arenaDTO = new ArenaDTO();
        arenaDTO.setArenaId(arena.getArenaId());
        arenaDTO.setCity(arena.getCity());
        arenaDTO.setCapacity(arena.getCapacity());

        List<MatchDTO> matchDTOList = new ArrayList<>();
        for(Match match: arena.getMatchList()){
            matchDTOList.add(convertToMatchDTO(match));
        }
        arenaDTO.setMatchList(matchDTOList);
        return arenaDTO;
    }

    public MatchDTO convertToMatchDTO(Match match){
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setMatchId(match.getMatchId());
        matchDTO.setHostTeam(match.getHostTeam());
        matchDTO.setAwayTeam(match.getAwayTeam());
        matchDTO.setDateTime(match.getDateTime());
        return matchDTO;
    }
}
