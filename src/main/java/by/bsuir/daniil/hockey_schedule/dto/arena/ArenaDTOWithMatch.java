package by.bsuir.daniil.hockey_schedule.dto.arena;

import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
@Schema(description = "Сущность арены со списком матчей")
public class ArenaDTOWithMatch {
    private Integer id;
    private String city;
    private Integer capacity;
    @Schema(description = "Список матчей")
    private List<MatchDTOWithTeam> matchDTOWithTeamList;
}
