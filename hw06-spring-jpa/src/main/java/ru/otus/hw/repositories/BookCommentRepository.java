package ru.otus.hw.repositories;

import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    List<BookComment> findAllForBook(long bookId);

    Optional<BookComment> findById(long id);
}
