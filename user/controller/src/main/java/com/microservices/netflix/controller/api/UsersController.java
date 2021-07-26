package com.microservices.netflix.controller.api;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.controller.business.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users/")
@CrossOrigin
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("findAllByIsActive")
    public DataResult<List<Film>> findAllByIsActive() {
        return this.userService.findAllByIsActive();
    }

    @GetMapping("findByIsActiveAndId")
    public  DataResult<Optional<Film>> findAllByIsActive(@RequestParam Long id) {
        return this.userService.findByIsActiveAndId(id);
    }

    @GetMapping("findFavouriteFilms")
    public DataResult<List<FavouriteFilm>> findFavouriteFilms() {
        return this.userService.findFavouriteFilms();
    }

    @GetMapping("findFavouriteFilmsByIsActiveAndUserId")
    public  DataResult<List<FavouriteFilm>> findFavouriteFilmsByIsActiveAndUserId(@RequestParam int userId) {
        return this.userService.findFavouriteFilmsByIsActiveAndUserId(userId);
    }

    @PostMapping("addToFav")
    public Result addToFav(@RequestBody FavouriteFilm favouriteFilm) throws IOException {
        return this.userService.addToFav(favouriteFilm);
    }

    @DeleteMapping("deleteFromFav")
    public Result deleteFromFav(@RequestParam int id) throws IOException {
        return this.userService.deleteFromFav(id);
    }
}
