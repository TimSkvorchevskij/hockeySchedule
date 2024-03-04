package by.bsuir.daniil.hockey_schedule.dto;

import by.bsuir.daniil.hockey_schedule.model.Match;
import lombok.Data;

import java.util.List;

@Data
public class ArenaDTO {
    Integer arenaId;
    String city;
    Integer capacity;
    List<MatchDTO> matchList;
}
