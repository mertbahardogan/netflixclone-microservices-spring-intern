package com.microservices.netflix.controller.dataAccess;

import com.microservices.netflix.common.entities.FavouriteFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteFilmDao extends JpaRepository<FavouriteFilm,Long> {

    List<FavouriteFilm> findAll();

    @Query("FROM FavouriteFilm WHERE userId=:userId and film.id=:filmId")
    Optional<FavouriteFilm> findByUserIdAndFilm(int userId,Long filmId);

    @Query("FROM FavouriteFilm f JOIN UserProcess u ON f.id=u.id JOIN Film fs ON u.film.id=fs.id AND u.film.isActive=true")
    List<FavouriteFilm> findByIsActiveAndUserId(int userId);
}
