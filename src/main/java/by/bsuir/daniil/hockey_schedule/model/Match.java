package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "matches")
@Schema(description = "Сущность матча")
public class Match {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime dateTime;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JsonIgnoreProperties("matchList")
    @JoinTable(name = "match_team",
            joinColumns = @JoinColumn(name = "matchId"),
            inverseJoinColumns = @JoinColumn(name = "teamId"))
    @Schema(description = "Список команд участвующих в матче")
    private List<Team> teamList;

    @ManyToOne
    @JoinColumn(name = "matchId")
    @JsonIgnoreProperties("matchList")
    @Schema(description = "Арена на которой проходит матч")
    private Arena arena;
}
