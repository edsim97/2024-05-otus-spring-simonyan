package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoRepository для работы с жанрами должен")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GenreRepositoryTest {

    private static final String GENRE_ID = "TEST_ID";

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("искать все жанры")
    @Test
    public void shouldFindAllGenres() {

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(6);
    }

    @DisplayName("искать жанр по ID")
    @Test
    public void shouldFindGenreById() {

        List<Genre> genres = genreRepository.findAll();
        Genre expectedGenre = genres.get(0);
        assertThat(expectedGenre).isNotNull();

        Optional<Genre> genre = genreRepository.findById(expectedGenre.getId());
        assertThat(genre).isNotEmpty().get().isEqualTo(expectedGenre);
    }

    @DisplayName("создавать жанр")
    @Test
    public void shouldCreateGenre() {

        Genre expectedGenre = new Genre(GENRE_ID, "TestGenreName");
        Genre genre = genreRepository.insert(expectedGenre);
        assertThat(genre).isNotNull().isEqualTo(expectedGenre);
    }

    @DisplayName("обновлять жанр")
    @Test
    public void shouldUpdateGenre() {

        Genre genre = genreRepository.findAll().get(0);
        Genre expectedGenre = new Genre(genre.getId(), "GenreTestUpdatedName");
        assertThat(genre).isNotNull().isNotEqualTo(expectedGenre);

        genreRepository.save(expectedGenre);

        assertThat(genreRepository.findById(genre.getId()))
            .isNotEmpty()
            .get()
            .isEqualTo(expectedGenre);
    }

    @DisplayName("удалять жанр")
    @Test
    public void shouldDeleteGenre() {

        Genre genre = genreRepository.findAll().get(0);
        assertThat(genre).isNotNull();

        genreRepository.delete(genre);

        assertThat(genreRepository.findById(genre.getId())).isEmpty();
    }
}
