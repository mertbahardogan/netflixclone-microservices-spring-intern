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
@RequestMapping("/film")
public class FilmsController {

    private final FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping()
    public DataResult<List<Film>> findAll() {
        return this.filmService.findAll();
    }

    @GetMapping("/active")
    public DataResult<List<Film>> findAllByIsActive() {
        return this.filmService.findAllByIsActive();
    }

    @GetMapping("/passive")
    public DataResult<List<Film>> findAllByIsNotActive() {
        return this.filmService.findAllByIsNotActive();
    }

    @GetMapping("/deleted")
    public DataResult<List<Film>> findAllByDeletedIsNotNull() {
        return this.filmService.findAllByDeletedIsNotNull();
    }

    @GetMapping(value = "/{id}")
    public DataResult<Optional<Film>> findById(@PathVariable(value = "id") Long id) {
        return this.filmService.findById(id);
    }

    @PostMapping()
    public Result add(@RequestBody Film film) {
        return this.filmService.add(film);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Long id, @RequestBody Film film) {
        return this.filmService.update(id, film);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Long id) {
        return this.filmService.delete(id);
    }

    @PatchMapping("/active/{id}")
    public Result setActive(@PathVariable(value = "id") Long id) {
        return this.filmService.setActive(id);
    }

    @PatchMapping("/passive/{id}")
    public Result setPassive(@PathVariable(value = "id") Long id) {
        return this.filmService.setPassive(id);
    }

    @GetMapping("/feign")
    public DataResult<List<Film>> findActiveFilmsFromUserService() {
        return this.filmService.findActiveFilmsFromUserService();
    }
}
