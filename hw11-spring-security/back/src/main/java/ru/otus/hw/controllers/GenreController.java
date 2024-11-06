package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getGenres() {

        return genreService.findAll();
    }
}
