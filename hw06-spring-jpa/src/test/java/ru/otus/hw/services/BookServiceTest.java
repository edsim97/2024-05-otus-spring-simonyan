package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Сервис для BookComment должен")
@SpringBootTest
public class BookServiceTest {

    private final BookRepository bookRepository;

    private final BookServiceImpl bookService;

    @DisplayName("находить книгу по ID")
    @Test
    void shouldFindBookById() {

        final Optional<Book> book = this.bookService.findById(1);

        assertThat(book).isNotEmpty().get().hasFieldOrPropertyWithValue("title", "BookTitle_1");
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindAllBooks() {

        final List<Book> books = this.bookService.findAll();

        ListAssert<Book> assertions = assertThat(books).hasSize(3);
        assertions.element(0).hasFieldOrPropertyWithValue("title", "BookTitle_1");
        assertions.element(1).hasFieldOrPropertyWithValue("title", "BookTitle_2");
        assertions.element(2).hasFieldOrPropertyWithValue("title", "BookTitle_3");
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("сохранять новую книгу в БД")
    @Test
    void shouldInsertBook() {

        final Book expectedBook = bookService.insert("BookTitle_4", 1, Set.of(1L, 2L));
        final Book book = bookRepository.findById(4).orElse(null);

        assertThat(book)
            .isNotNull()
            .isEqualTo(expectedBook);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("обновлять существующую книгу в БД")
    @Test
    void shouldUpdateBook() {

        final Book book = bookService.update(3, "BookTitle_Test", 1, Set.of(1L, 2L));
        final Book expectedBook = bookRepository.findById(3).orElse(null);

        assertThat(book)
            .isNotNull()
            .isEqualTo(expectedBook);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {

        bookService.deleteById(3);

        assertThat(bookRepository.findById(3)).isEmpty();
    }
}
