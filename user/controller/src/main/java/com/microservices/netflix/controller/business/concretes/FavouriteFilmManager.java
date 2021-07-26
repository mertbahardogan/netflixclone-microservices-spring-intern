package com.microservices.netflix.controller.business.concretes;

import com.microservices.netflix.controller.business.abstracts.FavouriteFilmService;
import com.microservices.netflix.controller.dataAccess.FavouriteFilmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteFilmManager implements FavouriteFilmService {
    private final FavouriteFilmDao favouriteFilmDao;

    @Autowired
    public FavouriteFilmManager(FavouriteFilmDao favouriteFilmDao) {
        this.favouriteFilmDao = favouriteFilmDao;
    }



}
