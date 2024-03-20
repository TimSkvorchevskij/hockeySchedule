package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "team")
@Schema(description = "Сущность команды")
public class Team {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private Integer id;
    private String teamName;
    @JsonIgnoreProperties("teamList")
    @ManyToMany(mappedBy = "teamList", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Schema(description = "Список матчей для команды")
    private List<Match> matchList;
}
