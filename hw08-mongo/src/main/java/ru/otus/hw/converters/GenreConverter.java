package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Genre;

import java.util.Optional;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {

        return Optional.ofNullable(genre)
            .map(g -> "{ Id: %s, Name: %s }".formatted(g.getId(), g.getName()))
            .orElse("null");
    }
}
