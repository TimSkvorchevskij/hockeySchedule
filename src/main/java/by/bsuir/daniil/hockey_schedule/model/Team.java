package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private Integer id;
    private String teamName;
    @JsonIgnoreProperties("teamList")
    @ManyToMany(mappedBy = "teamList", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Match> matchList;
}
