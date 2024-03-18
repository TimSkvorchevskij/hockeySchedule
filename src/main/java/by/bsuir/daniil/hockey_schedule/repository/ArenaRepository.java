package by.bsuir.daniil.hockey_schedule.repository;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArenaRepository extends JpaRepository<Arena, Integer> {
    @Query("SELECT a FROM Arena a WHERE a.capacity <= :maxValue")
    List<Arena> findArenaByMaxCapacity(Integer maxValue);
    @Query("SELECT a FROM Arena a WHERE a.capacity >= :minValue")
    List<Arena> findArenaByMinCapacity(Integer minValue);
    @Query("SELECT a FROM Arena a WHERE a.capacity >= :minValue AND a.capacity <= :maxValue")
    List<Arena> findArenaByMinAndMaxCapacity(Integer minValue, Integer maxValue);

}
