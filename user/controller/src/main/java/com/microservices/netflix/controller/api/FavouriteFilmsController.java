package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.FavouriteFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/favourite")
public class FavouriteFilmsController{

    private final FavouriteFilmService favouriteFilmService;

    @Autowired
    public FavouriteFilmsController(FavouriteFilmService favouriteFilmService) {
        this.favouriteFilmService = favouriteFilmService;
    }

    @GetMapping()
    public DataResult<List<FavouriteFilm>> findFavouriteFilms() {
        return this.favouriteFilmService.findFavouriteFilms();
    }

    @GetMapping("/{userId}")
    public  DataResult<List<FavouriteFilm>> findFavouriteFilmsByIsActiveAndUserId(@PathVariable(value = "userId") int userId) {
        return this.favouriteFilmService.findFavouriteFilmsByIsActiveAndUserId(userId);
    }

    @PostMapping()
    public Result addToFav(@RequestBody FavouriteFilm favouriteFilm) {
        return this.favouriteFilmService.addToFav(favouriteFilm);
    }

    @DeleteMapping(value = "/{id}")
    public Result deleteFromFav(@PathVariable(value = "id") Long id) {
        return this.favouriteFilmService.deleteFromFav(id);
    }
}
