package com.microservices.netflix.film.controller.api;

import com.microservices.netflix.common.entities.Film;

import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//Erişim seçenekleri:
//localhost:54279/film-controller/api/findAll
//localhost:8088/film-controller/api/findAll
//Swagger:http://localhost:8088/film-controller/swagger-ui.html#/
@RequestMapping("api/")
@CrossOrigin
public class FilmsController {

    private final FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Autowired
    Environment environment;


    @GetMapping("findAll")
    public DataResult<List<Film>> findAll() {
        System.out.println("Let's see SERVER PORT(load balancer): "+ environment.getProperty("local.server.port"));
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
