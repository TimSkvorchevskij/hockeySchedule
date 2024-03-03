package by.bsuir.daniil.hockey_schedule.dto;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
@Data
public class MatchDTO {
    private Integer matchId;
    private String hostTeam;
    private String awayTeam;
    private ZonedDateTime dateTime;
}
