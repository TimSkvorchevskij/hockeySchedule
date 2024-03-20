package by.bsuir.daniil.hockey_schedule.dto.match;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Schema(description = "Сущность матча с арен")
public class MatchDTOWithArena {
    private Integer id;
    private ZonedDateTime dateTime;
    @Schema(description = "Арена установленная для матча")
    private ArenaDTO arenaDTO;
}
