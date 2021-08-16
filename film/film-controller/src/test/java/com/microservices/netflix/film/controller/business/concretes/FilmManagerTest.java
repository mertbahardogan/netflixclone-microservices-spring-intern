package com.microservices.netflix.film.controller.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.service.services.UserControllerClient;
import com.microservices.netflix.film.controller.dataAccess.FilmDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmManagerTest {

    @InjectMocks
    private FilmManager filmManager;

    @Mock
    private FilmDao filmDao;

    @Mock
    private UserControllerClient userControllerClient;


    @Test
    void testFindAll() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test-Name");
        film.setCategory("Test-Category");
        film.setSummary("Test-Summary");
        film.setCoverPhoto("Test-Cover");
        film.setSpeakLanguage("Test-Lang");
        film.setTime("1");
        film.setActive(true);

        when(filmDao.findAll()).thenReturn(Collections.singletonList(film));
        DataResult<List<Film>> filmList=filmManager.findAll();
        assertEquals(filmList.getData().size(),1);

        assertEquals(filmList.getCustomStatusCode(),200);
        assertEquals(filmList.getData().get(0).getId(),film.getId());
    }

    @Test
    void testFindAllException(){
        when(filmDao.findAll()).thenReturn(Collections.singletonList(null));
        DataResult<List<Film>> filmList=filmManager.findAll();

        assertEquals(filmList.getData(),null);
        assertEquals(filmList.getCustomStatusCode(),80007);
    }

    @Test
    void testFindById() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test-Name");
        film.setCategory("Test-Category");
        film.setSummary("Test-Summary");
        film.setCoverPhoto("Test-Cover");
        film.setSpeakLanguage("Test-Lang");
        film.setTime("1");
        film.setActive(true);

        when(filmDao.findById(1L)).thenReturn(Optional.of(film));
        Film filmList=filmManager.findById(1L).getData().get();
        assertEquals(filmList.getId(),film.getId());
    }

    @Test
    void testFindByIdException(){
        when(filmDao.findById(1L)).thenReturn(null);
        DataResult<Optional<Film>> filmList=filmManager.findById(1L);
        assertEquals(filmList.getData(),null);
        assertEquals(filmList.getCustomStatusCode(),80007);
    }

    @Test
    void testFindActiveFilmsFromUserService(){
        when(userControllerClient.findAllByIsActive()).thenReturn(null);
        DataResult<List<Film>> filmList=filmManager.findActiveFilmsFromUserService();
        assertEquals(filmList.getCustomStatusCode(),80007);
    }
}