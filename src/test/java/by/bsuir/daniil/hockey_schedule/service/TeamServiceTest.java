package by.bsuir.daniil.hockey_schedule.service;


import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
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

class TeamServiceTest {

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
    void testFindTeamById() {
        Integer teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        team.setTeamName("Meal");
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        TeamDTOWithMatch teamDTOWithMatch = teamService.findTeamById(teamId);
        assertEquals(team.getId(), teamDTOWithMatch.getId());
        assertEquals(team.getTeamName(), teamDTOWithMatch.getTeamName());
    }
    @Test
    void testFindTeamByIdThrowsResourceNotFoundException() {
        Integer teamId = 1;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> teamService.findTeamById(teamId));
    }

    @Test
    void deleteTeamTest() {
        // Arrange
        Integer teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        List<Team> teamList = new ArrayList<>();
        teamList.add(team);

        Match match1 = new Match();
        match1.setId(1);
        match1.setTeamList(teamList);


        Match match2 = new Match();
        match2.setId(2);
        match2.setTeamList(teamList);

        List<Match> matchList = new ArrayList<>();
        matchList.add(match1);
        matchList.add(match2);
        team.setMatchList(matchList);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        teamService.deleteTeam(teamId);

        verify(teamRepository, times(1)).deleteById(teamId);
        verify(matchRepository, times(2)).save(any(Match.class));
        verify(cacheManager, times(1)).remove(anyString());
    }

    @Test
    void deleteTeamTestExceptionResourceNotFound() {
        Integer teamId = 1;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeam(teamId));
    }

    @Test
    void addMatchInMatchListTest() {
        Integer teamId = 1;
        Integer matchId = 1;
        Team team = new Team();
        team.setId(teamId);
        Match match = new Match();
        match.setTeamList(new ArrayList<>());

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        Assertions.assertNotNull(teamService.addMatchInMatchList(teamId,matchId));


        Integer matchIdWithOneTeam = 3;
        Match matchWithOneTeam = new Match();
        matchWithOneTeam.setId(3);
        matchWithOneTeam.setTeamList(new ArrayList<>());
        matchWithOneTeam.getTeamList().add(new Team());

        when(matchRepository.findById(matchIdWithOneTeam)).thenReturn(Optional.of(matchWithOneTeam));
        when(matchRepository.save(matchWithOneTeam)).thenReturn(matchWithOneTeam);

        Assertions.assertNotNull(teamService.addMatchInMatchList(teamId,matchIdWithOneTeam));
    }

    @Test
    void testAddMatchInMatchListWithError() {
        Integer teamId = 1;
        Integer matchId = 1;
        Team team = new Team();
        team.setId(teamId);
        Match match = new Match();
        match.setId(matchId);
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        assertThrows(ResourceNotFoundException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchId));

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchId));

        List<Team> teamList = new ArrayList<>();
        teamList.add(team);
        match.setTeamList(teamList);
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        assertThrows(BadRequestException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchId));

        List<Team> teamListWithTwoTeams = new ArrayList<>();
        teamListWithTwoTeams.add(new Team());
        teamListWithTwoTeams.add(new Team());
        match.setTeamList(teamListWithTwoTeams);
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        assertThrows(BadRequestException.class, ()
                -> teamService.addMatchInMatchList(teamId, matchId));

    }

    @Test
    void testDelMatchInMatchList() {
        Integer teamId = 1;
        Integer matchId = 1;

        Team team = new Team();
        team.setId(teamId);

        Match match = new Match();
        match.setId(matchId);
        match.setTeamList(new ArrayList<>());
        match.getTeamList().add(team);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        TeamDTO result = teamService.delMatchInMatchList(teamId, matchId);

        verify(matchRepository, times(1)).findById(matchId);
        verify(teamRepository, times(2)).findById(teamId);
        verify(matchRepository, times(1)).save(match);
        assertEquals(teamId, result.getId());

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()
                -> teamService.delMatchInMatchList(teamId, matchId));

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()
                -> teamService.delMatchInMatchList(teamId, matchId));

    }

    @Test
    void testAddMultipleCommands() {
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        ArrayList<Team> teamList = new ArrayList<>();
        teamList.add(team1);
        teamList.add(team2);

        when(teamRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<TeamDTO> result = teamService.addMultipleCommands(teamList);

        verify(teamRepository, times(2)).save(any());
        assertEquals(2, result.size());
        assertEquals(team1.getId(), result.get(0).getId());
        assertEquals(team2.getId(), result.get(1).getId());
    }

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