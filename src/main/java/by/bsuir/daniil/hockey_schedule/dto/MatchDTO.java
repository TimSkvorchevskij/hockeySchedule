package by.bsuir.daniil.hockey_schedule.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class MatchDTO {
    private Integer matchId;
    private List<TeamDTO> teams;
    private ZonedDateTime dateTime;
}
