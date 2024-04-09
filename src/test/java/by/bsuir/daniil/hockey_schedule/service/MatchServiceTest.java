package by.bsuir.daniil.hockey_schedule.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithArena;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.exception.BadRequestException;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import by.bsuir.daniil.hockey_schedule.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private ArenaRepository arenaRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private CacheManager<String, Object> cacheManager;

    @InjectMocks
    private MatchService matchService;

    @Test
    public void testGetAllMatches() {
        List<Match> matches = new ArrayList<>();
        Match match1 = new Match();
        Match match2 = new Match();
        match1.setId(1);
        match2.setId(2);
        matches.add(match1);
        matches.add(match2);

        when(matchRepository.findAll()).thenReturn(matches);
        List<MatchDTOWithTeamAndArena> result = matchService.getAllMatches();
        assertEquals(2, result.size());

        when(matchRepository.findAll()).thenReturn(Collections.emptyList());
        List<MatchDTOWithTeamAndArena> resultWithEmptyList = matchService.getAllMatches();
        assertEquals(0, resultWithEmptyList.size());
    }

    @Test
    public void testAddMatch() {
        Match match = new Match();
        when(matchRepository.save(match)).thenReturn(match);
        MatchDTOWithTeamAndArena result = matchService.addMatch(match);
        assertNotNull(result);

        Match matchWithTeams = new Match();
        matchWithTeams.setTeamList(new ArrayList<>());
        matchWithTeams.getTeamList().add(new Team());
        matchWithTeams.getTeamList().add(new Team());

        when(teamRepository.save(any())).thenReturn(any());
        when(matchRepository.save(matchWithTeams)).thenReturn(matchWithTeams);

        MatchDTOWithTeamAndArena resultWithTeams = matchService.addMatch(matchWithTeams);
        assertEquals(2, resultWithTeams.getTeamDTOList().size());

        matchWithTeams.getTeamList().add(new Team());
        Assertions.assertThrows(BadRequestException.class, () -> {
            matchService.addMatch(matchWithTeams);
        });
    }

    @Test
    public void testDeleteMatch() {
        Integer delMatchId = 1;
        Match delMatch = new Match();
        when(matchRepository.findById(1)).thenReturn(Optional.of(delMatch));
        assertDoesNotThrow(() -> matchService.deleteMatch(delMatchId));

        when(matchRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            matchService.deleteMatch(2);
        });
    }

    @Test
    public void testSetNewArena() {
        Integer matchId = 1;
        Integer newArenaId = 1;
        Match match = new Match();
        Arena arena = new Arena();
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(arenaRepository.findById(newArenaId)).thenReturn(Optional.of(arena));
        MatchDTOWithArena result = matchService.setNewArena(matchId, newArenaId);
        assertNotNull(result);

        when(matchRepository.findById(2)).thenReturn(Optional.empty());
        when(arenaRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            matchService.setNewArena(2,2);
        });
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            matchService.setNewArena(matchId,2);
        });
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            matchService.setNewArena(2,newArenaId);
        });
    }

    @Test
    public void testFindById() {
        Integer id = 1;

        when(matchRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            matchService.findById(id);
        });

        MatchDTOWithTeamAndArena matchDTO = new MatchDTOWithTeamAndArena();
        when(cacheManager.get("matchDTOWithTeamAndArena_" + id)).thenReturn(matchDTO);
        MatchDTOWithTeamAndArena result = matchService.findById(id);
        assertNotNull(result);

        when(cacheManager.get("matchDTOWithTeamAndArena_" + id)).thenReturn(null);
        Match match = new Match();
        match.setId(id);
        matchDTO.setId(id);
        matchDTO.setTeamDTOList(new ArrayList<>());
        when(matchRepository.findById(id)).thenReturn(Optional.of(match));
        when(cacheManager.put("matchDTOWithTeamAndArena_" + id, matchDTO)).thenReturn(null);
        MatchDTOWithTeamAndArena result2 = matchService.findById(id);
        assertNotNull(result2);




    }
}
