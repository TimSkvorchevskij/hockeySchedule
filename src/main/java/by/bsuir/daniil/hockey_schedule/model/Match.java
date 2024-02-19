package by.bsuir.daniil.hockey_schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @Column(unique = true)
    private Integer matchId;
    private String hostTeam;
    private String awayTeam;
    private String city;
    private ZonedDateTime dateTime;

}
