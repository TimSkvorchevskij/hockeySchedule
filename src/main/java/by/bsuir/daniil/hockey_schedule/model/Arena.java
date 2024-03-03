package by.bsuir.daniil.hockey_schedule.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Data
@Entity
@Table(name = "arenas")
public class Arena {
    @Id
    @Column(unique = true)
    Integer arenaId;
    String city;
    Integer capacity;
//    @JsonIgnore
//    @JsonBackReference
//    @JsonManagedReference
//    @JsonIgnoreProperties("arena")
    @OneToMany(mappedBy = "arena", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH} )
    List<Match> matchList;
}
