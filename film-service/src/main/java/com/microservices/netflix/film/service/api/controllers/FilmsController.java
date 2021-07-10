package com.microservices.netflix.film.service.api.controllers;

import com.microservices.netflix.film.service.business.abstracts.FilmService;
import com.microservices.netflix.film.service.entities.concretes.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/films/")
public class FilmsController {

    private FilmService finalService;

    @Autowired
    public FilmsController(FilmService finalService) {
        this.finalService = finalService;
    }

    @GetMapping("getAll")
    public List<Film> getAll() {
        return this.finalService.getAll();
    }


    @GetMapping("getById")
    public Film getById(@RequestParam Long id) {
        return this.finalService.getById(id);
    }


}
