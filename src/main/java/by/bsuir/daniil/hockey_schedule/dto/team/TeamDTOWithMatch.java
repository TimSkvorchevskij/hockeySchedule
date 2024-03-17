package by.bsuir.daniil.hockey_schedule.dto.team;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import lombok.Data;

import java.util.List;

@Data
public class TeamDTOWithMatch {
    private Integer id;
    private String teamName;
    private List<MatchDTOWithArena> matchDTOWithArenaList;
}
