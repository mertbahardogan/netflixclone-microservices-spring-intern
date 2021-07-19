package com.microservices.netflix.favourite.process.business.abstracts;

import com.microservices.netflix.common.entities.FavouriteFilm;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteProcessService {
    void addToFav(FavouriteFilm favouriteFilm);

    void deleteFromFav(Long id);
}
