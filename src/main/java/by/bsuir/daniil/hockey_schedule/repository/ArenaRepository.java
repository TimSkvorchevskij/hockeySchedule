package by.bsuir.daniil.hockey_schedule.repository;

import by.bsuir.daniil.hockey_schedule.model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArenaRepository extends JpaRepository<Arena, Integer> {

}
