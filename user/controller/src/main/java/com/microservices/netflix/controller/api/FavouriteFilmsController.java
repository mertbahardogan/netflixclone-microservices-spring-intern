package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.FavouriteFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/favourite-films/")
@CrossOrigin
public class FavouriteFilmsController{

    private final FavouriteFilmService favouriteFilmService;

    @Autowired
    public FavouriteFilmsController(FavouriteFilmService favouriteFilmService) {
        this.favouriteFilmService = favouriteFilmService;
    }


    @GetMapping("findFavouriteFilms")
    public DataResult<List<FavouriteFilm>> findFavouriteFilms() {
        return this.favouriteFilmService.findFavouriteFilms();
    }

    @GetMapping("findFavouriteFilmsByIsActiveAndUserId")
    public  DataResult<List<FavouriteFilm>> findFavouriteFilmsByIsActiveAndUserId(@RequestParam int userId) {
        return this.favouriteFilmService.findFavouriteFilmsByIsActiveAndUserId(userId);
    }

    @PostMapping("addToFav")
    public Result addToFav(@RequestBody FavouriteFilm favouriteFilm) throws IOException {
        return this.favouriteFilmService.addToFav(favouriteFilm);
    }

    @DeleteMapping("deleteFromFav")
    public Result deleteFromFav(@RequestParam int id) throws IOException {
        return this.favouriteFilmService.deleteFromFav(id);
    }
}
