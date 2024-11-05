package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoRepository для работы с авторами должен")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class AuthorRepositoryTest {

    private static final String AUTHOR_ID = "TEST_ID";

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("искать всех авторов")
    @Test
    public void shouldFindAllAuthors() {

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(4);
    }

    @DisplayName("искать автора по ID")
    @Test
    public void shouldFindAuthorById() {

        List<Author> authors = authorRepository.findAll();
        Author expectedAuthor = authors.get(0);
        assertThat(expectedAuthor).isNotNull();

        Optional<Author> author = authorRepository.findById(expectedAuthor.getId());
        assertThat(author).isNotEmpty().get().isEqualTo(expectedAuthor);
    }

    @DisplayName("создавать автора")
    @Test
    public void shouldCreateAuthor() {

        Author expectedAuthor = new Author(AUTHOR_ID, "TestAuthorName");
        Author author = authorRepository.insert(expectedAuthor);
        assertThat(author).isNotNull().isEqualTo(expectedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    public void shouldUpdateAuthor() {

        Author author = authorRepository.findAll().get(0);
        Author expectedAuthor = new Author(author.getId(), "AuthorTestUpdatedName");
        assertThat(author).isNotNull().isNotEqualTo(expectedAuthor);

        authorRepository.save(expectedAuthor);

        assertThat(authorRepository.findById(author.getId()))
            .isNotEmpty()
            .get()
            .isEqualTo(expectedAuthor);
    }

    @DisplayName("удалять автора")
    @Test
    public void shouldDeleteAuthor() {

        Author author = authorRepository.findAll().get(0);
        assertThat(author).isNotNull();

        authorRepository.delete(author);

        assertThat(authorRepository.findById(author.getId())).isEmpty();
    }
}
