package com.microservices.netflix.film.controller.api;

import com.microservices.netflix.common.entities.Film;

import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films/")
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
    public Result add(@RequestBody Film film) throws IOException {
        return this.filmService.add(film);
    }

    @PutMapping("update")
    public Result update(@RequestParam Long id, @RequestBody Film film) throws IOException {
        return this.filmService.update(id, film);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestParam Long id) throws IOException {
        return this.filmService.delete(id);
    }
}
