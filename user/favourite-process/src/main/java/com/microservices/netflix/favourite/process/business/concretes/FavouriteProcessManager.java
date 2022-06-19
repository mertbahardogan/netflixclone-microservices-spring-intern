package com.microservices.netflix.favourite.process.business.concretes;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.favourite.process.business.abstracts.FavouriteProcessService;
import com.microservices.netflix.favourite.process.dataAccess.FavouriteProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteProcessManager implements FavouriteProcessService {

    private final FavouriteProcessDao  favouriteProcessDao;

    @Autowired
    public FavouriteProcessManager(FavouriteProcessDao favouriteProcessDao) {
        this.favouriteProcessDao = favouriteProcessDao;
    }


    @Override
    public void addToFav(FavouriteFilm favouriteFilm) {
        this.favouriteProcessDao.save(favouriteFilm);
    }

    @Override
    public void deleteFromFav(Long id) {
        this.favouriteProcessDao.deleteById(id);
    }
}
