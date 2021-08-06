package com.microservices.netflix.film.process.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import com.microservices.netflix.film.process.dataAccess.FilmProcessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class FilmProcessManager implements FilmProcessService {

    private final FilmProcessDao processDao;
    Logger logger = LoggerFactory.getLogger(FilmProcessManager.class);

    @Autowired
    public FilmProcessManager(FilmProcessDao processDao) {
        this.processDao = processDao;
    }

    @Override
    public void add(Film film) {
        OffsetDateTime offsetDTA = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
        film.setCreated(offsetDTA);
        logger.info("New Film Added!");
        this.processDao.save(film);
    }

    @Override
    public void update(Long id, Film film) {
        Film valueUpdate = this.processDao.findById(id).get();
        valueUpdate.setName(film.getName());
        valueUpdate.setSummary(film.getSummary());
        valueUpdate.setCoverPhoto(film.getCoverPhoto());
        valueUpdate.setSpeakLanguage(film.getSpeakLanguage());
        valueUpdate.setTime(film.getTime());
        valueUpdate.setCategory(film.getCategory());
        valueUpdate.setActive(film.isActive());

        OffsetDateTime offsetDTU = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
        valueUpdate.setEdited(offsetDTU);
        logger.info("Film Updated!");
        this.processDao.save(valueUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Film valueDelete = this.processDao.findById(id).get();
        valueDelete.setActive(false);
        OffsetDateTime offsetDTD = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
        valueDelete.setDeleted(offsetDTD);
        logger.info("Film Deleted!");
        this.processDao.save(valueDelete);
    }

    @Override
    public void settingActive(Long id) {
        Film value = this.processDao.findById(id).get();
        value.setActive(true);
        logger.info("Film Actived!");
        this.processDao.save(value);
    }

    @Override
    public void settingPassive(Long id) {
        Film value = this.processDao.findById(id).get();
        value.setActive(false);
        logger.info("Film Passived!");
        this.processDao.save(value);
    }
}
