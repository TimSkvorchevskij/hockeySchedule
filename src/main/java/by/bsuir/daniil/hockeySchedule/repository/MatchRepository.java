package by.bsuir.daniil.hockeySchedule.repository;

import by.bsuir.daniil.hockeySchedule.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> findByAwayTeamOrHostTeam(String awayTeam, String hostTeam);  /// ????????
//    @Override
//    Match findByMatchId(Integer id);
//    List<Match> findByAwayTeamOr(String awayTeam);
//    Match findByDate (ZonedDateTime date){
//    }
//    boolean delete(Match delMach){
//
}
