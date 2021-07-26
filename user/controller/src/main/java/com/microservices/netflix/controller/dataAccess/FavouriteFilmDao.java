package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.FavouriteFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteFilmDao extends JpaRepository<FavouriteFilm,Long> {

    List<FavouriteFilm> findAll();

    @Query("FROM FavouriteFilm f JOIN UserProcess u ON f.id=u.id JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true")
    List<FavouriteFilm> findByIsActiveAndUserId(int userId);
}
