package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.jpa.JpaAuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Репозиторий для Author должен")
@DataJpaTest
@Import(JpaAuthorRepository.class)
public class AuthorRepositoryTest {

    private final JpaAuthorRepository authorRepository;

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldFindAllAuthors() {

        final List<Author> authors = this.authorRepository.findAll();
        final List<Author> expectedAuthors = List.of(
            new Author(1L, "Author_1"),
            new Author(2L, "Author_2"),
            new Author(3L, "Author_3")
        );
        assertThat(authors).hasSize(3).containsAll(expectedAuthors);
    }

    @DisplayName("находить автора по его ID")
    @Test
    void shouldFindAuthorById() {

        final Optional<Author> author = authorRepository.findById(1);

        assertThat(author)
            .isNotEmpty()
            .get()
            .hasFieldOrPropertyWithValue("fullName", "Author_1");
    }
}
