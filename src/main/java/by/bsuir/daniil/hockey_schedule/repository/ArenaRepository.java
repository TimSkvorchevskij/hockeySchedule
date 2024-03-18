package by.bsuir.daniil.hockey_schedule.repository;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArenaRepository extends JpaRepository<Arena, Integer> {
    @Query("SELECT a FROM Arena a WHERE a.capacity <= :maxValue ORDER BY a.capacity DESC")
    List<Arena> findArenaByMaxCapacity(@Param("maxValue") Integer maxValue);
    @Query("SELECT a FROM Arena a WHERE a.capacity >= :minValue ORDER BY a.capacity ASC")
    List<Arena> findArenaByMinCapacity(@Param("minValue") Integer minValue);

    @Query(value = "SELECT * FROM arenas WHERE capacity BETWEEN :minValue AND :maxValue ORDER BY capacity ASC", nativeQuery = true)
    List<Arena> findArenaByMinAndMaxCapacity(@Param("minValue") Integer minValue, @Param("maxValue") Integer maxValue);
    @Query("SELECT DISTINCT a FROM Arena a LEFT JOIN FETCH a.matchList m LEFT JOIN m.teamList")
    List<Arena> findAllWithMatchListAndTeamList();
}
