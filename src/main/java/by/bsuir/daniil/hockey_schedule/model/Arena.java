package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
//import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "arenas")
@Schema(description = "Арены")
public class Arena {
    @Id
    @GeneratedValue
    private Integer id;
    private String city;
    private Integer capacity;
    @JsonIgnoreProperties("arena")
    @OneToMany(mappedBy = "arena", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @Schema(description = "Список матчей арене")
    private List<Match> matchList;
}
