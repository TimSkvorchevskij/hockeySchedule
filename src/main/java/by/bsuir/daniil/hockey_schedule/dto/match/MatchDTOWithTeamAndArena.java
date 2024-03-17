package by.bsuir.daniil.hockey_schedule.dto.match;

import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class MatchDTOWithTeamAndArena {
    private Integer id;
    private ZonedDateTime dateTime;
    private List<TeamDTO> teamDTOList;
    private ArenaDTO arenaDTO;
}
