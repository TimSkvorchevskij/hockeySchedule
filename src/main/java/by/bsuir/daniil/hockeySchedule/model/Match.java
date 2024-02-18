package by.bsuir.daniil.hockeySchedule.model;

import lombok.Builder;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Builder
public class Match {
    private String hostTeam;
    private String awayTeam;
    private String city;
    private ZonedDateTime dateTime;
    private Integer matchId;

//    private GameStats gameStats;
}
