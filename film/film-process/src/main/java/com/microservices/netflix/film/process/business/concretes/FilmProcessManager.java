package com.microservices.netflix.film.process.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import com.microservices.netflix.film.process.dataAccess.FilmProcessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public Film add(Film film) {
        Assert.notNull(film.getName(), "İsim alanı boş olamaz.");
        OffsetDateTime offsetDTA = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
        film.setCreated(offsetDTA);
        final Film filmDB = this.processDao.save(film);
        film.setId(filmDB.getId());
        logger.info("New Film Added!" + film.getId() + "!" + film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        Film valueUpdate = this.processDao.findById(film.getId()).get();
        valueUpdate.setName(film.getName());
        valueUpdate.setSummary(film.getSummary());
        valueUpdate.setCoverPhoto(film.getCoverPhoto());
        valueUpdate.setSpeakLanguage(film.getSpeakLanguage());
        valueUpdate.setTime(film.getTime());
        valueUpdate.setCategory(film.getCategory());
        valueUpdate.setActive(film.isActive());

        OffsetDateTime offsetDTU = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
        valueUpdate.setEdited(offsetDTU);
        this.processDao.save(valueUpdate);
        logger.info("Film Updated!" + valueUpdate.getId() + "!" + valueUpdate.getName());
        return film;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Film valueDelete = this.processDao.findById(id).get();
            valueDelete.setActive(false);
            OffsetDateTime offsetDTD = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+03:00"));
            valueDelete.setDeleted(offsetDTD);
            logger.info("Film Deleted!" + id);
            this.processDao.save(valueDelete);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean settingActive(Long id) {
        try {
            Film value = this.processDao.findById(id).get();
            value.setActive(true);
            logger.info("Film Actived!" + id);
            this.processDao.save(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean settingPassive(Long id) {
      try {
          Film value = this.processDao.findById(id).get();
          value.setActive(false);
          logger.info("Film Passived!" + id);
          this.processDao.save(value);
          return true;
      }
      catch (Exception e){
          return false;
      }
    }
}
