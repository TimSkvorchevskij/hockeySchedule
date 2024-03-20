package by.bsuir.daniil.hockey_schedule.dto.match;

import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Schema(description = "Сущность матча со списком команд")
public class MatchDTOWithTeam {
    private Integer id;
    private ZonedDateTime dateTime;
    @Schema(description = "Играющие команды")
    private List<TeamDTO> teamDTOList;
}
