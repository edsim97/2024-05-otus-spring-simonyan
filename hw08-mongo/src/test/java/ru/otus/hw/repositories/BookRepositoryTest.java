package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoRepository для работы с книгами должен")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    private static final String BOOK_ID = "TEST_ID";

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("искать все книги")
    @Test
    public void shouldFindAllBooks() {

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3);
    }

    @DisplayName("искать книгу по ID")
    @Test
    public void shouldFindBookById() {

        List<Book> books = bookRepository.findAll();
        Book expectedBook = books.get(0);
        assertThat(expectedBook).isNotNull();

        Optional<Book> book = bookRepository.findById(expectedBook.getId());
        assertThat(book).isNotEmpty().get().isEqualTo(expectedBook);
    }

    @DisplayName("создавать книгу")
    @Test
    public void shouldCreateBook() {

        Book expectedBook = new Book(BOOK_ID, "TestBookName", null, List.of());
        Book book = bookRepository.insert(expectedBook);
        assertThat(book).isNotNull().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    public void shouldUpdateBook() {

        Book book = bookRepository.findAll().get(0);
        Book expectedBook = new Book(book.getId(), "BookTestUpdatedName", null, List.of());
        assertThat(book).isNotNull().isNotEqualTo(expectedBook);

        bookRepository.save(expectedBook);

        assertThat(bookRepository.findById(book.getId()))
            .isNotEmpty()
            .get()
            .isEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу")
    @Test
    public void shouldDeleteBook() {

        Book book = bookRepository.findAll().get(0);
        assertThat(book).isNotNull();

        bookRepository.delete(book);

        assertThat(bookRepository.findById(book.getId())).isEmpty();
    }
}
