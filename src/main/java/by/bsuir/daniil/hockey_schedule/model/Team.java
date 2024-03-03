package by.bsuir.daniil.hockey_schedule.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "team")
public class Team {
    @Id
    @Column(unique = true)
    private Integer teamId;
    @ManyToMany(mappedBy = "teamList")
    private List<Match> matchList;
}
