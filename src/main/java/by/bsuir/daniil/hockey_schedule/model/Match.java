package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue
    private Integer id;

    private ZonedDateTime dateTime;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JsonIgnoreProperties("matchList")
    @JoinTable(name = "match_team",
            joinColumns = @JoinColumn(name = "matchId"),
            inverseJoinColumns = @JoinColumn(name = "teamId"))
    private List<Team> teamList;

    @ManyToOne
    @JoinColumn(name = "matchId")
    @JsonIgnoreProperties("matchList")
    private Arena arena;
}
