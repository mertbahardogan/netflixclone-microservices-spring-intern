package com.microservices.netflix.film.controller.api;

import com.microservices.netflix.common.entities.Film;

import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Film> findAll() {
        return this.filmService.findAll();
    }


    @GetMapping("findById")
    public Optional<Film> findById(@RequestParam Long id) {
        return this.filmService.findById(id);
    }

    @PostMapping("add")
    public void add(@RequestBody Film film){
        try {
            this.filmService.add(film);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
