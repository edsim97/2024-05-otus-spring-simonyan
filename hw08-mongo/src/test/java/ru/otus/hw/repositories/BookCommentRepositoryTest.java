package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoRepository для работы с комментариями книг должен")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookCommentRepositoryTest {

    private static final String BOOK_COMMENT_ID = "TEST_ID";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    private Book book;

    @BeforeEach
    public void getBook() {

        book = bookRepository.findAll().get(0);
        assertThat(book).isNotNull();
    }

    @DisplayName("искать все комментарии")
    @Test
    public void shouldFindAllBookComments() {

        List<BookComment> bookComments = bookCommentRepository.findAll();
        assertThat(bookComments).hasSize(6);
    }

    @DisplayName("искать комментарий по ID")
    @Test
    public void shouldFindBookCommentById() {

        List<BookComment> bookComments = bookCommentRepository.findAll();
        BookComment expectedComment = bookComments.get(0);
        assertThat(expectedComment).isNotNull();

        Optional<BookComment> bookComment = bookCommentRepository.findById(expectedComment.getId());
        assertThat(bookComment).isNotEmpty().get().isEqualTo(expectedComment);
    }

    @DisplayName("искать комментарии по ID книги")
    @Test
    public void shouldFindBookCommentsByBookId() {

        List<BookComment> bookComments = bookCommentRepository.findByBook(book.getId());
        List<BookComment> allComments = bookCommentRepository.findAll();

        assertThat(allComments.stream().filter(comment -> Objects.equals(comment.getBook().getId(), book.getId())))
            .hasSameElementsAs(bookComments);
    }

    @DisplayName("создавать комментарий")
    @Test
    public void shouldCreateBookComment() {

        BookComment expectedBookComment = new BookComment(BOOK_COMMENT_ID, book, "TestBookCommentName");
        BookComment bookComment = bookCommentRepository.insert(expectedBookComment);
        assertThat(bookComment).isNotNull().isEqualTo(expectedBookComment);
    }

    @DisplayName("обновлять комментарий")
    @Test
    public void shouldUpdateBookComment() {

        BookComment bookComment = bookCommentRepository.findAll().get(0);
        BookComment expectedBookComment = new BookComment(bookComment.getId(), book, "BookCommentTestUpdatedName");
        assertThat(bookComment).isNotNull().isNotEqualTo(expectedBookComment);

        bookCommentRepository.save(expectedBookComment);

        assertThat(bookCommentRepository.findById(bookComment.getId()))
            .isNotEmpty()
            .get()
            .isEqualTo(expectedBookComment);
    }

    @DisplayName("удалять комментарий")
    @Test
    public void shouldDeleteBookComment() {

        BookComment bookComment = bookCommentRepository.findAll().get(0);
        assertThat(bookComment).isNotNull();

        bookCommentRepository.delete(bookComment);

        assertThat(bookCommentRepository.findById(bookComment.getId())).isEmpty();
    }
}
