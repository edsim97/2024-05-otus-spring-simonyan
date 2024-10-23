package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Репозиторий для Genre должен")
@DataJpaTest
public class GenreRepositoryTest {

    private final GenreRepository genreRepository;

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindAllGenres() {

        final List<Genre> genres = this.genreRepository.findAll();
        final List<Genre> expectedGenres = List.of(
            new Genre(1L, "Genre_1"),
            new Genre(2L, "Genre_2"),
            new Genre(3L, "Genre_3"),
            new Genre(4L, "Genre_4"),
            new Genre(5L, "Genre_5"),
            new Genre(6L, "Genre_6")
        );
        assertThat(genres).hasSize(6).containsAll(expectedGenres);
    }

    @DisplayName("находить жанр по его ID")
    @Test
    void shouldFindGenreById() {

        final List<Genre> genres = this.genreRepository.findAllByIds(Set.of(1L, 2L, 3L));
        final List<Genre> expectedGenres = List.of(
            new Genre(1L, "Genre_1"),
            new Genre(2L, "Genre_2"),
            new Genre(3L, "Genre_3")
        );

        assertThat(genres)
            .hasSize(3)
            .containsAll(expectedGenres);
    }
}
