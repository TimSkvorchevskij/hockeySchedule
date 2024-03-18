package by.bsuir.daniil.hockey_schedule.repository;

import by.bsuir.daniil.hockey_schedule.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {


}
