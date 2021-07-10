package com.microservices.netflix.film.service.business.concretes;

import com.microservices.netflix.film.service.business.abstracts.FilmService;
import com.microservices.netflix.film.service.dataAccess.abstracts.FilmDao;
import com.microservices.netflix.film.service.entities.concretes.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmManager implements FilmService {

    //?Bean oldu
    @Autowired
    public FilmManager(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    private FilmDao filmDao; //final

    @Override
    public List<Film> getAll() {
        return this.filmDao.findAll();
    }

    @Override
    public Film getById(Long id) {
        return this.filmDao.getById(id);
    }
}
