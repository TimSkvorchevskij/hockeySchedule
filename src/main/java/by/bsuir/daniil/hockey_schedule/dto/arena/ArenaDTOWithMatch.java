package by.bsuir.daniil.hockey_schedule.dto.arena;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeam;
import lombok.Data;

import java.util.List;

@Data
public class ArenaDTOWithMatch {
    Integer id;
    String city;
    Integer capacity;
    List<MatchDTOWithTeam> matchDTOWithTeamList;
}
