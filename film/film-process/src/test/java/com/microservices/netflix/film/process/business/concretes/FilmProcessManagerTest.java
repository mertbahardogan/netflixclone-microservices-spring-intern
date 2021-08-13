package com.microservices.netflix.film.process.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.film.process.dataAccess.FilmProcessDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmProcessManagerTest {
    @InjectMocks
    private FilmProcessManager filmProcessManager;

    @Mock
    private FilmProcessDao processDao;

    @Test
    void testAdd() {
        Film film = new Film();
        film.setName("Test-Name");
        film.setCategory("Test-Category");
        film.setSummary("Test-Summary");
        film.setCoverPhoto("Test-Cover");
        film.setSpeakLanguage("Test-Lang");
        film.setTime("1");
        film.setActive(true);

        Film filmMock = Mockito.mock(Film.class);

        when(filmMock.getId()).thenReturn(1L);
        when(processDao.save(ArgumentMatchers.any(Film.class))).thenReturn(filmMock);
        Film result = filmProcessManager.add(film);

        assertEquals(result.getName(), film.getName());
        assertEquals(result.getId(), 1L);
    }


    @Test
    void testAddByException() {
        Film film = new Film();
//        film.setName("Test-Name");
        film.setCategory("Test-Category");
        film.setSummary("Test-Summary");
        film.setCoverPhoto("Test-Cover");
        film.setSpeakLanguage("Test-Lang");
        film.setTime("1");
        film.setActive(true);

        Assertions.assertThrows(IllegalArgumentException.class,()->filmProcessManager.add(film));
    }

    @Test
    void testUpdate() {
        // No value present hatası.
        Film film = new Film();
        film.setId(1L);
        film.setName("Test-Name");
        film.setCategory("Test-Category");
        film.setSummary("Test-Summary");
        film.setCoverPhoto("Test-Cover");
        film.setSpeakLanguage("Test-Lang");
        film.setTime("1");
        film.setActive(true);
        Film filmMock = Mockito.mock(Film.class);

        when(filmMock.getId()).thenReturn(1L);
        when(processDao.save(ArgumentMatchers.any(Film.class))).thenReturn(filmMock);
        when(processDao.findById(filmMock.getId()).get()).thenReturn(filmMock);
        Film result = filmProcessManager.update(1L,film);

        assertEquals(result.getName(), film.getName());
        assertEquals(result.getId(), 1L);
    }
}

