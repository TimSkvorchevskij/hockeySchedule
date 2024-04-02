package by.bsuir.daniil.hockey_schedule.service;

import static org.junit.Assert.*;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.match.MatchDTOWithTeamAndArena;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTO;
import by.bsuir.daniil.hockey_schedule.dto.team.TeamDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.exception.BadRequestException;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.model.Team;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import by.bsuir.daniil.hockey_schedule.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private CacheManager cacheManager;
    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTeam() {
        Team team = new Team();
        team.setId(1);
        when(teamRepository.save(team)).thenReturn(team);
        TeamDTO teamDTO = teamService.addTeam(team);
        assertEquals(team.getId(), teamDTO.getId());
    }
    @Test
    void addMatchInMatchListTest() {
        Integer teamId = 1;
        Integer matchIdWithTwoTeams = 1;
        Team team = new Team();
        team.setId(teamId);
        Match matchWithTwoTeams = new Match();
        matchWithTwoTeams.setTeamList(new ArrayList<>());
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        when(matchRepository.findById(matchIdWithTwoTeams)).thenReturn(Optional.of(matchWithTwoTeams));
        matchWithTwoTeams.getTeamList().add(new Team());
        matchWithTwoTeams.getTeamList().add(new Team());
        assertThrows(BadRequestException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchIdWithTwoTeams));

        Integer matchIdContainTeam = 2;
        Match matchContainTeam = new Match();
        matchContainTeam.setId(matchIdContainTeam);
        matchContainTeam.setTeamList(new ArrayList<>());
        matchContainTeam.getTeamList().add(team);
        when(matchRepository.findById(matchIdContainTeam)).thenReturn(Optional.of(matchContainTeam));
        assertThrows(BadRequestException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchIdContainTeam));

        Integer matchIdWithOneTeam = 3;
        Match matchWithOneTeam = new Match();
        matchWithOneTeam.setId(3);
        matchWithOneTeam.setTeamList(new ArrayList<>());
        matchWithOneTeam.getTeamList().add(new Team());
        when(matchRepository.findById(matchIdWithOneTeam)).thenReturn(Optional.of(matchWithOneTeam));
        when(matchRepository.save(matchWithOneTeam)).thenReturn(matchWithOneTeam);
        Assertions.assertNotNull(teamService.addMatchInMatchList(teamId,matchIdWithOneTeam));
    }

//    @Test
//    void testFindTeamById() {
//        Integer teamId = 1;
//        Team team = new Team();
//        team.setId(teamId);
//        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
//        TeamDTO teamDTO = teamService.findTeamById(teamId);
//        assertEquals(team.getId(), teamDTO.getId());
//    }

    @Test
    void testFindTeamByIdThrowsResourceNotFoundException() {
        Integer teamId = 1;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamService.findTeamById(teamId));
    }
    @Test
    void testAddMatchInMatchList() {
        Integer teamId = 1;
        Integer matchId = 1;
        Team team = new Team();
        team.setId(teamId);
        Match match = new Match();
        match.setId(matchId);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        TeamDTO teamDTO = teamService.addMatchInMatchList(teamId, matchId);

//        assertNotNull(teamDTO);
        // Проверки могут быть расширены в зависимости от ваших требований
    }

    // Пример теста для метода getAllTeams
    @Test
    void testGetAllTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());
        when(teamRepository.findAll()).thenReturn(teams);


        List<TeamDTOWithMatch> result = teamService.getAllTeams();
        assertEquals(2, result.size());
    }
}