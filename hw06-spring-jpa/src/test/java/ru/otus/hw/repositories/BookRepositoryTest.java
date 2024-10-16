package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.jpa.JpaBookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Репозиторий для Book должен")
@DataJpaTest
@Import(JpaBookRepository.class)
public class BookRepositoryTest {

    private final EntityManager em;

    private final JpaBookRepository bookRepository;

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindAllBooks() {

        final List<Book> books = this.bookRepository.findAll();

        ListAssert<Book> bookAssertions = assertThat(books).hasSize(3);
        bookAssertions.element(0).hasFieldOrPropertyWithValue("title", "BookTitle_1");
        bookAssertions.element(1).hasFieldOrPropertyWithValue("title", "BookTitle_2");
        bookAssertions.element(2).hasFieldOrPropertyWithValue("title", "BookTitle_3");
    }

    @DisplayName("находить книгу по её ID")
    @Test
    void shouldFindBookById() {

        final Optional<Book> book = bookRepository.findById(1L);

        assertThat(book)
            .isNotEmpty()
            .get()
            .hasFieldOrPropertyWithValue("title", "BookTitle_1");
    }

    @DisplayName("сохранять новую книгу в БД")
    @Test
    void shouldInsertBook() {

        final Book expectedBook = bookRepository.save(new Book(0, "BookTitle_4", null, null, null));
        final Book book = em.find(Book.class, 4);

        assertThat(book)
            .isNotNull()
            .isEqualTo(expectedBook);
    }

    @DisplayName("обновлять существующую книгу в БД")
    @Test
    void shouldUpdateBook() {

        final Book expectedBook = em.find(Book.class, 3);
        final Book book = bookRepository.save(expectedBook.toBuilder().title("BookTitle_Test").build());

        assertThat(book)
            .isNotNull()
            .isEqualTo(expectedBook);
    }
}
