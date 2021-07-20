package com.microservices.netflix.film.process.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import com.microservices.netflix.film.process.dataAccess.FilmProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmProcessManager implements FilmProcessService {
    private final FilmProcessDao processDao;

    @Autowired
    public FilmProcessManager(FilmProcessDao processDao) {
        this.processDao = processDao;
    }

    @Override
    public void add(Film film) {
        this.processDao.save(film);
    }

    @Override
    public void update(Long id, Film film) {
        Film value = this.processDao.findById(id).get();
        value.setName(film.getName());
        value.setSummary(film.getSummary());
        value.setCoverPhoto(film.getCoverPhoto());
        value.setSpeakLanguage(film.getSpeakLanguage());
        value.setTime(film.getTime());
        value.setCategory(film.getCategory());
        value.setCreatedBy(film.getCreatedBy());
        value.setEditedBy(film.getEditedBy());
        value.setCreated(film.getCreated());
        value.setEdited(film.getEdited());
        value.setDeleted(film.getDeleted());
        this.processDao.save(value);
    }

    @Override
    public void deleteById(Long id) {
        this.processDao.deleteById(id);
    }

    @Override
    public void setActive(Long id) {
        Film value = this.processDao.findById(id).get();
        if (value.isActive()==false){
            value.setActive(true);
            this.processDao.save(value);
        }
        else
            System.out.println("SET ACTIVE ÇALIŞAMAZ ÇÜNKÜ ZATEN TRUE!");
    }

    @Override
    public void setPassive(Long id) {
        Film value = this.processDao.findById(id).get();
        if (value.isActive()==true){
            value.setActive(false);
            this.processDao.save(value);
        }
        else
            System.out.println("SET ACTIVE ÇALIŞAMAZ ÇÜNKÜ ZATEN FALSE!");

    }
}
