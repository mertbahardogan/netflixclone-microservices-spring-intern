package com.microservices.netflix.film.controller.api;

import com.microservices.netflix.common.entities.Film;

import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("api/films/")
@RequestMapping("film-controller/")
@CrossOrigin
public class FilmsController {

    private final FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("findAll")
    public DataResult<List<Film>> findAll() {
        return this.filmService.findAll();
    }

    @GetMapping("findById")
    public DataResult<Optional<Film>> findById(@RequestParam Long id) {
        return this.filmService.findById(id);
    }

    @PostMapping("add")
    public Result add(@RequestBody Film film)  {
        return this.filmService.add(film);
    }

    @PutMapping("update")
    public Result update(@RequestParam Long id, @RequestBody Film film)  {
        return this.filmService.update(id, film);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestParam Long id) {
        return this.filmService.delete(id);
    }

    @PatchMapping("setActive")
    public Result setActive(@RequestParam Long id)  {
        return this.filmService.setActive(id);
    }

    @PatchMapping("setPassive")
    public Result setPassive(@RequestParam Long id)  {
        return this.filmService.setPassive(id);
    }

    @GetMapping("findActiveFilmsFromUserService")
    public DataResult<List<Film>> findActiveFilmsFromUserService() {
        return this.filmService.findActiveFilmsFromUserService();
    }
}
