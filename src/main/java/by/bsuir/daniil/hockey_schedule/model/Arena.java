package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "arenas")
public class Arena {
    @Id
    @GeneratedValue
    Integer id;
    String city;
    Integer capacity;
    @JsonIgnoreProperties("arena")
    @OneToMany(mappedBy = "arena", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH} )
    List<Match> matchList;
}
