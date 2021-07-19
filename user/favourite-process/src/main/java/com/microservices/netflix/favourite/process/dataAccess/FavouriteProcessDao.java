package com.microservices.netflix.favourite.process.dataAccess;

import com.microservices.netflix.common.entities.FavouriteFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavouriteProcessDao extends JpaRepository<FavouriteFilm,Long> {
    void deleteById(Long id);
}
