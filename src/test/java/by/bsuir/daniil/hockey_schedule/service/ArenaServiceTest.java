package by.bsuir.daniil.hockey_schedule.service;

import by.bsuir.daniil.hockey_schedule.cache.CacheManager;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTO;
import by.bsuir.daniil.hockey_schedule.dto.arena.ArenaDTOWithMatch;
import by.bsuir.daniil.hockey_schedule.exception.BadRequestException;
import by.bsuir.daniil.hockey_schedule.exception.ResourceNotFoundException;
import by.bsuir.daniil.hockey_schedule.model.Arena;
import by.bsuir.daniil.hockey_schedule.model.Match;
import by.bsuir.daniil.hockey_schedule.repository.ArenaRepository;
import by.bsuir.daniil.hockey_schedule.repository.MatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ArenaServiceTest {
    @Mock
    private ArenaRepository arenaRepository;
    @Mock
    private CacheManager<String, Object> cacheManager;
    @Mock
    private MatchRepository matchRepository;
    @InjectMocks
    private ArenaService arenaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllArenas() {
        List<Arena> arenas = new ArrayList<>();
        arenas.add(new Arena());
        arenas.add(new Arena());

        when(arenaRepository.findAllWithMatchListAndTeamList()).thenReturn(arenas);
        List<ArenaDTOWithMatch> result = arenaService.getAllArenas();
        assertEquals(2, result.size());

        when(arenaRepository.findAllWithMatchListAndTeamList()).thenReturn(null);
        List<ArenaDTOWithMatch> result2 = arenaService.getAllArenas();
        assertTrue(result2.isEmpty());
    }

    @Test
    void testGetArenaById() {
        Arena arena = new Arena();
        arena.setId(1);
        arena.setCity("TestCity");
        arena.setCapacity(100);
        ArenaDTO arenaDTO = new ArenaDTO();
        arenaDTO.setId(2);

        when(cacheManager.get(anyString())).thenReturn(arenaDTO);
        assertEquals(arenaDTO.getId(),arenaService.getArenaById(2).getId());


        when(cacheManager.get(anyString())).thenReturn(null);
        when(arenaRepository.findById(1)).thenReturn(Optional.of(arena));
        ArenaDTO result = arenaService.getArenaById(1);

        assertEquals("TestCity", result.getCity());
        assertEquals(100, result.getCapacity());

        when(arenaRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            arenaService.getArenaById(1);
        });

    }

    @Test
    void testGetArenaByCapacity() {
        Integer minValue = 10;
        Integer maxValue = 20;
        List<Arena> arenas = new ArrayList<>();
        arenas.add(new Arena());
        arenas.add(new Arena());

        when(arenaRepository.findArenaByMinAndMaxCapacity(minValue, maxValue)).thenReturn(arenas);
        List<ArenaDTO> result = arenaService.getArenaByCapacity(minValue, maxValue);
        assertEquals(2, result.size());

        when(arenaRepository.findArenaByMinAndMaxCapacity(null,maxValue)).thenReturn(arenas);
        List<ArenaDTO> result2 = arenaService.getArenaByCapacity(null, maxValue);
        assertEquals(0, result2.size());


        List<ArenaDTO> resultBadRequest = arenaService.getArenaByCapacity(maxValue, minValue);
        assertEquals(0, resultBadRequest.size());



    }

    @Test
    void testDeleteArena() {
        Arena arena = new Arena();
        arena.setId(1);
        Match match1 = new Match();
        match1.setArena(arena);
        match1.setId(1);
        List<Match> matchList = new ArrayList<>();
        matchList.add(match1);

        Match match2 = new Match();
        match2.setArena(arena);
        match2.setId(2);
        matchList.add(match2);
        arena.setMatchList(matchList);

        // Настроим макеты репозиториев
        when(arenaRepository.findById(1)).thenReturn(Optional.of(arena));
        arenaService.deleteArena(1);
        verify(arenaRepository, times(1)).deleteById(1);
        verify(matchRepository, times(2)).save(any(Match.class));
        verify(cacheManager, times(1)).remove(anyString());
    }

    @Test
    void testDeleteArenaError() {
        when(arenaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            arenaService.deleteArena(1);
        });
    }

    @Test
    void testUpdate() {
        // Готовим данные для теста
        Arena arena = new Arena();
        arena.setId(1);
        arena.setCity("TestCity");
        arena.setCapacity(100);

        // Устанавливаем ожидаемое поведение репозитория
        when(arenaRepository.findById(1)).thenReturn(Optional.of(arena));

        // Выполняем метод, который тестируем
        ArenaDTO result = arenaService.update(1, "NewCity", 200);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("NewCity", result.getCity());
        assertEquals(200, result.getCapacity());


        when(arenaRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            arenaService.update(2, "newCity", 200);
        });
    }

    @Test
    void testCreateArena() {
        // Arrange
        Arena arena = new Arena();
        arena.setId(1);
        arena.setCity("Test City");

        when(arenaRepository.save(any())).thenReturn(arena);

        // Act
        ArenaDTO result = arenaService.createArena(arena);

        // Assert
        verify(arenaRepository, times(1)).save(arena);
        verify(cacheManager, times(1)).put(anyString(), any());
        assertNotNull(result);
        assertEquals(arena.getId(), result.getId());
        assertEquals(arena.getCity(), result.getCity());
    }

    @Test
    void testCreateArenaEmptyCity() {
        Arena arena = new Arena();
        arena.setId(1);

        assertThrows(BadRequestException.class, () -> arenaService.createArena(arena));
        verify(arenaRepository, never()).save(arena);
        verify(cacheManager, never()).put(anyString(), any());
    }
}
