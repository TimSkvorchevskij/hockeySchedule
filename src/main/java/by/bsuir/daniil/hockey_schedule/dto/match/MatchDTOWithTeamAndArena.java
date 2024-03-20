package by.bsuir.daniil.hockey_schedule.dto.match;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Schema(description = "Сущность матча с ареной и списком команда")
public class MatchDTOWithTeamAndArena {
    private Integer id;
    private ZonedDateTime dateTime;
    @Schema(description = "Список играющих команд")
    private List<TeamDTO> teamDTOList;
    @Schema(description = "Арена на которой проходит матч")
    private ArenaDTO arenaDTO;
}
