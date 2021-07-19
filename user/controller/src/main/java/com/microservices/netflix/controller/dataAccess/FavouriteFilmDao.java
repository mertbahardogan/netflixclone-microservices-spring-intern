package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.FavouriteFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteFilmDao extends JpaRepository<FavouriteFilm,Long> {

    List<FavouriteFilm> findAll();
}
