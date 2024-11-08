package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для BookComment должен")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(propagation = Propagation.NEVER)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookCommentServiceTest {

    private final BookCommentServiceImpl bookCommentService;

    @DisplayName("возвращать список всех комментариев для указанной книги")
    @Test
    void shouldFindAllComments() {

        final List<BookComment> comments = this.bookCommentService.findAllForBook(1);

        ListAssert<BookComment> assertions = assertThat(comments).hasSize(2);
        assertions.element(0).hasFieldOrPropertyWithValue("text", "Book_1__Comment_1");
        assertions.element(1).hasFieldOrPropertyWithValue("text", "Book_1__Comment_2");
    }

    @DisplayName("находить комментарий по его ID")
    @Test
    void shouldFindCommentById() {

        final Optional<BookComment> comment = this.bookCommentService.findById(1L);

        assertThat(comment)
            .isNotEmpty()
            .get()
            .hasFieldOrPropertyWithValue("text", "Book_1__Comment_1");
    }
}
