package by.bsuir.daniil.hockey_schedule.dto.team;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Сущность матча со списком матчей и арен")
public class TeamDTOWithMatch {
    private Integer id;
    private String teamName;
    @Schema(description = "Список матчей")
    private List<MatchDTOWithArena> matchDTOWithArenaList;
}
