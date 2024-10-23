package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.repositories.jpa.JpaBookCommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Репозиторий для BookComment должен")
@DataJpaTest
@Import(JpaBookCommentRepository.class)
public class BookCommentRepositoryTest {

    private final JpaBookCommentRepository bookCommentRepository;

    @DisplayName("возвращать список всех комментариев для указанной книги")
    @Test
    void shouldFindAllComments() {

        final List<BookComment> comments = this.bookCommentRepository.findAllForBook(1);
        final Book expectedBook = Book.builder()
            .id(1L)
            .title("BookTitle_1")
            .build();

        final List<BookComment> expectedComments = List.of(
            new BookComment(1L, expectedBook, "Book_1__Comment_1"),
            new BookComment(2L, expectedBook, "Book_1__Comment_2")
        );
        assertThat(comments).hasSize(2).containsAll(expectedComments);
    }

    @DisplayName("находить комментарий по его ID")
    @Test
    void shouldFindCommentById() {

        final Optional<BookComment> comment = this.bookCommentRepository.findById(1L);
        final Book expectedBook = Book.builder()
            .id(1L)
            .title("BookTitle_1")
            .build();

        assertThat(comment)
            .isNotEmpty()
            .get()
            .isEqualTo(new BookComment(1L, expectedBook, "Book_1__Comment_1"));
    }
}
