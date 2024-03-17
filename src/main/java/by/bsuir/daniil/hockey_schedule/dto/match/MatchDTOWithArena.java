package by.bsuir.daniil.hockey_schedule.dto.match;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class MatchDTOWithArena {
    private Integer id;
    private ZonedDateTime dateTime;
    private ArenaDTO arenaDTO;
}
